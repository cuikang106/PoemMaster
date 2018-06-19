package com.example.cuikang.poemmaster.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.cuikang.poemmaster.R;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IDesign_Lab on 2018/6/13.
 */

public class RecordAdapter extends ArrayAdapter<String> {
    private int resourceId;
    public RecordAdapter (Context context, int textViewResourceId, List<String> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String record = getItem(position);
        List<String> subject= Arrays.asList(record.split(","));
        View view;
        RecordAdapter.ViewHolder viewHolder;
        if (convertView == null)
        {
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new RecordAdapter.ViewHolder();
            viewHolder.frontSentence = (TextView) view.findViewById(R.id.front_sentence);
            viewHolder.behindSentence = (TextView) view.findViewById(R.id.behind_sentence);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder = (RecordAdapter.ViewHolder) view.getTag();
        }

        viewHolder.frontSentence.setText(subject.get(0));
        viewHolder.behindSentence.setText(subject.get(1));

        if (Integer.parseInt(subject.get(2))==1) {
            viewHolder.frontSentence.setTextColor(Color.BLACK);
            viewHolder.behindSentence.setTextColor(Color.GRAY);
            if (subject.size()==4)
                viewHolder.frontSentence.setTextColor(Color.RED);
        }
        else{
            viewHolder.behindSentence.setTextColor(Color.BLACK);
            viewHolder.frontSentence.setTextColor(Color.GRAY);
            if (subject.size()==4)
                viewHolder.behindSentence.setTextColor(Color.RED);
        }

        return view;
    }

    class ViewHolder{
        TextView frontSentence;
        TextView behindSentence;
    }
}