package com.example.whatsapp_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnSendMessage;
    EditText etMessage, etMobileNumber;
    String strMessage, strMobileNumber = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSendMessage = findViewById(R.id.btnSendMessage);
        etMessage = findViewById(R.id.etMessage);
        etMobileNumber = findViewById(R.id.etMobileNumber);

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strMessage = etMessage.getText().toString();
                strMobileNumber = etMobileNumber.getText().toString();

                //+91 and 91 resolved i.e. country code
                if(strMobileNumber.length()==12)
                    strMobileNumber=strMobileNumber.substring(2);
                else if(strMobileNumber.length()==13)
                    strMobileNumber = strMobileNumber.substring(3);


                if (etMessage.getText().toString().isEmpty() && etMobileNumber.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter Mobile Number and message!!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean installed = appInstalledOrNot("com.whatsapp");
                    if (installed){
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=91" + strMobileNumber
                                + "&text=" + strMessage));
                        startActivity(intent);
                        etMobileNumber.setText("");
                        etMessage.setText("");// to clear edit text when you send message on whatsapp

                    }else {
                        Toast.makeText(MainActivity.this, "WhatsApp not installed on your Device", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager packageManager = getPackageManager();
        boolean appInstalled;
        try {
            packageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            appInstalled = true;
        }catch (PackageManager.NameNotFoundException e){
            appInstalled = false;
        }
        return appInstalled;
    }

}