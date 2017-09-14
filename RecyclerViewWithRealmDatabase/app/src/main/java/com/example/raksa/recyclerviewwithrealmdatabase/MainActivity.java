package com.example.raksa.recyclerviewwithrealmdatabase;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.raksa.recyclerviewwithrealmdatabase.model.Book;
import com.example.raksa.recyclerviewwithrealmdatabase.my_recycler_view.MyAdapter;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    //REQUEST CODE
    public static final int ADD_BOOK = 1;
    public static final int Edit_BOOK = 2;

    public static Activity getMainActivity;
    public static RecyclerView recyclerView;
    Realm bookDatabase;
    TextView textViewStatus;
    RealmResults<Book> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
            getWindow().setAllowEnterTransitionOverlap(false);
            getWindow().setAllowReturnTransitionOverlap(false);

            Explode explodeTransition = new Explode();
            explodeTransition.setDuration(500);

            getWindow().setReenterTransition(explodeTransition);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View Reference
        textViewStatus = (TextView) findViewById(R.id.textViewDataStatus);


        //set Activity Reference
        getMainActivity = this;


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Books");
        setSupportActionBar(toolbar);

        //Realm Database
        bookDatabase = Realm.getDefaultInstance();

        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //get and set Data to RecyclerView
        bookDatabase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results = realm.where(Book.class).findAll();
            }
        });

        if (results.size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            MyAdapter adapter = new MyAdapter(this,results);
            recyclerView.setAdapter(adapter);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){
            case R.id.addBookActionItem:
                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                }
                Intent intent = new Intent(this,AddActivity.class);
                startActivity(intent,options.toBundle());
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ADD_BOOK:

                //get the database
                bookDatabase.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        results = realm.where(Book.class).findAll();
                    }
                });

                MyAdapter adapter =new MyAdapter(this,results);
                recyclerView.setAdapter(adapter);
                if (recyclerView.getVisibility()==View.GONE){
                    recyclerView.setVisibility(View.VISIBLE);
                }
                break;
            case Edit_BOOK:

                //get the database
                bookDatabase.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        results = realm.where(Book.class).findAll();
                    }
                });

                MyAdapter editAdapter = new MyAdapter(this,results);
                recyclerView.setAdapter(editAdapter);

            default:
                break;



        }
    }
}
