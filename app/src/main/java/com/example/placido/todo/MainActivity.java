package com.example.placido.todo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnClickListener {

    Context context;
    EditText txtItem;
    Button btn;
    ListView listView;

    //ArrayList<toDoItem> todoList; //data source
    private SQLiteDatabase db;

    ToDoListAdapter ca; //data bridge between data source and listview (control)

    String selectedmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtItem = (EditText)findViewById(R.id.editText);
        btn = (Button)findViewById(R.id.button);
        listView = (ListView)findViewById(R.id.listView);


       // todoList = new ArrayList<>();
       // ca = new ToDoListAdapter(this, android.R.layout.simple_list_item _1, todoList);

        this.context = this;
        //Get a new database helper, then start a new database (or find one that already exists)
        ToDoItemsContract.todoDbHelper myDbHelper = new ToDoItemsContract(). new todoDbHelper(this.getBaseContext());
        this.db = myDbHelper.getWritableDatabase();

        //This cursor will collect all entries in the database
        Cursor cursor = db.rawQuery("SELECT  * FROM " + ToDoItemsContract.TodoEntry.TABLE_NAME, null);

        //Initialize the adapter, and give it the cursor with all of the entries from the database. Then assign it to the ListView
        this.ca = new ToDoListAdapter(this.getBaseContext(),cursor,0);
        listView.setAdapter(ca); // sent an adapter to the control
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                Log.d("MYTAG", "YOU JUST LONGCLICKED");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("TodoOptions");
                builder.setMessage("Delete this todo?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (context instanceof MainActivity) {
                            TextView dateField = (TextView) view.findViewById(R.id.dateAdded);
                            ((MainActivity) context).deleteFromList(dateField.getText().toString());
                            Log.d("MYTAG", "Just sent the following data for deletion:" + dateField.getText().toString());
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });


        btn.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu); *commented out because not going to use xml file

        super.onCreateOptionsMenu(menu);
        menu.add("Do Something Healthy");
        menu.add("Shopping");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        if(!item.hasSubMenu()){
            if(item.getTitle() == "Do Something Healthy"){
                this.displayPopup("Do Something Healthy", getResources().getStringArray(R.array.do_something_healthy)); //pass different arguments to popfunction, passing do something healthy array

            }
            if(item.getTitle() == "Shopping"){
                this.displayPopup("Shopping", getResources().getStringArray(R.array.shopping));

            }

            selectedmenu = item.getTitle().toString();
        }
        //logic
        //call a function to display pop up according to menu selected

        return true;
    }

    private void displayPopup(String title, String[] item){ //generates popup which had to implement dialoginterface
        //create alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setItems(item, this);
        builder.show();
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
    //This method happens when the button is called, it calls the addItem method to actually go through the motions of updating the list
    @Override
    public void onClick(View v){
        if(v == this.btn){ //check if listener has been initiated by button or something else

            //to add task to listview

            toDoItem x = new toDoItem(txtItem.getText().toString());
            this.addItem(x);
            this.txtItem.setText(""); //setting add text to null basically

        }
    }
    //This method is called when the user presses the button to store a new item in the list
    private void addItem(toDoItem item){
        if(item != null){
            //Initialize a new item to be added to the database
            ContentValues newItem = new ContentValues();
            //Assign this new item the name passed from the EditText field
            newItem.put(ToDoItemsContract.TodoEntry.COLUMN_TWO_NAME_TITLE, item.getName());
            //Since Databases don't support boolean fields, if the todoitem is completed, pass in a 1, otherwise pass in a 0
            if (item.isCompleted()){
                newItem.put(ToDoItemsContract.TodoEntry.COLUMN_THREE_NAME_TITLE, 1);
            }
            else {
                newItem.put(ToDoItemsContract.TodoEntry.COLUMN_THREE_NAME_TITLE, 0);
            }
            //Finally, pass in the date the todoItem was created
            newItem.put(ToDoItemsContract.TodoEntry.COLUMN_FOUR_NAME_TITLE, item.getDateCreated());

            //Last: insert the new item into the database
            long newRowId = db.insert(ToDoItemsContract.TodoEntry.TABLE_NAME, ToDoItemsContract.TodoEntry.COLUMN_ONE_ENTRY_ID, newItem);

            //Now: get a new cursor of all the items in the database, so that it includes the new one we just added
            Cursor cursor = db.rawQuery("SELECT  * FROM " + ToDoItemsContract.TodoEntry.TABLE_NAME, null);
            //Pass that new cursor to the adapter
            ca.changeCursor(cursor);
            //Tell the adapter to update the ListView with the new todoItem
            this.ca.notifyDataSetChanged(); //notifying adapter that there are some changes
        }
    }
    //THANKS TO MY ROOMATE YARON FOR UX TESTING!!!!!!
    public void deleteFromList(String nameOfTodo){
        String selection = ToDoItemsContract.TodoEntry.COLUMN_FOUR_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = { nameOfTodo };
        db.delete(ToDoItemsContract.TodoEntry.TABLE_NAME, selection, selectionArgs);
        Cursor cursor = db.rawQuery("SELECT  * FROM " + ToDoItemsContract.TodoEntry.TABLE_NAME, null);
        ca.changeCursor(cursor);
        this.ca.notifyDataSetChanged(); //notifying adapter that there are some changes
    }

    @Override
    public void onClick(DialogInterface arg0, int arg1){
       // if(selectedmenu == "Do something healthy") this.addItem(this.DoSomethingHealthy[order]);
    }
/*
    public void toggleCompleted(int position){
        if (todoList.get(position).isCompleted()){
            todoList.get(position).setCompleted(false);
        }
        else {
            todoList.get(position).setCompleted(true);
        }
        this.ca.notifyDataSetChanged();
        Log.d("MyTag","TOGGLED");
    }
    */
}
