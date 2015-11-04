package com.example.placido.todo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnClickListener {

    EditText txtItem;
    Button btn;
    ListView listView;

    ArrayList<toDoItem> todoList; //data source
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

       btn.setOnClickListener(this);

        todoList = new ArrayList<toDoItem>();
        aa = new ToDoListAdapter(this, android.R.layout.simple_list_item_1, todoList);
        listView.setAdapter(aa); // sent an adapter to the control

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
        if(item.hasSubMenu() == false){
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
            this.todoList.add(item);
            this.aa.notifyDataSetChanged(); //notifying array adapter that there are some changes
        }
    }

    @Override
    public void onClick(DialogInterface arg0, int arg1){
       // if(selectedmenu == "Do something healthy") this.addItem(this.DoSomethingHealthy[order]);
    }
}
