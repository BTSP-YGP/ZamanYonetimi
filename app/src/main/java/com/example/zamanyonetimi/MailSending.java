package com.example.zamanyonetimi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class MailSending extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText to;
    EditText editTextSubject;
    EditText editTextMessage;
    Button button;
    String editJobName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_sending);

        Bundle b = getIntent().getExtras();
        editJobName = b.getString("editJobName");


        to = findViewById(R.id.editTextto);
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextMessage = findViewById(R.id.editTextMessage);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
        ArrayList<String> data = new ArrayList<String>();
        myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getWritableDatabase();
        Cursor res = db.rawQuery("select * from jobs where name = \'"+ editJobName+"\'", null);
        while (res.moveToNext()) {
            if (res.getString(res.getColumnIndex("name")) != "") {
                data.add("Görevin Adı: "+res.getString(res.getColumnIndex("name"))+ "\n");
            }
            if (res.getString(res.getColumnIndex("description")) != "") {
                data.add("Görevin Tanımı: "+res.getString(res.getColumnIndex("description"))+ "\n");
            }
            if (res.getString(res.getColumnIndex("baslangic")) != "") {
                data.add("Görevin Başlangıç Tarihi: "+res.getString(res.getColumnIndex("baslangic"))+ "\n");
            }
            if (res.getString(res.getColumnIndex("bitis")) != "") {
                data.add("Görevin Bitiş Tarihi: "+res.getString(res.getColumnIndex("bitis"))+ "\n");
            }
        }
        editTextMessage.setText("Sayın \n size delege edilen görevin detayları şu şekildedir: \n "+Arrays.toString(data.toArray())
                .replace("[", "")
                .replace("]", "")
                .replace(",", ""));
        //String list = Arrays.toString(customers.toArray()).replace("[", "").replace("]", "");
        editTextSubject.setText(editJobName + " Görevi Size Delege Edilmiştir");
    }


    public void sendMail(){

        String recipientList=to.getText().toString();
        String [] recipients=recipientList.split(",");

        String subject=editTextSubject.getText().toString();
        String message=editTextMessage.getText().toString();

        Intent intent=new Intent (Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"e-mail seçiniz"));


    }
}
