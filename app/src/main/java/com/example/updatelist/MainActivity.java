package com.example.updatelist;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //String data;
    ListView lv;
    EditText txtSearch;
    ArrayList<Student> list = new ArrayList<Student>();
    ArrayList<Student> kape = new ArrayList<Student>();
    StudentDatabase db;

    ArrayAdapter<Student> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new StudentDatabase(this);

        for (Student student : db.getStudents()) {
            list.add(student);
            kape.add(student);
        }

        adapter = new ItemAdapter(this, android.R.layout.simple_list_item_1, kape);

        lv = findViewById(R.id.listView1);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);
        txtSearch = findViewById(R.id.searchText);

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                kape.clear();

                for (Student tidert: list){
                    if(tidert.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        kape.add(tidert);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        this.getMenuInflater().inflate(R.menu.mymenu,menu);
        //Intent intent = new Intent(this, AddActivity.class);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        Intent intent = new Intent(this, AddActivity.class);
       // Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 3);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        String title = item.getTitle().toString();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Student student = adapter.getItem(info.position);

        switch (title) {
            case "EDIT":

                Log.e("SADCASDC", student.getStudentId()  + "");

                Intent intent = new Intent(this, AddActivity.class);
                intent.putExtra("student", student);
                startActivityForResult(intent, 150);

                break;

            case "DELETE":
                adapter.remove(student);
                db.removeStudent(student);
                break;

            case "CALL":
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
                String num = student.getName().toLowerCase();
//                for(char i:num.toCharArray()){
//                    if(i>='a'||i<='z'){
//                        Toast.makeText(this, "This is not a valid number", Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//                }
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + num));
                startActivity(callIntent);

                break;

        }

        return super.onContextItemSelected(item);
    }



    @Override

    protected  void  onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode== Activity.RESULT_OK){
            if(requestCode==3){
                Student tidert =(Student) data.getSerializableExtra("tidert");

                tidert.setStudentId(db.addStudent(tidert));
                list.add(tidert);
                kape.add(tidert);
            }

            if(requestCode == 150){
                Student tidert =(Student) data.getSerializableExtra("tidert");

                Log.e("asdasd", tidert.getStudentId()  + "");
                db.editStudent(tidert);

                list.clear();
                kape.clear();

                for (Student student : db.getStudents()) {
                    list.add(student);
                    kape.add(student);
                }
            }

            adapter.notifyDataSetChanged();
        }
    }
}