package com.example.raksa.recyclerviewwithrealmdatabase;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.raksa.recyclerviewwithrealmdatabase.model.Book;

import java.util.UUID;

import io.realm.Realm;

public class AddActivity extends AppCompatActivity {

    Realm bookDatabase;
    EditText editTextTitle;
    EditText editTextAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //set up Activity Transition...
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Explode explodeTransition = new Explode();
            explodeTransition.setDuration(1000);
            getWindow().setEnterTransition(explodeTransition);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //View Reference
        editTextTitle = (EditText) findViewById(R.id.editText);
        editTextAuthor = (EditText) findViewById(R.id.editText2);

        //get the database
        bookDatabase = Realm.getDefaultInstance();



    }

    //interrect with Button and View
    public void onAddClickButton(View view) {

        CharSequence charSequence = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String title = editTextTitle.getText().toString();
        String author = editTextAuthor.getText().toString();

        if (title.startsWith(" ")||author.startsWith(" ")){
            Toast.makeText(getBaseContext(),"Please Fill In The Blank",Toast.LENGTH_SHORT).show();
        }
        else {


            try {
                //working with database
                bookDatabase.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        String bookID = UUID.randomUUID().toString();
                        Book book = realm.createObject(Book.class,bookID);
                        book.setTitle(editTextTitle.getText().toString());
                        book.setAuthor(editTextAuthor.getText().toString());

                    }
                });
                //success message
                Toast.makeText(getApplicationContext(),"Succesfully Add!",Toast.LENGTH_SHORT).show();
                Intent intentAddResult = new Intent(getBaseContext(),MainActivity.class);
                intentAddResult.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intentAddResult,MainActivity.ADD_BOOK);

            }catch (Exception e){

                Log.i("Realm Add",e.getMessage(),e.getCause());
                Toast.makeText(getApplicationContext(),"Fail to Add The Data!",Toast.LENGTH_SHORT).show();
            }

            finish();

        }

    }
}
