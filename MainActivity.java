package com.example.notesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btn_save;
    private Button share;
    private EditText add_note;
    private TextView view_note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Share), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_save=findViewById(R.id.save);
        share=findViewById(R.id.share);
        add_note=findViewById(R.id.add_note);
        view_note=findViewById(R.id.view_note);

        loadNote();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note=add_note.getText().toString();

                if(!note.isEmpty())
                {
                    saveNote(note);
                    view_note.setText(note);
                    add_note.setText("");
                }

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareNote();
            }
        });
    }
    private void loadNote()
    {
        SharedPreferences pref=getApplicationContext().getSharedPreferences("MyPerf",0);
        String note=pref.getString("note", "Cannot Recieve the note");
        view_note.setText(note);

    }

    private void saveNote(String n)
    {
        SharedPreferences pref=getApplicationContext().getSharedPreferences("MyPerf",0);
        SharedPreferences.Editor ed= pref.edit();
        ed.putString("note",n);
        ed.apply();
    }
    private void shareNote()
    {
        String note=view_note.getText().toString();
        if(!note.isEmpty())
        {
            Intent in=new Intent(Intent.ACTION_SEND);
            in.setType("text/plain");
            in.putExtra(Intent.EXTRA_TEXT,note);   //Puts the note text into the intent to be shared.
            startActivity(Intent.createChooser(in,"share via"));  //Starts an activity that allows the user to choose which app to use for sharing the note.
        }

    }

}