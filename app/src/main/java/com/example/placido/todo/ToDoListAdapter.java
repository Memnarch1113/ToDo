package com.example.placido.todo;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Gabe on 10/29/2015.
 * This is the modified adapter that will take in toDoItem objects, populate a task_row, and give it back
 * to the ListView as a view so that it can be displayed in the activity.
 * I don't completely understand alllll of the code (mostly the constructors) since I got it from a website
 */
public class ToDoListAdapter extends CursorAdapter {

    public ToDoListAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, 0);
    }

    //This inflates a new view and returns it (it doesn't store any values from the database yet)
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return LayoutInflater.from(context).inflate(R.layout.task_row,parent,false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //Get the elements from the layout for the ListView row (the layout task_row)
        TextView nameView = (TextView) view.findViewById(R.id.toDoName);
        TextView dateView = (TextView) view.findViewById(R.id.dateAdded);
        CheckBox completeBox = (CheckBox) view.findViewById(R.id.checkbox);

        //Get the values for each element from the cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
        int isComplete = cursor.getInt(cursor.getColumnIndexOrThrow("isComplete"));

        //Set the elements in the UI to reflect these fetched values
        nameView.setText(name);
        dateView.setText(date);
        if (isComplete == 0){
            completeBox.setChecked(false);
        }
        else {
            completeBox.setChecked(true);
        }
    }
}
