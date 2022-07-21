package com.project.telephonedirectory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SendEmail extends AppCompatActivity {
    private Persons persons;
    private TextView twPersonName;
    private TextView twEmailAdress;
    private EditText editTextSubject;
    private EditText editTextEmailMessage;
    private Button btnSendEmail;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        twPersonName = findViewById(R.id.twPersonName);
        twEmailAdress = findViewById(R.id.twEmailAdress);
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextEmailMessage = findViewById(R.id.editTextMessage);
        btnSendEmail = findViewById(R.id.btnSendMessage);
        toolbar = findViewById(R.id.toolbar);


        persons = (Persons) getIntent().getSerializableExtra("person_object");

        twPersonName.setText(persons.getPerson_name());
        twEmailAdress.setText(persons.getPerson_email());

        toolbar.setTitle("Send Email");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);


        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

    }


    private void sendEmail() {
        String recipienList = twEmailAdress.getText().toString();
        String[] recipients = recipienList.split(",");
        String subject = editTextSubject.getText().toString();
        String message = editTextEmailMessage.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"choose an email cliet"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


}