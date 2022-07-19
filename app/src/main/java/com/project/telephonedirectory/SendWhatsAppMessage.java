package com.project.telephonedirectory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendWhatsAppMessage extends AppCompatActivity {
    private Persons persons;
    private TextView twPNumber;
    private EditText editTextWpMessage;
    private Button btnSendWpMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_whats_app_message);

        twPNumber = findViewById(R.id.twPNumber);
        editTextWpMessage = findViewById(R.id.editTextWpMessage);
        btnSendWpMessage = findViewById(R.id.btnSendWpMessage);

        persons = (Persons) getIntent().getSerializableExtra("person_object");

        twPNumber.setText(persons.getPhone_number());

        btnSendWpMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNumber = twPNumber.getText().toString();
                String message = editTextWpMessage.getText().toString();

                boolean installed = appInstalledOrNot("com.whatsapp");

                if (installed) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+9"+ mobileNumber + "&text=" + message));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(SendWhatsAppMessage.this,"Whatsapp not installed on your device",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private boolean appInstalledOrNot(String url) {
        PackageManager packageManager = getPackageManager();
        boolean app_installed;

        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}