package com.example.zamanyonetimi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;


public class MainReminder extends AppCompatActivity {
    Button setTime;
    Button scheduleEvent;
    TextView textView;
    static final int DATEPICKER_DIALOG_ID = 0;
    static final int TIMEPICKER_DIALOG_ID = 1;
    int Year, Month, Day, Hour, Minute;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reminder);
        final Calendar calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR_OF_DAY);
        Minute = calendar.get(Calendar.MINUTE);
        setTime =  findViewById(R.id.button1);
        scheduleEvent = findViewById(R.id.button2);
        textView=findViewById(R.id.textView2);




        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATEPICKER_DIALOG_ID);
            }
        });

        scheduleEvent.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                Intent intent = new Intent("com.example.zamanyonetimi");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainReminder.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, Year);
                cal.set(Calendar.MONTH, Month);
                cal.set(Calendar.DAY_OF_MONTH, Day);
                cal.set(Calendar.HOUR_OF_DAY, Hour);
                cal.set(Calendar.MINUTE, Minute);
                cal.set(Calendar.SECOND, 0);
                long mills = cal.getTimeInMillis();

                assert alarmMgr != null;
                alarmMgr.set(AlarmManager.RTC_WAKEUP, mills, pendingIntent);
                //Toast.makeText(MainReminder.this, "Event scheduled at " + Hour + ":" + Minute + " " + Day + "/" + Month + "/" + Year, Toast.LENGTH_LONG).show();
                //textView.setText(+Day +"/" +Month+"/ "+Year+"  "+Hour+ ":"  +Minute);

                String dateBack = Day +"/" +Month+"/"+Year;
                String timeBack = Hour+ ":" +Minute;
                Intent returnIntent = new Intent();
                returnIntent.putExtra("date",dateBack);
                returnIntent.putExtra("time",timeBack);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }


            // private void getSystemService(String alarmService) {
            //}
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DATEPICKER_DIALOG_ID) {
            return new DatePickerDialog(this, datePickerListener, Year, Month, Day);
        } else if (id == TIMEPICKER_DIALOG_ID) {
            return new TimePickerDialog(this, timePickerListener, Hour, Minute, false);
        } else {
            return null;
        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    Year = y;
                    Month = m + 1;
                    Day = d;
                    showDialog(TIMEPICKER_DIALOG_ID);
                }
            };

    protected TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int h, int min) {
                    Hour = h;
                    Minute = min;
                    textView.setText(+Day +"/" +Month+"/ "+Year+"  "+Hour+ ":"  +Minute);
                }
            };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",textView.getText().toString());
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}






