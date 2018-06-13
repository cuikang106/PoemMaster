package com.example.cuikang.poemmaster.adapter;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.example.cuikang.poemmaster.R;
import com.example.cuikang.poemmaster.bean.PoemBean;

import java.util.List;


/**
 * Created by IDesign_Lab on 2018/6/9.
 */

//public class PoemAdapter extends RecyclerView.Adapter<PoemAdapter.ViewHolder>{
//
//    private List<PoemBean> myPoemList;
//
//    static class ViewHolder extends RecyclerView.ViewHolder{
//        View poemView;
//        TextView poemTitle;
//        TextView poemAuthor;
//        TextView poemSequence;
//
//        public ViewHolder (View view){
//            super(view);
//            poemView = view;
//            poemTitle = (TextView) view.findViewById(R.id.poem_title);
//            poemAuthor = (TextView) view.findViewById(R.id.poem_author);
//            poemSequence = (TextView) view.findViewById(R.id.poem_sequence);
//        }
//    }
//
//    public PoemAdapter(List<PoemBean> poemList){
//        myPoemList=poemList;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poem_item,parent,false);
//        final ViewHolder holder = new ViewHolder(view);
//        holder.poemView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                int position = holder.getAdapterPosition();
//                PoemBean poem = myPoemList.get(position);
////                Toast.makeText(v.getContext(), poem.getTitle(), Toast.LENGTH_LONG).show();
//
//            }
//
//        });
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position){
//        PoemBean poem = myPoemList.get(position);
//        holder.poemTitle.setText(poem.getTitle());
//        holder.poemAuthor.setText(poem.getAuthor());
//        holder.poemSequence.setText(poem.getSequence());
//    }
//
//    @Override
//    public int getItemCount(){
//        return myPoemList.size();
//    }
//}

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
