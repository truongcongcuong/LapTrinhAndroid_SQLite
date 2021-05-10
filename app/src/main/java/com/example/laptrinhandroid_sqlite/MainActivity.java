package com.example.laptrinhandroid_sqlite;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText edt_main_location;
    Button btt_main_save, btt_main_cancel;
    RecyclerView rcy_main_location;
    LocationAdapter adapter;
    DatabaseHandler databaseHandler;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_main_location = findViewById(R.id.edt_main_Location);
        btt_main_save = findViewById(R.id.btt_main_save);
        btt_main_cancel = findViewById(R.id.btt_main_cancel);
        rcy_main_location = findViewById(R.id.rcy_main_location);
        databaseHandler = new DatabaseHandler(this);
        adapter = new LocationAdapter(this,databaseHandler.getLocations());
        rcy_main_location.setAdapter(adapter);
        rcy_main_location.setLayoutManager(new GridLayoutManager(this,1));


        btt_main_save.setText("Save");
        btt_main_cancel.setText("Cancel");

        btt_main_save.setOnClickListener(v -> {
            databaseHandler.addNewLocation(new Location(edt_main_location.getText().toString()));
            adapter.dataChange(databaseHandler.getLocations());
        });

        btt_main_cancel.setOnClickListener(v -> edt_main_location.setText(""));

    }
}