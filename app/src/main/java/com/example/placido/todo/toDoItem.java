package com.example.placido.todo;


import java.util.Calendar;

/**
 * Created by Gabe on 10/24/2015.
 * This is the object that stores all of the data associated with a tdo item.
 * As of right now, this only contains some fields, and getter + setter methods.
 * Also as of right now, the date created field only shows created today. I haven't worked out the whole system
 * for creating a calendar and figuring out how long it's been since the creation of the object
 *
 * TODO: set up dateCreated to store an actual date
 * TODO: add a method that converts the date created to the string that will be shown once the item is added to the list.
 */
public class toDoItem {
    private String name;
    private String catagory;
    private String dateCreated;

    public toDoItem (String name) {
        this.name = name;
       // Calendar c = Calendar.getInstance();
       // this.dateCreated = c.toString();
        this.dateCreated = "Created Today";

    }
    public void setName(String name) {
        this.name = name;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    private boolean completed;


    public boolean isCompleted() {
        return completed;
    }

    public String getDateCreated() {

        return dateCreated;
    }

    public String getCatagory() {

        return catagory;
    }

    public String getName() {

        return name;
    }

}
