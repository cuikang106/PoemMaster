package com.example.cuikang.poemmaster.Fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuikang.poemmaster.CommentActivity;
import com.example.cuikang.poemmaster.DatabaseUtil.HandlePoem;
import com.example.cuikang.poemmaster.MainActivity;
import com.example.cuikang.poemmaster.R;
import com.example.cuikang.poemmaster.ShowCommentActivity;
import com.example.cuikang.poemmaster.UserManage.UserManage;

import java.util.Random;

public class BrowseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private SQLiteDatabase db;
    private int sequence;



    public BrowseFragment() {
        // Required empty public constructor
    }

    //创建并返回一个实例
    public static BrowseFragment newInstance(String param1) {
        BrowseFragment fragment = new BrowseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //创建fragment对应的视图，并返回给调用者
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //页面是静态的，不需要调整
        return inflater.inflate(R.layout.fragment_browse, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Random r = new Random();
        sequence = r.nextInt(325);
        EditText editTxt = (EditText) getActivity().findViewById(R.id.edit_text);
        MainActivity activity = (MainActivity) getActivity();
        if (activity.exchange!=-1)
            sequence=activity.exchange+1;//获得activity中的exchange变量，用于索引诗文，可用来作为
        editTxt.setText(sequence + "");
        displayFunction();

        Button displayBt = (Button) getActivity().findViewById(R.id.display_button);
        displayBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTxt = (EditText) getActivity().findViewById(R.id.edit_text);
                if (editTxt.getText().toString().trim().isEmpty())
                    Toast.makeText(getActivity().getApplicationContext(), "请先输入编号", Toast.LENGTH_LONG).show();
                else {
                    sequence = Integer.parseInt(editTxt.getText().toString());
                    if (sequence > 324 || sequence < 1)
                        Toast.makeText(getActivity().getApplicationContext(), "请输入范围内的数字(1~324)!", Toast.LENGTH_LONG).show();
                    else {
                        displayFunction();
                    }
                }
            }
        });

        Button lastBt = (Button) getActivity().findViewById(R.id.last_button);
        lastBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTxt = (EditText) getActivity().findViewById(R.id.edit_text);
                if (editTxt.getText().toString().trim().isEmpty())
                    Toast.makeText(getActivity().getApplicationContext(), "请先输入编号", Toast.LENGTH_LONG).show();
                else {
                    sequence = Integer.parseInt(editTxt.getText().toString());
                    if (sequence == 1)
                        Toast.makeText(getActivity().getApplicationContext(), "当前为第一首诗", Toast.LENGTH_LONG).show();
                    else {
                        sequence -= 1;
                        editTxt.setText(sequence + "");
                        displayFunction();
                    }
                }
            }
        });

        Button nextBt = (Button) getActivity().findViewById(R.id.next_button);
        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTxt = (EditText) getActivity().findViewById(R.id.edit_text);
                if (editTxt.getText().toString().trim().isEmpty())
                    Toast.makeText(getActivity().getApplicationContext(), "请先输入编号", Toast.LENGTH_LONG).show();
                else {
                    sequence = Integer.parseInt(editTxt.getText().toString());
                    if (sequence == 324)
                        Toast.makeText(getActivity().getApplicationContext(), "当前为最后一首诗", Toast.LENGTH_LONG).show();
                    else {
                        sequence += 1;
                        editTxt.setText(sequence + "");
                        displayFunction();
                    }
                }
            }
        });

        Button showCommentBt=getActivity().findViewById(R.id.btn_showComment);
        showCommentBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShowComment();
            }
        });

        Button commentBt=getActivity().findViewById(R.id.btn_comment);
        commentBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onComment();
            }
        });
    }

    public void displayFunction() {
        MainActivity activity = (MainActivity) getActivity();

        TextView poemTitle = (TextView) getActivity().findViewById(R.id.poem_title);
        TextView poemAuthor = (TextView) getActivity().findViewById(R.id.poem_author);
        TextView poemParagraphs = (TextView) getActivity().findViewById(R.id.poem_paragraphs);
        TextView poemStrains = (TextView) getActivity().findViewById(R.id.poem_strains);

        poemTitle.setText(activity.poemList.get(sequence-1).getTitle());
        poemAuthor.setText(activity.poemList.get(sequence-1).getAuthor());
        poemParagraphs.setText(HandlePoem.combinePoem(activity.poemList.get(sequence-1).getParagraphs()));
        poemStrains.setText(HandlePoem.combinePoem(activity.poemList.get(sequence-1).getStrains()));
    }

    public void onShowComment(){
        Intent intentShowComment=new Intent(getActivity(), ShowCommentActivity.class);
        intentShowComment.putExtra("poemID",String.valueOf(sequence));
        startActivity(intentShowComment);
    }

    public void onComment(){
        EditText editText=getActivity().findViewById(R.id.edt_comment);
        String commentText=editText.getText().toString();
        if(commentText.isEmpty()) {
            Toast.makeText(getActivity(), "请输入内容", Toast.LENGTH_LONG).show();
        }else if(!UserManage.getInstance().hasUserInfo(getActivity())){
            Toast.makeText(getActivity(), "游客不能发表评论，请先登录", Toast.LENGTH_LONG).show();
        }else{
            Intent intentComment=new Intent(getActivity(),CommentActivity.class);
            intentComment.putExtra("commentText",commentText);
            intentComment.putExtra("poemID",String.valueOf(sequence));
            startActivity(intentComment);
        }
    }
}
