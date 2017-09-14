package com.example.raksa.recyclerviewwithrealmdatabase.my_recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raksa.recyclerviewwithrealmdatabase.R;

/**
 * Created by Raksa on 9/7/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    public static Context context;

    public TextView bookTitle;
    public TextView authorName;
    public ImageView editBook;
    public ImageView deleteBook;

    public MyViewHolder(View itemView) {
        super(itemView);

        context = itemView.getContext();

        bookTitle = (TextView) itemView.findViewById(R.id.textViewBookTitle);
        authorName = (TextView) itemView.findViewById(R.id.textViewAuthor);
        editBook = (ImageView) itemView.findViewById(R.id.imageViewEditBook);
        deleteBook = (ImageView) itemView.findViewById(R.id.imageViewDeleteBook);


    }
}
