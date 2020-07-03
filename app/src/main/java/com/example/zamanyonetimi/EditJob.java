package com.example.zamanyonetimi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.example.zamanyonetimi.ui.Inbox.InboxAdapter;
import com.example.zamanyonetimi.ui.Inbox.InboxFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.text.DateFormat;
import java.util.Calendar;

public class EditJob extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    DatabaseHelper myDb;
    EditText editName, editDescription, editBaslangic, editBitis, editRemindDate, editRemindTime;
    CheckBox chkHatirlatici, chkOnemli, chkAcil;
    FloatingActionButton duzenleBtn;
    Calendar takvim = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        String editJobName = b.getString("editJobName");
        Integer jobId;
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
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                editBaslangic.setText(DateFormat.getDateInstance().format(takvim.getTime()));
            }
        });

        editBitis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                editBitis.setText(DateFormat.getDateInstance().format(takvim.getTime()));
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

        editRemindTime.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String text = editRemindTime.getText().toString();
                int textlength = editRemindTime.getText().length();

                if(textlength == 2)
                {
                    editRemindTime.setText(text + ":");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        duzenleBtn.setOnClickListener (
            new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    if (editName != null) {

                        boolean databaseIslendi = myDb.insertJob(editName.getText().toString(),
                                editDescription.getText().toString(),
                                editBaslangic.getText(),
                                editBitis.getText(),
                                chkOnemli.isChecked(),
                                chkAcil.isChecked());
                        if (databaseIslendi) {
                            Toast.makeText(getApplicationContext(), "Görev İşlendi", Toast.LENGTH_LONG).show();
                            finishAndRemoveTask();
                            InboxFragment frg = new InboxFragment();
                            frg.update();
                        } else {
                             Toast.makeText(getApplicationContext(), "Görev İşlenemedi", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setCancelable(true);
                        builder.setTitle("Hata");
                        builder.setMessage("Lutfen gorev ismi giriniz");
                        builder.show();
                    }
                }
            }
        );
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
