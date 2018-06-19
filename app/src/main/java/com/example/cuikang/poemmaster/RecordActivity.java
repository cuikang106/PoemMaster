package com.example.cuikang.poemmaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.example.cuikang.poemmaster.adapter.RecordAdapter;
import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Intent intent = getIntent();
        ArrayList<String> data = intent.getStringArrayListExtra("extra_data");
        ArrayList<Integer> sequence = intent.getIntegerArrayListExtra("wrong_sequence");
        for(int wrong:sequence) {
            data.set(wrong-1,data.get(wrong-1)+",wrong");
        }

        RecordAdapter adapter = new RecordAdapter(this, R.layout.record_item, data);
        ListView listview = (ListView) findViewById(R.id.record_list);
        listview.setAdapter(adapter);
    }
}
