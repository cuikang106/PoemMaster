package com.example.cuikang.poemmaster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.cuikang.poemmaster.R;
import com.example.cuikang.poemmaster.bean.PoemBean;
import java.util.List;


/**
 * Created by IDesign_Lab on 2018/6/9.
 */

public class PoemAdapter extends ArrayAdapter<PoemBean>{
    private int resourceId;
    public PoemAdapter (Context context, int textViewResourceId, List<PoemBean> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        PoemBean poem = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null)
        {
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.poemTitle = (TextView) view.findViewById(R.id.poem_title);
            viewHolder.poemAuthor = (TextView) view.findViewById(R.id.poem_author);
            viewHolder.poemSequence = (TextView) view.findViewById(R.id.poem_sequence);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.poemTitle.setText(poem.getTitle());
        viewHolder.poemAuthor.setText(poem.getAuthor());
        viewHolder.poemSequence.setText(poem.getSequence());
        return view;
    }

    class ViewHolder{
        TextView poemAuthor;
        TextView poemTitle;
        TextView poemSequence;
    }
}
