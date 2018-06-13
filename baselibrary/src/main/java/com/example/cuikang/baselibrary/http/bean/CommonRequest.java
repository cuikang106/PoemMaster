package com.example.cuikang.baselibrary.http.bean;

/**
 * Created by njau_ on 2018/6/10.
 */
//功能：接受两个string类型参数，组装成jason并输出为字符串
import com.example.cuikang.baselibrary.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
/** 基本请求体封装类
 * Created by WangJie on 2017-05-03.
 */

public class CommonRequest {
    /**
     * 请求码，类似于接口号（在本文中用Servlet做服务器时暂时用不到）
     */
    //private String requestCode;
    /**
     * 请求参数
     * （说明：这里只用一个简单map类封装请求参数，对于请求报文需要上送一个数组的复杂情况需要自己再加一个ArrayList类型的成员变量来实现）
     */
    private HashMap<String, Object> Param;

    public CommonRequest() {
        //requestCode = "";
        Param = new HashMap<>();
    }

    /**
     * 设置请求代码，即接口号，在本例中暂时未用到
     */
    /*
    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }
    */
    /**
     * 为请求报文设置参数
     * @param paramKey 参数名
     * @param paramValue 参数值
     */
    public void addRequestParam(String paramKey, String paramValue) {
        Param.put(paramKey, paramValue);
    }

    /**
     * 将请求报文体组装成json形式的字符串，以便进行网络发送
     * @return 请求报文的json字符串
     */
    public String getJsonStr() {

        JSONObject object = new JSONObject(Param);

        // 打印原始请求报文
        LogUtil.logRequest(object.toString());
        return object.toString();
    }
}
