package com.example.cuikang.poemmaster;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cuikang.baselibrary.activity.BaseActivity;
import com.example.cuikang.baselibrary.http.bean.CommonRequest;
import com.example.cuikang.baselibrary.http.bean.CommonResponse;
import com.example.cuikang.baselibrary.http.interf.ResponseHandler;
import com.example.cuikang.poemmaster.url.ServerURL;
import java.util.ArrayList;
import java.util.HashMap;

import static android.widget.Toast.LENGTH_LONG;


public class ShowCommentActivity extends BaseActivity {

    ListView commentList;
    String poemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_comment);
        commentList=findViewById(R.id.list_comment);
        poemID=getIntent().getStringExtra("poemID");
        getListData(poemID);
    }

    private void getListData(final String poemID) {

        CommonRequest request = new CommonRequest();
        request.addRequestParam("poemID",poemID);

        sendHttpPostRequest(ServerURL.GET_COMMENT, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {

                if (response.getDataList().size() > 0) {
                    CommentAdapter adapter = new CommentAdapter(ShowCommentActivity.this, response.getDataList());
                    commentList.setAdapter(adapter);
                } else {
                    Toast.makeText(ShowCommentActivity.this,"列表数据为空",LENGTH_LONG).show();
                }
            }

            @Override
            public void fail(String failCode, String failMsg) {
                Toast.makeText(ShowCommentActivity.this,failCode + ":" + failMsg,LENGTH_LONG).show();
            }
        });
    }

    static class CommentAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<HashMap<String, String>> list;

        private CommentAdapter(Context context, ArrayList<HashMap<String, String>> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false);
                holder = new ViewHolder();
                holder.tvName =  convertView.findViewById(R.id.tv_name);
                holder.tvTime =  convertView.findViewById(R.id.tv_time);
                holder.tvComment = convertView.findViewById(R.id.tv_comment);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            HashMap<String, String> map = list.get(position);
            holder.tvName.setText(map.get("name"));
            holder.tvTime.setText(map.get("time"));
            holder.tvComment.setText(map.get("comment"));

            return convertView;
        }

        private static class ViewHolder {
            private TextView tvName;
            private TextView tvTime;
            private TextView tvComment;
        }
    }
}
