package com.example.placido.todo;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gabe on 10/29/2015.
 * This is the modified adapter that will take in toDoItem objects, populate a task_row, and give it back
 * to the ListView as a view so that it can be displayed in the activity.
 * I don't completely understand alllll of the code (mostly the constructors) since I got it from a website
 */
public class ToDoListAdapter extends ArrayAdapter<toDoItem> {

    public ToDoListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ToDoListAdapter(Context context, int resource, List<toDoItem> items) {
        super(context, resource, items);
    }

    @Override
    //THIS IS THE IMPORTANT METHOD, that does all of the work to give the ListView what it needs to display
    public View getView(int position, View convertView, ViewGroup parent) {

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
                //This code doesn't work, but It was intended to make the TextViews be struck out when the ToDoItem was completed.
                //TODO: Get the list items to be struck out when they are completed.
                if (p.isCompleted()) {
                    nameView.setPaintFlags(nameView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    dateView.setPaintFlags(dateView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                completeBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LinearLayout outerList = (LinearLayout) v.getParent();

                    }
                });
            }
        }


        return v;//Then after populating the layout, the adapter returns the view to the ListView, which adds it as a row it displays.
    }
}
