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

/*
        THIS IS OLD CODE FROM THE OLD VERSION OF THE TODOLISTADAPTER

    @Override
    //THIS IS THE IMPORTANT METHOD, that does all of the work to give the ListView what it needs to display
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView; //No idea what this is

        if (v == null) {//No idea what this does, exactly
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.task_row, parent, false);//IMPORTANT: Not sure, but I think this line tells the adapter what kind of format it should be filling out. In this case, it's the task_row.xml that I made for each ToDoItem
            //This is code that I thought I needed to make the rows tall enough, but it turned out it wasn't needed
          //  ViewGroup.LayoutParams l = v.getLayoutParams();
           // l.height += 10;
           // v.setLayoutParams(l);
        }

        toDoItem p = getItem(position);//Don't know how exactly this works, but p will be the toDoItem that the adapter will be adding to the list

        if (p != null) {//Find each of the elements that are stored in the task_row.xml layout, so that we can edit them
            TextView nameView = (TextView) v.findViewById(R.id.toDoName);
            TextView dateView = (TextView) v.findViewById(R.id.dateAdded);
            CheckBox completeBox = (CheckBox) v.findViewById(R.id.checkbox);

            //Then, if the toDoItem in question has each of the following properties, change the TextViews so so that they show off the correct values
            if (nameView != null) {
                nameView.setText(p.getName());
            }

            if (dateView != null) {
                dateView.setText(p.getDateCreated());
            }

            if (completeBox != null) {
                completeBox.setChecked(p.isCompleted());
                //When drawing a toDoItem for the first time, check to see if it's marked complete. If it is, mark it's name and date created to be drawn as struckthrough
                if (p.isCompleted() && nameView != null && dateView != null) {
                    nameView.setPaintFlags(nameView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    dateView.setPaintFlags(dateView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }

                //When you're done with that, make a listener for the checkbox, that will wait until user clicks on it,
                //when they do make the text struckthrough or not, depending on whether or not the
                //checkbox is now checked or clear
                completeBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LinearLayout outerList = (LinearLayout) v.getParent();//Get the container of the checkbox so we can find the text views that show the name and date of the todoitem
                        TextView nameField = (TextView) outerList.findViewById(R.id.toDoName);//Fetch the name field
                        TextView dateField = (TextView) outerList.findViewById(R.id.dateAdded);//Fetch the date field
                        CheckBox c = (CheckBox) v;//Cast the checkbox so we can see if it is checked
                        if (c.isChecked()) {//If the checkbox is now checked, set both of those fields to struck through
                            nameField.setPaintFlags(nameField.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            dateField.setPaintFlags(dateField.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        }
                        else {//Set both of those fields to not struck through
                            nameField.setPaintFlags(1);
                            dateField.setPaintFlags(1);
                        }
                        if (context instanceof MainActivity){
                            ((MainActivity) context).toggleCompleted(position);
                        }

                    }
                });
            }
        }


        return v;//Then after populating the layout, the adapter returns the view to the ListView, which adds it as a row it displays.
    }
    */
}
