package com.example.updatelist;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Student> {

    public ItemAdapter(@NonNull Context context, int resource, ArrayList<Student> items){
        super(context, resource, items);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
        }
        ImageView picture = convertView.findViewById(R.id.picture);
        TextView nameText = convertView.findViewById(R.id.nameText);
        TextView course = convertView.findViewById(R.id.courseText);
        Student item = (Student) getItem(position);

        if (item.getUriString() != null){
            picture.setImageURI(Uri.parse(item.getUriString()));
        } else {
            picture.setImageResource(item.getImageId());
        }

//        picture.setImageResource(item.getImageId());

        nameText.setText(item.getName());
        //mag null diri
        Log.e("course",item.getCourse());

        course.setText(item.getCourse());
        return convertView;
    }

}
