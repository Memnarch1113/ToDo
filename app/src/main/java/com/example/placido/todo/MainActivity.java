package com.example.placido.todo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnClickListener {


    EditText txtItem;
    Button btn;
    ListView listView;

    //ArrayList<toDoItem> todoList; //data source
    private SQLiteDatabase db;

    ToDoListAdapter aa; //data bridge between arraylist (data source) and listview (control)

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
       // aa = new ToDoListAdapter(this, android.R.layout.simple_list_item _1, todoList);

        //Get a new databse helper, then start a new database (or find one that alrady exists)
        ToDoItemsContract.todoDbHelper myDbHelper = new ToDoItemsContract(). new todoDbHelper(this.getBaseContext());
        this.db = myDbHelper.getWritableDatabase();

        //This cursor will collect all entries in the database
        Cursor cursor = db.rawQuery("SELECT  * FROM " + ToDoItemsContract.TodoEntry.TABLE_NAME, null);

        //Initialize the adapter, and give it the cursor with all of the entries from the database. Then assign it to the ListView
        this.aa = new ToDoListAdapter(this.getBaseContext(),cursor,0);
        listView.setAdapter(aa); // sent an adapter to the control


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
            aa.changeCursor(cursor);
            //Tell the adapter to update the ListView with the new todoItem
            this.aa.notifyDataSetChanged(); //notifying array adapter that there are some changes
        }
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
        this.aa.notifyDataSetChanged();
        Log.d("MyTag","TOGGLED");
    }
    */
}
