package com.example.load_save_share;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private Button buttonShare, buttonLoad, buttonSave;
    private EditText editText;
    private TextView textView, buttonNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonLoad = findViewById(R.id.buttonLoad);
        buttonSave = findViewById(R.id.buttonSave);
        buttonShare = findViewById(R.id.buttonShare);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        String line = "0";
        try {
            FileOutputStream fileOutput = openFileOutput("my_file.xml", MODE_PRIVATE);
            fileOutput.write(line.getBytes());
            fileOutput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fileInput = openFileInput("my_file.xml");
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInput);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuffer stringBuffer = new StringBuffer();
                    String lines;
                    while((lines = bufferedReader.readLine()) != null){
                        stringBuffer.append(lines + "\n");
                    }
                    textView.setText(stringBuffer.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String line = editText.getText().toString();
                try {
                    FileOutputStream fileOutput = openFileOutput("my_file.xml", MODE_PRIVATE);
                fileOutput.write(line.getBytes());
                fileOutput.close();
                editText.setText("");
                Toast.makeText(MainActivity.this, "our string saved", Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            }
        });
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStorageDirectory(), "my_file.txt");
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("*/txt");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(Intent.EXTRA_STREAM, file);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
//                File file = new File("my_file.xml");
//                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                sharingIntent.setType("xml/*");
//                sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getAbsolutePath()));
//                startActivity(Intent.createChooser(sharingIntent, "share file wi"));
            }
        });
    }
}
