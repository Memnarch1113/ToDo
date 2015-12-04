package com.example.placido.todo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Danny on 12/3/15.
 */
public class AddTask extends AppCompatActivity {
    int year;
    int month;
    int day;
    int hour;
    int minute;
    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showTimePickerDialog();
        showDatePickerDialog();
    }
    public void showTimePickerDialog(){
        Button btn = (Button) findViewById(R.id.setTimeBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == TIME_DIALOG_ID){
            return new TimePickerDialog(AddTask.this, TimePickerListener, hour, minute, false);
        }
        else if(id == DATE_DIALOG_ID){
            return new DatePickerDialog(AddTask.this, DatePickerListener, year, month, day);
        }
        return null;
    }
    protected TimePickerDialog.OnTimeSetListener TimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int min) {
            hour = hourOfDay;
            minute = min;
            Toast.makeText(AddTask.this,hour+":"+minute,Toast.LENGTH_SHORT).show();
        }
    };
    public void showDatePickerDialog(){
        Button btn = (Button) findViewById(R.id.setDateBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

    protected DatePickerDialog.OnDateSetListener DatePickerListener = new DatePickerDialog.OnDateSetListener(){
        public void onDateSet(DatePicker view, int y, int m, int d){
            year = y;
            month = m+1;
            day = d;
            Toast.makeText(AddTask.this, year+"/"+month+"/"+day, Toast.LENGTH_SHORT).show();
        }
    };
    public void onSave(View view){
        Intent intent = new Intent(this, MainActivity.class);
        EditText taskName = (EditText) findViewById(R.id.taskET);
        String name = String.valueOf(taskName.getText());
        String dateDue = day+"/"+month+"/"+year+", "+hour+":"+minute;
        intent.putExtra("dateDue", dateDue);
        intent.putExtra("name", name);
        setResult(RESULT_OK, intent);
        finish();
    }
}

