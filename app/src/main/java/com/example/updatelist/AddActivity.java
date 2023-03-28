package com.example.updatelist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

public class AddActivity extends AppCompatActivity {
    ImageView img;
    EditText layla;
    Button hanabi;
    String course;
    Intent intent;
    Student student;

    Button btncancel;
    Uri uti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        intent = getIntent();
        img=findViewById(R.id.iv);
        layla=findViewById(R.id.layla);
        btncancel = findViewById(R.id.cancel);
        Spinner spin = (Spinner) findViewById (R.id.spin);
        hanabi=findViewById(R.id.buttonCheck);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.courses,
                        android.R.layout.simple_spinner_item);
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(staticAdapter);

        student =(Student) intent.getSerializableExtra("student");

        if(student!=null){
            uti = Uri.parse(student.getUriString());
            img.setImageURI(uti);
            layla.setText(student.getName());
            for (int i=0;i<spin.getCount();i++){
                if(spin.getItemAtPosition(i).toString().equalsIgnoreCase(student.getCourse())){
                    spin.setSelection(i);
                    break;
                }


            }
        }

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                course= adapterView.getItemAtPosition(i).toString();
//                Intent intent = new Intent();
//                intent.putExtra("course",course);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 5);
            }
        });

        hanabi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(AddActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 23);
                        return;
                    }
                }

                Student tidert = new Student(uti,layla.getText().toString(),course);

                if(student!=null){
                    tidert.setStudentId(student.getStudentId());

                }


                Intent intent = new Intent();
                intent.putExtra("tidert", tidert);
                setResult(RESULT_OK, intent);
                finish();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 5 && resultCode==RESULT_OK && data!=null){
            uti=data.getData();
            img.setImageURI(uti);
        }
    }
}