package com.example.raksa.recyclerviewwithrealmdatabase.my_recycler_view;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raksa.recyclerviewwithrealmdatabase.EditActivity;
import com.example.raksa.recyclerviewwithrealmdatabase.MainActivity;
import com.example.raksa.recyclerviewwithrealmdatabase.R;
import com.example.raksa.recyclerviewwithrealmdatabase.model.Book;


import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Raksa on 9/7/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    RealmResults<Book> data;
    LayoutInflater inflater;
    Realm bookDatabase;

    public MyAdapter(Context context, RealmResults<Book> arrayList){
        inflater = LayoutInflater.from(context);
        this.data = arrayList;
        this.bookDatabase = Realm.getDefaultInstance();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.my_card_view,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Book book = data.get(position);
        holder.bookTitle.setText(book.getTitle());
        holder.authorName.setText(book.getAuthor());


        holder.editBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.getMainActivity);
                    Intent startActivityIntent = new Intent(holder.context,EditActivity.class);
                    startActivityIntent.putExtra("title",book.getTitle());
                    startActivityIntent.putExtra("author",book.getAuthor());
                    startActivityIntent.putExtra("ID",book.getID());
                    holder.context.startActivity(startActivityIntent,options.toBundle());
                }

            }
        });

        holder.deleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    bookDatabase.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
//                            Book deleteBook = realm.where(Book.class).equalTo("ID",book.getID()).findFirst();
                            data.deleteFromRealm(position);
                            data = realm.where(Book.class).findAll();
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position,data.size());
                        }
                    });

                }catch (Exception e){
                }

                if (!(data.size()>0)){
                    MainActivity.recyclerView.setVisibility(View.GONE);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
