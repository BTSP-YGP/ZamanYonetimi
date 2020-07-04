package com.example.zamanyonetimi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.os.Bundle;

public class MailSending extends AppCompatActivity {
    EditText to;
    EditText editTextSubject;
    EditText editTextMessage;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_sending);

        to = findViewById(R.id.editTextto);
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextMessage = findViewById(R.id.editTextMessage);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }

        });
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
