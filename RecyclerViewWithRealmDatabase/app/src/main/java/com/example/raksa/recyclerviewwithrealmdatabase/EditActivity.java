package com.example.raksa.recyclerviewwithrealmdatabase;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.raksa.recyclerviewwithrealmdatabase.model.Book;

import io.realm.Realm;

public class EditActivity extends AppCompatActivity {

    EditText title;
    EditText author;
    Button editButton;
    Realm bookDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Explode explodeTransition = new Explode();
            explodeTransition.setDuration(1000);
            getWindow().setEnterTransition(explodeTransition);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //get Database
        bookDatabase = Realm.getDefaultInstance();

        //get Data From Intent
        Intent intent = getIntent();
        String titleData = intent.getStringExtra("title");
        String authorData = intent.getStringExtra("author");
        final String ID_Data = intent.getStringExtra("ID");

        //interraction with view
        title = (EditText) findViewById(R.id.editTextEditTitle);
        author = (EditText) findViewById(R.id.editTextEditAuthor);
        editButton = (Button) findViewById(R.id.buttonEdit);

        title.setText(titleData);
        author.setText(authorData);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (title.getText().toString().startsWith(" ")||
                        author.getText().toString().startsWith(" ")||
                        title.getText().toString().isEmpty()||
                        author.getText().toString().isEmpty()){

                    Toast.makeText(getBaseContext(),"Can not Start With Space or Empty Text",Toast.LENGTH_SHORT)
                            .show();
                }
                else {

                    bookDatabase.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Book book = realm.where(Book.class).equalTo("ID",ID_Data).findFirst();
                            book.setTitle(title.getText().toString());
                            book.setAuthor(author.getText().toString());
                        }
                    });

                    Toast.makeText(getBaseContext(),"Successfully Edited !",Toast.LENGTH_SHORT).show();
                    Intent editIntent = new Intent(getBaseContext(),MainActivity.class);
                    editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(editIntent,MainActivity.Edit_BOOK);
                    finish();
                }

            }
        });

    }
}
