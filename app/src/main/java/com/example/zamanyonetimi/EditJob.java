package com.example.zamanyonetimi;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.zamanyonetimi.ui.Inbox.InboxAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Calendar;


public class EditJob extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    DatabaseHelper myDb;
    EditText editName, editDescription, editBaslangic, editBitis, editRemindDate, editRemindTime;
    CheckBox chkHatirlatici, chkOnemli, chkAcil;
    FloatingActionButton duzenleBtn;
    Calendar takvim = Calendar.getInstance();
    Integer jobId;
    String editJobName;
    private InboxAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        editJobName = b.getString("editJobName");

        setContentView(R.layout.activity_edit_job);

        myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getWritableDatabase();


        editName = (EditText)findViewById(R.id.editTextName);
        editDescription = (EditText)findViewById(R.id.editTextDescription);
        editBaslangic = (EditText)findViewById(R.id.editTextBaslangic);
        editBitis = (EditText)findViewById(R.id.editTextBitis);
        editRemindDate = (EditText)findViewById(R.id.editTextReminderDate);
        editRemindTime = (EditText)findViewById(R.id.editTextReminderTime);
        chkHatirlatici = (CheckBox) findViewById(R.id.checkBoxHatirlatici);
        chkOnemli = (CheckBox) findViewById(R.id.checkBoxOnemli);
        chkAcil = (CheckBox) findViewById(R.id.checkBoxAcil);
        duzenleBtn = (FloatingActionButton)findViewById(R.id.duzenletusu);

        if (editJobName != "xeklex") {
            Cursor res = db.rawQuery("select * from jobs where name = \'"+ editJobName+"\'", null);
            while (res.moveToNext()) {
                jobId = res.getInt(res.getColumnIndex("jobid"));
                if (res.getString(res.getColumnIndex("name")) != "") {
                    editName.setText(res.getString(res.getColumnIndex("name")));
                }
                if (res.getString(res.getColumnIndex("description")) != "") {
                    editDescription.setText(res.getString(res.getColumnIndex("description")));
                }
                if (res.getString(res.getColumnIndex("baslangic")) != "") {
                    editBaslangic.setText(res.getString(res.getColumnIndex("baslangic")));
                }
                if (res.getString(res.getColumnIndex("bitis")) != "") {
                    editBitis.setText(res.getString(res.getColumnIndex("bitis")));
                }
                if (res.getString(res.getColumnIndex("important")) == "1") {
                    chkOnemli.setChecked(true);
                }
                if (res.getString(res.getColumnIndex("urgent")) == "1") {
                    chkAcil.setChecked(true);
                }
            }
            res.close();
        }

        editBaslangic.setOnClickListener(new View.OnClickListener() {
            Calendar calBas = Calendar.getInstance();
            int day = calBas.get(Calendar.DAY_OF_MONTH);
            int month = calBas.get(Calendar.MONTH);
            int year = calBas.get(Calendar.YEAR);

            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(EditJob.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editBaslangic.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });

        editBitis.setOnClickListener(new View.OnClickListener() {
                Calendar calBas = Calendar.getInstance();
                int day = calBas.get(Calendar.DAY_OF_MONTH);
                int month = calBas.get(Calendar.MONTH);
                int year = calBas.get(Calendar.YEAR);

                @Override
                public void onClick(View v) {
                    DatePickerDialog datePicker = new DatePickerDialog(EditJob.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    editBitis.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }, year, month, day);
                    datePicker.show();
                }
        });

        chkHatirlatici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkHatirlatici.isChecked()) {
                    editRemindDate.setVisibility(View.VISIBLE);
                    editRemindTime.setVisibility(View.VISIBLE);
                }
                else {
                    editRemindDate.setVisibility(View.INVISIBLE);
                    editRemindTime.setVisibility(View.INVISIBLE);
                    editRemindTime.setText("");
                    editRemindDate.setText("");
                }
            }
        });


        editRemindDate.setOnClickListener(new View.OnClickListener() {
                Calendar calBas = Calendar.getInstance();
                int day = calBas.get(Calendar.DAY_OF_MONTH);
                int month = calBas.get(Calendar.MONTH);
                int year = calBas.get(Calendar.YEAR);

                @Override
                public void onClick(View v) {
                    DatePickerDialog datePicker = new DatePickerDialog(EditJob.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    editRemindDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }, year, month, day);
                    datePicker.show();
                }
        });

        editRemindTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minutes = c.get(Calendar.MINUTE);
                TimePickerDialog picker = new TimePickerDialog(EditJob.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                editRemindTime.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        duzenleBtn.setOnClickListener (
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editName != null) {
                        SQLiteDatabase dbs = myDb.getWritableDatabase();
                        if (editJobName.equals("xeklex")) {
                            Cursor curExist = dbs.rawQuery("select * from jobs where name = \'" + editName.getText().toString() + "\'", null);
                            if (curExist.getCount() > 0) {
                                Toast.makeText(getApplicationContext(), "Görev ismi mevcut, lütfen değiştirin", Toast.LENGTH_LONG).show();
                            } else {
                                boolean databaseIslendi = myDb.insertJob(editName.getText().toString(),
                                        editDescription.getText().toString(),
                                        editBaslangic.getText(),
                                        editBitis.getText(),
                                        chkOnemli.isChecked(),
                                        chkAcil.isChecked());
                                if (databaseIslendi) {
                                    Toast.makeText(getApplicationContext(), "Görev İşlendi", Toast.LENGTH_LONG).show();
                                    finishAndRemoveTask();

                                } else {
                                    Toast.makeText(getApplicationContext(), "Görev İşlenemedi", Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {
                            boolean databaseIslendi = myDb.updateJob(jobId, editName.getText().toString(),
                                    editDescription.getText().toString(),
                                    editBaslangic.getText(),
                                    editBitis.getText(),
                                    chkOnemli.isChecked(),
                                    chkAcil.isChecked());
                            if (databaseIslendi) {
                                Toast.makeText(getApplicationContext(), "Görev güncellendi", Toast.LENGTH_LONG).show();
                                finishAndRemoveTask();

                            } else {
                                Toast.makeText(getApplicationContext(), "Görev güncellenemedi", Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Lütfen bir görev ismi giriniz", Toast.LENGTH_LONG).show();
                    }

                }
            });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        takvim.set(Calendar.YEAR, year);
        takvim.set(Calendar.MONTH, month);
        takvim.set(Calendar.DAY_OF_MONTH, dayOfMonth);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clear();
        finish();

    }

    public void clear() {
        editName.setText("");
        editDescription.setText("");
        editBaslangic.setText("");
        editBitis.setText("");
        editRemindDate.setText("");
        editRemindTime.setText("");
        chkHatirlatici.setChecked(false);
        chkOnemli.setChecked(false);
        chkAcil.setChecked(false);
    }
}