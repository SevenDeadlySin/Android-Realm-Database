package com.example.raksa.recyclerviewwithrealmdatabase.model;

import java.lang.annotation.Annotation;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Raksa on 9/7/2017.
 */

public class Book extends RealmObject {

    @PrimaryKey
    String ID;
    String title;
    String author;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }






}
