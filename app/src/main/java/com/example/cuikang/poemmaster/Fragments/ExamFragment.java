package com.example.cuikang.poemmaster.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuikang.poemmaster.MainActivity;
import com.example.cuikang.poemmaster.R;
import com.example.cuikang.poemmaster.RecordActivity;
import com.example.cuikang.poemmaster.bean.PoemBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ExamFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    int sequence;
    boolean startAnswer=false;
    int goodChoice;
    ArrayList<String> extraData = new ArrayList<>();
    ArrayList<Integer> wrongQuestionSet = new ArrayList<>();
    String key;

    public ExamFragment() {
        // Required empty public constructor
    }

    //创建并返回一个实例
    public static ExamFragment newInstance(String param1) {
        ExamFragment fragment = new ExamFragment();
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
        return inflater.inflate(R.layout.fragment_exam, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        reset();

        new AlertDialog.Builder(getContext()).setTitle("答题说明").setMessage("60s的答题时间，回答10道唐诗三百首选择题，赶快点击“开始答题”按钮开始吧！")
                .setPositiveButton("我准备好了", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // 开始答题按钮
                        Button startBtn = (Button) getActivity().findViewById(R.id.start_btn);
                        startBtn.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                if (!startAnswer) {
                                    reset();
                                    initialize();
                                    CountDownTimer timer = new CountDownTimer(61000, 1000) {

                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            TextView leftTime = (TextView) getActivity().findViewById(R.id.left_time);
                                            if(startAnswer) {
                                                leftTime.setText("" + (millisUntilFinished / 1000));
                                            }
                                            else{
                                                leftTime.setText(""+60);
                                                cancel();
                                            }
                                        }

                                        @Override
                                        public void onFinish() {
                                            if(startAnswer) {
                                                Toast.makeText(getContext(), "时间结束！", Toast.LENGTH_LONG).show();
                                                startAnswer=false;
                                            }

                                        }
                                    };
                                    timer.start();
                                    startAnswer=true;
                                }
                            }
                        });

                        // 结束答题按钮
                        Button endBtn = (Button) getActivity().findViewById(R.id.end_btn);
                        endBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (startAnswer) {
                                    // TODO 时间错题等置零
                                    startAnswer=false;
                                    reset();
                                }
                            }
                        });

                        // 提交答案按钮
                        Button submitBtn = (Button) getActivity().findViewById(R.id.submit_btn);
                        submitBtn.setOnClickListener(new View.OnClickListener() {
                            MainActivity activity = (MainActivity) getActivity();
                            RadioGroup radioGroup = (RadioGroup) activity.findViewById(R.id.radio_group);
                            TextView rightAnswerNumber = (TextView) activity.findViewById(R.id.right_answer_num);
                            TextView wrongAnswerNumber = (TextView) activity.findViewById(R.id.wrong_answer_num);
                            TextView leftNumber = (TextView) activity.findViewById(R.id.left_num);
                            TextView poemKey = (TextView) activity.findViewById(R.id.poem_key);

                            @Override
                            public void onClick(View v) {

                                if (startAnswer) {
//                                    Log.e(activity.TAG, goodChoice + "");
                                    poemKey.setText("");
                                    boolean right = false;  // 是否正确
                                    boolean selected = false;  // 是否选择
                                    // 先判断有没有选择选项
                                    for (int i = 0; i < radioGroup.getChildCount(); i++) {
                                        RadioButton rb = (RadioButton) radioGroup.getChildAt(i);
                                        if (rb.isChecked()) {
                                            selected = true;
                                            break;
                                        }
                                    }

                                    // 如果没有选择选项,则报错
                                    if (selected) {
                                        for (int i = 0; i < radioGroup.getChildCount(); i++) {
                                            RadioButton rb = (RadioButton) radioGroup.getChildAt(i);
                //                                Log.e(activity.TAG, String.valueOf(rb.isChecked()));
                                            if (rb.isChecked() && goodChoice == i) {
                                                rightAnswerNumber.setText((Integer.parseInt(rightAnswerNumber.getText().toString()) + 1) + "");
                                                right = true;
                                                break;
                                            }
                                        }
                                        if (!right) {
                                            wrongAnswerNumber.setText((Integer.parseInt(wrongAnswerNumber.getText().toString()) + 1) + "");
                                            wrongQuestionSet.add(11-Integer.parseInt(leftNumber.getText().toString()));
                                            Log.e(activity.TAG,"错误的题目是："+(11-Integer.parseInt(leftNumber.getText().toString())));
                                        }
                                        leftNumber.setText((Integer.parseInt(leftNumber.getText().toString()) - 1) + "");
                                        radioGroup.clearCheck();
                                        if (Integer.parseInt(leftNumber.getText().toString()) != 0)
                                            initialize();
                                        else{
                                            anotherReset();
                                            startAnswer = false;
                                            Toast.makeText(getContext(), "答题完成！", Toast.LENGTH_LONG).show();
                                        }
                                    } else Toast.makeText(getContext(), "请选择选项！", Toast.LENGTH_LONG).show();

                                }
                            }
                        });


                        final Button keyBtn = (Button) getActivity().findViewById(R.id.key_btn);
                        keyBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView keyPoem = (TextView) getActivity().findViewById(R.id.poem_key);
                                if (startAnswer)
                                    keyPoem.setText(key);
                            }
                        });

                        Button historyBtn = (Button) getActivity().findViewById(R.id.history_btn);
                        historyBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!startAnswer)
                                {
                                    Intent intent = new Intent(getActivity().getApplicationContext(), RecordActivity.class);
                                    intent.putExtra("extra_data",extraData);
                                    intent.putExtra("wrong_sequence",wrongQuestionSet);
                                    startActivity(intent);
                                }

                            }
                        });
                    }
                }).show();
    }

    // 选出符合要求的诗句数目(2句或者4句)requiredParagraphSize的诗，和符合要求的七言或者五言诗requiredPoemSize
    public PoemBean selectPoem(List<PoemBean> tempOptionPoemList, int requiredParagraphSize , int requiredPoemSize, int requiredPoemRealSize){
        PoemBean poem;
        int paragraphSize;
        int poemSize;
        int poemRealSize;
        do {
            Random r = new Random();
            sequence = r.nextInt(tempOptionPoemList.size());
            paragraphSize=tempOptionPoemList.get(sequence).getParagraphs().size();
            poemSize=tempOptionPoemList.get(sequence).getParagraphs().get(0).length();
            poemRealSize=tempOptionPoemList.get(sequence).getParagraphs().get(0).replaceAll( "[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]" , "").length();
//            Log.e(activity.TAG,paragraphSize+"");
        }while (paragraphSize!=requiredParagraphSize||poemSize!=requiredPoemSize||poemRealSize!=requiredPoemRealSize);
        poem = tempOptionPoemList.get(sequence);
        tempOptionPoemList.remove(sequence);
        return poem;
    }

    // a为诗中的某句话，b为一句话中的前句或者后句
    public String selectSentence(PoemBean poem, int a, int b){
        String halfSentence;
        String tempString=poem.getParagraphs().get(a);
        List<String> subject = Arrays.asList(tempString.substring(0,tempString.length() - 1).replaceAll("？","，").split("，"));
        halfSentence=subject.get(b);
        return halfSentence;
    }

    public void reset(){
        TextView questionTitle = (TextView) getActivity().findViewById(R.id.question_title);
        TextView questionAuthor = (TextView) getActivity().findViewById(R.id.question_author);
        TextView questionContent = (TextView) getActivity().findViewById(R.id.question_content);
        TextView poemKey = (TextView) getActivity().findViewById(R.id.poem_key);
        questionTitle.setText("题名");
        questionAuthor.setText("作者");
        questionContent.setText("");
        poemKey.setText("");

        TextView rightAnswerNumber = (TextView) getActivity().findViewById(R.id.right_answer_num);
        TextView wrongAnswerNumber = (TextView) getActivity().findViewById(R.id.wrong_answer_num);
        TextView leftNumber = (TextView) getActivity().findViewById(R.id.left_num);
        rightAnswerNumber.setText("" + 0);
        wrongAnswerNumber.setText("" + 0);
        leftNumber.setText(""+10);

        RadioButton selection1 = (RadioButton) getActivity().findViewById(R.id.radiobtn_selection_1);
        RadioButton selection2 = (RadioButton) getActivity().findViewById(R.id.radiobtn_selection_2);
        RadioButton selection3 = (RadioButton) getActivity().findViewById(R.id.radiobtn_selection_3);
        RadioButton selection4 = (RadioButton) getActivity().findViewById(R.id.radiobtn_selection_4);
        RadioGroup radioGroup = (RadioGroup) getActivity().findViewById(R.id.radio_group);
        selection1.setText("选项1");
        selection2.setText("选项2");
        selection3.setText("选项3");
        selection4.setText("选项4");
        radioGroup.clearCheck();
    }

    public void anotherReset() {
        TextView questionTitle = (TextView) getActivity().findViewById(R.id.question_title);
        TextView questionAuthor = (TextView) getActivity().findViewById(R.id.question_author);
        TextView questionContent = (TextView) getActivity().findViewById(R.id.question_content);
        TextView poemKey = (TextView) getActivity().findViewById(R.id.poem_key);
        questionTitle.setText("题名");
        questionAuthor.setText("作者");
        questionContent.setText("");
        poemKey.setText("");

        RadioButton selection1 = (RadioButton) getActivity().findViewById(R.id.radiobtn_selection_1);
        RadioButton selection2 = (RadioButton) getActivity().findViewById(R.id.radiobtn_selection_2);
        RadioButton selection3 = (RadioButton) getActivity().findViewById(R.id.radiobtn_selection_3);
        RadioButton selection4 = (RadioButton) getActivity().findViewById(R.id.radiobtn_selection_4);
        RadioGroup radioGroup = (RadioGroup) getActivity().findViewById(R.id.radio_group);
        selection1.setText("选项1");
        selection2.setText("选项2");
        selection3.setText("选项3");
        selection4.setText("选项4");
        radioGroup.clearCheck();
    }

    public void initialize(){
        MainActivity activity = (MainActivity) getActivity();
        List<PoemBean> tempPoemList = new ArrayList<>();
        tempPoemList = activity.poemList;
        int paragraphSize;
        int poemSize;
        int poemRealSize;
        do {
            Random r = new Random();
            sequence = r.nextInt(tempPoemList.size());
            Log.e(((MainActivity) getActivity()).TAG, sequence+"");
            paragraphSize=tempPoemList.get(sequence).getParagraphs().size();
            poemSize=tempPoemList.get(sequence).getParagraphs().get(0).length();
            poemRealSize=tempPoemList.get(sequence).getParagraphs().get(0).replaceAll( "[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]" , "").length();
//            Log.e(activity.TAG,poemSize+"");
        }while ((paragraphSize!=2 && paragraphSize!=4)||(poemSize!=12 && poemSize!=16)||(poemRealSize!=10 && poemRealSize!=14));
        PoemBean poem = tempPoemList.get(sequence);
        tempPoemList.remove(sequence);

        TextView questionTitle = (TextView) activity.findViewById(R.id.question_title);
        questionTitle.setText(poem.getTitle());

        TextView questionAuthor = (TextView) activity.findViewById(R.id.question_author);
        questionAuthor.setText(poem.getAuthor());

        TextView questionContent = (TextView) activity.findViewById(R.id.question_content);

        // 选取诗中的一句话
        Random r = new Random();
        int poemSequence = r.nextInt(paragraphSize);
        String tempString=poem.getParagraphs().get(poemSequence);
        List<String> subject =Arrays.asList(tempString.substring(0,tempString.length() - 1).replaceAll("？","，").split("，"));
        Log.e(activity.TAG,subject+"");

        // 选取一句诗中的半句话作为题干
        Random r1 = new Random();
        int sentenceSequence=r1.nextInt(2);
//        Log.e(activity.TAG,"取出的是第"+sentenceSequence+"半句");
        if (sentenceSequence==0)
            questionContent.setText(subject.get(sentenceSequence)+"，_______________。");
        else questionContent.setText("_______________，"+subject.get(sentenceSequence)+"。");

        RadioButton selection1 = (RadioButton) activity.findViewById(R.id.radiobtn_selection_1);
        RadioButton selection2 = (RadioButton) activity.findViewById(R.id.radiobtn_selection_2);
        RadioButton selection3 = (RadioButton) activity.findViewById(R.id.radiobtn_selection_3);
        RadioButton selection4 = (RadioButton) activity.findViewById(R.id.radiobtn_selection_4);

        Random r3 = new Random();  // 决定正确答案放在哪个选项
        goodChoice = r3.nextInt(4);
        switch (goodChoice) {

            case 0:
                selection1.setText(subject.get(1 - sentenceSequence));
            {
                List<PoemBean> tempOptionPoemList = tempPoemList;
                selection2.setText(selectSentence(selectPoem(tempOptionPoemList, paragraphSize, poemSize, poemRealSize), poemSequence, 1 - sentenceSequence));
                selection3.setText(selectSentence(selectPoem(tempOptionPoemList, paragraphSize, poemSize, poemRealSize), poemSequence, 1 - sentenceSequence));
                selection4.setText(selectSentence(selectPoem(tempOptionPoemList, paragraphSize, poemSize, poemRealSize), poemSequence, 1 - sentenceSequence));
            }
            break;

            case 1:
                selection2.setText(subject.get(1 - sentenceSequence));
            {
                List<PoemBean> tempOptionPoemList = tempPoemList;
                selection1.setText(selectSentence(selectPoem(tempOptionPoemList, paragraphSize, poemSize, poemRealSize), poemSequence, 1 - sentenceSequence));
                selection3.setText(selectSentence(selectPoem(tempOptionPoemList, paragraphSize, poemSize, poemRealSize), poemSequence, 1 - sentenceSequence));
                selection4.setText(selectSentence(selectPoem(tempOptionPoemList, paragraphSize, poemSize, poemRealSize), poemSequence, 1 - sentenceSequence));
            }
            break;

            case 2:
                selection3.setText(subject.get(1 - sentenceSequence));
            {
                List<PoemBean> tempOptionPoemList = tempPoemList;
                selection2.setText(selectSentence(selectPoem(tempOptionPoemList, paragraphSize, poemSize, poemRealSize), poemSequence, 1 - sentenceSequence));
                selection1.setText(selectSentence(selectPoem(tempOptionPoemList, paragraphSize, poemSize, poemRealSize), poemSequence, 1 - sentenceSequence));
                selection4.setText(selectSentence(selectPoem(tempOptionPoemList, paragraphSize, poemSize, poemRealSize), poemSequence, 1 - sentenceSequence));

            }
            break;

            case 3:
                selection4.setText(subject.get(1 - sentenceSequence));
            {
                List<PoemBean> tempOptionPoemList = tempPoemList;
                selection2.setText(selectSentence(selectPoem(tempOptionPoemList, paragraphSize, poemSize, poemRealSize), poemSequence, 1 - sentenceSequence));
                selection3.setText(selectSentence(selectPoem(tempOptionPoemList, paragraphSize, poemSize, poemRealSize), poemSequence, 1 - sentenceSequence));
                selection1.setText(selectSentence(selectPoem(tempOptionPoemList, paragraphSize, poemSize, poemRealSize), poemSequence, 1 - sentenceSequence));
            }
            break;
        }
        key = subject.get(1 - sentenceSequence);
        String tempextraData=subject.get(0)+","+subject.get(1)+","+(sentenceSequence+"");
        extraData.add(tempextraData);
    }
}
