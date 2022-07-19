package com.project.telephonedirectory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendSMS extends AppCompatActivity {

    private Persons persons;
    TextView twMessageName;
    TextView twMessagePnumber;
    Button btnSendMessage;
    EditText editTextMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        twMessageName = findViewById(R.id.twMessageName);
        twMessagePnumber = findViewById(R.id.twMessagePnumber);
        btnSendMessage = findViewById(R.id.btnSendMessage);
        editTextMessage = findViewById(R.id.editTextMessage);

        persons = (Persons) getIntent().getSerializableExtra("person_object");


        twMessageName.setText(persons.getPerson_name());
        twMessagePnumber.setText(persons.getPhone_number());

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(SendSMS.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    sendMessage();
                }
                else {
                    ActivityCompat.requestPermissions(SendSMS.this,new String[] {Manifest.permission.SEND_SMS},100);
                }

            }
        });




    }

    private void sendMessage() {
        String sPhone = twMessagePnumber.getText().toString().trim();
        String sMessage = editTextMessage.getText().toString().trim();

        if (!sPhone.equals("") && !sMessage.equals("")) {

            SmsManager smsManager = SmsManager.getDefault();

            smsManager.sendTextMessage(sPhone,null,sMessage,null,null);

            Toast.makeText(getApplicationContext(),"SMS sent successfully!",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"Enter value first.",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendMessage();
        }
        else {
            Toast.makeText(getApplicationContext(),"Permission Denied!",Toast.LENGTH_SHORT).show();
        }
    }
}