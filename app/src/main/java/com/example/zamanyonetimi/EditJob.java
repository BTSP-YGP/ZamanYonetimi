package com.example.zamanyonetimi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Calendar;


public class EditJob extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private DatabaseHelper myDb;
    private EditText editName, editDescription, editBaslangic, editBitis;
    private CheckBox chkOnemli, chkAcil;
    private Button hatirlaticiBtn;
    private Calendar takvim = Calendar.getInstance();
    private Integer jobId;
    private String editJobName;
    int HATIRLATICI_ACTIVITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        assert b != null;
        editJobName = b.getString("editJobName");

        setContentView(R.layout.activity_edit_job);

        myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getWritableDatabase();


        editName = findViewById(R.id.editTextName);
        editDescription = findViewById(R.id.editTextDescription);
        editBaslangic = findViewById(R.id.editTextBaslangic);
        editBitis = findViewById(R.id.editTextBitis);
        chkOnemli = findViewById(R.id.checkBoxOnemli);
        chkAcil = findViewById(R.id.checkBoxAcil);
        hatirlaticiBtn = findViewById(R.id.buttonHatirlatici);
        FloatingActionButton duzenleBtn = findViewById(R.id.duzenletusu);

        if (!editJobName.equals("xeklex")) {
            Cursor res = db.rawQuery("select * from jobs where name = \'"+ editJobName+"\'", null);
            while (res.moveToNext()) {
                jobId = res.getInt(res.getColumnIndex("jobid"));
                if (!res.getString(res.getColumnIndex("name")).equals("")) {
                    editName.setText(res.getString(res.getColumnIndex("name")));
                }
                if (!res.getString(res.getColumnIndex("description")).equals("")) {
                    editDescription.setText(res.getString(res.getColumnIndex("description")));
                }
                if (!res.getString(res.getColumnIndex("baslangic")).equals("")) {
                    editBaslangic.setText(res.getString(res.getColumnIndex("baslangic")));
                }
                if (!res.getString(res.getColumnIndex("bitis")).equals("")) {
                    editBitis.setText(res.getString(res.getColumnIndex("bitis")));
                }
                if (res.getString(res.getColumnIndex("important")).equals("1")) {
                    chkOnemli.setChecked(true);
                }
                if (res.getString(res.getColumnIndex("urgent")).equals("1")) {
                    chkAcil.setChecked(true);
                }
            }
            res.close();
        }

        editName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!editName.getText().toString().equals("")) {
                            hatirlaticiBtn.setEnabled(true);
                    }
                }
            }
        });

        editBaslangic.setOnClickListener(new View.OnClickListener() {
            Calendar calBas = Calendar.getInstance();
            int day = calBas.get(Calendar.DAY_OF_MONTH);
            int month = calBas.get(Calendar.MONTH);
            int year = calBas.get(Calendar.YEAR);

            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(EditJob.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
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
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    editBitis.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }, year, month, day);
                    datePicker.show();
                }
        });

        hatirlaticiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   Intent intent=new Intent(EditJob.this, MainReminder.class);
                   startActivityForResult(intent, HATIRLATICI_ACTIVITY);
            }
        });

        duzenleBtn.setOnClickListener (
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editName != null) {
                        SQLiteDatabase dbs = myDb.getWritableDatabase();
                        if (editJobName.equals("xeklex")) {
                            @SuppressLint("Recycle") Cursor curExist = dbs.rawQuery("select * from jobs where name = \'" + editName.getText().toString() + "\'", null);
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
                                    Intent returnIntent = new Intent();
                                    setResult(Activity.RESULT_OK,returnIntent);
                                    returnIntent.putExtra("status","ekleme");
                                    finish();

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
                                Intent returnIntent = new Intent();
                                setResult(Activity.RESULT_OK,returnIntent);
                                returnIntent.putExtra("status","duzenle");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == HATIRLATICI_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                String resDate =data.getStringExtra("date");
                String resTime =data.getStringExtra("time");
                SQLiteDatabase dbs = myDb.getWritableDatabase();
                @SuppressLint("Recycle") Cursor curExist = dbs.rawQuery("select * from reminders where name = \'" + editName.getText().toString() + "\'", null);
                if (curExist.getCount() > 0) {
                    myDb.updateReminder(editName.getText().toString(), resDate, resTime);
                    Toast.makeText(getApplicationContext(), "Hatirlatici "+resDate+" günü saat "+resTime+" olarak ayarlandı", Toast.LENGTH_LONG).show();
                } else {
                    if (myDb.insertReminder(editName.getText().toString(), resDate, resTime)) {
                        Toast.makeText(getApplicationContext(), "Hatirlatici "+resDate+" günü saat "+resTime+" olarak güncellendi", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Hatirlatici güncellenemedi", Toast.LENGTH_LONG).show();
                    }
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Hatirlatici Ayarlanmadi", Toast.LENGTH_LONG).show();
            }
        }
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
        chkOnemli.setChecked(false);
        chkAcil.setChecked(false);
    }
}