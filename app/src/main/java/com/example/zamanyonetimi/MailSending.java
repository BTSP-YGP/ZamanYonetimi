package com.example.zamanyonetimi;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
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
    private EditText to;
    private EditText editTextSubject;
    private EditText editTextMessage;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_sending);

        Bundle b = getIntent().getExtras();
        assert b != null;
        String editJobName = b.getString("editJobName");


        to = findViewById(R.id.editTextto);
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextMessage = findViewById(R.id.editTextMessage);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
        ArrayList<String> data = new ArrayList<>();
        DatabaseHelper myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getWritableDatabase();
        @SuppressLint("Recycle") Cursor res = db.rawQuery("select * from jobs where name = \'"+ editJobName +"\'", null);
        while (res.moveToNext()) {
            if (!res.getString(res.getColumnIndex("name")).equals("")) {
                data.add("Görevin Adı: "+res.getString(res.getColumnIndex("name"))+ "\n");
            }
            if (!res.getString(res.getColumnIndex("description")).equals("")) {
                data.add("Görevin Tanımı: "+res.getString(res.getColumnIndex("description"))+ "\n");
            }
            if (!res.getString(res.getColumnIndex("baslangic")).equals("")) {
                data.add("Görevin Başlangıç Tarihi: "+res.getString(res.getColumnIndex("baslangic"))+ "\n");
            }
            if (!res.getString(res.getColumnIndex("bitis")).equals("")) {
                data.add("Görevin Bitiş Tarihi: "+res.getString(res.getColumnIndex("bitis"))+ "\n");
            }
        }
        editTextMessage.setText("Sayın \n size delege edilen görevin detayları şu şekildedir: \n "+Arrays.toString(data.toArray())
                .replace("[", "")
                .replace("]", "")
                .replace(",", ""));
        editTextSubject.setText(editJobName + " Görevi Size Delege Edilmiştir");
    }


    private void sendMail(){

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
