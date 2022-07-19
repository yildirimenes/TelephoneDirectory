package com.project.telephonedirectory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SendEmail extends AppCompatActivity {
    private Persons persons;
    private TextView twEmailAdress;
    private EditText editTextSubject;
    private EditText editTextEmailMessage;
    private Button btnSendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        twEmailAdress = findViewById(R.id.twEmailAdress);
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextEmailMessage = findViewById(R.id.editTextEmailMessage);
        btnSendEmail = findViewById(R.id.btnSendEmail);

        persons = (Persons) getIntent().getSerializableExtra("person_object");

        twEmailAdress.setText(persons.getPerson_email());

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
                /*
                Intent intent = new Intent(Intent.ACTION_VIEW
                        , Uri.parse("mailto : " + twEmailAdress.getText().toString()));
                        intent.putExtra(Intent.EXTRA_SUBJECT,editTextSubject.getText().toString());
                        intent.putExtra(Intent.EXTRA_TEXT,editTextEmailMessage.getText().toString());
                        startActivity(intent);
                 */
                /*
                String toEemail = twEmailAdress.getText().toString();
                String subject = editTextSubject.getText().toString();
                String message = editTextEmailMessage.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, toEemail);
                intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                intent.putExtra(Intent.EXTRA_TEXT,message);
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent,"choose an email cliet"));
                 */
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
}