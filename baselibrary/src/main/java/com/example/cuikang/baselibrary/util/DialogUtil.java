package com.example.cuikang.baselibrary.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cuikang.baselibrary.R;
/**
 * Created by njau_ on 2018/6/10.
 */

public class DialogUtil {

    private static Dialog dialog; // 只使用一个Dialog实例
    private static View hintView;
    private static View decideView;

    /**
     * 提示框（无标题，一个确认键）
     *
     * @param context             上下文
     * @param msgStr              提示内容文字
     * @param exitCurrentActivity 点击确认按钮是否退出当前Activity
     */
    public static void showHintDialog(Context context,
                                      String msgStr,
                                      boolean exitCurrentActivity) {
        hintView = prepareHintView(context, false, null, msgStr, "确定", exitCurrentActivity);
        dialog = createDialog(context, hintView, false);
        dialog.show();
    }

    /**
     * 提示框（无标题，一个确认键）
     *
     * @param context             上下文
     * @param msgStrId            提示内容文字资源ID
     * @param exitCurrentActivity 点击确认按钮是否退出当前Activity
     */
    public static void showHintDialog(Context context,
                                      int msgStrId,
                                      boolean exitCurrentActivity) {
        showHintDialog(context, context.getResources().getString(msgStrId), exitCurrentActivity);
    }

    /**
     * 提示框（无标题，一个确认键）
     *
     * @param context  上下文
     * @param msgStr   提示内容文字
     * @param listener 确认按钮响应事件
     */
    public static void showHintDialog(Context context,
                                      String msgStr,
                                      View.OnClickListener listener) {
        showHintDialog(context, false, null, msgStr, "确定", listener);
    }

    /**
     * 提示框（无标题，一个确认键）
     *
     * @param context  上下文
     * @param msgStrId 提示内容文字资源ID
     * @param listener 确认按钮响应事件
     */
    public static void showHintDialog(Context context,
                                      int msgStrId,
                                      View.OnClickListener listener) {
        showHintDialog(context, context.getResources().getString(msgStrId), listener);
    }

    /**
     * 提示框（带标题，一个确认按钮）
     *
     * @param context             上下文
     * @param titleStr            标题文字
     * @param msgStr              内容显示文字
     * @param exitCurrentActivity 点击确认按钮是否退出当前Activity
     */
    public static void showHintDialogWithTitle(Context context,
                                               String titleStr,
                                               String msgStr,
                                               boolean exitCurrentActivity) {
        hintView = prepareHintView(context, true, titleStr, msgStr, "确定",
                exitCurrentActivity);
        dialog = createDialog(context, hintView, false);
        dialog.show();
    }

    /**
     * 提示框（带标题，一个确认按钮）
     *
     * @param context             上下文
     * @param titleStrId          标题文字资源ID
     * @param msgStrId            内容显示文字资源ID
     * @param exitCurrentActivity 点击确认按钮是否退出当前Activity
     */
    public static void showHintDialogWithTitle(Context context,
                                               int titleStrId,
                                               int msgStrId,
                                               boolean exitCurrentActivity) {
        showHintDialogWithTitle(context, context.getResources().getString(titleStrId),
                context.getResources().getString(msgStrId), exitCurrentActivity);
    }

    /**
     * 提示框（带标题，一个确认按钮）
     *
     * @param context  上下文
     * @param titleStr 标题文字
     * @param msgStr   内容显示文字
     * @param listener 点击确认按钮响应事件
     */
    public static void showHintDialogWithTitle(Context context,
                                               String titleStr,
                                               String msgStr,
                                               View.OnClickListener listener) {
        showHintDialog(context, true, titleStr, msgStr, "确定", listener);
    }

    /**
     * 提示框（带标题，一个确认按钮）
     *
     * @param context    上下文
     * @param titleStrId 标题文字资源ID
     * @param msgStrId   内容显示文字资源ID
     * @param listener   点击确认按钮响应事件
     */
    public static void showHintDialogWithTitle(Context context,
                                               int titleStrId,
                                               int msgStrId,
                                               View.OnClickListener listener) {
        showHintDialog(context, true, context.getResources().getString(titleStrId),
                context.getResources().getString(msgStrId), "确定", listener);
    }

    /**
     * 提示框（全属性：标题、内容、按钮文字、按钮响应）
     *
     * @param context   上下文
     * @param showTitle 是否显示标题
     * @param titleStr  标题文字
     * @param msgStr    内容文字
     * @param btnStr    按钮文字
     * @param listener  按钮响应监听
     */
    public static void showHintDialog(Context context,
                                      boolean showTitle,
                                      String titleStr,
                                      String msgStr,
                                      String btnStr,
                                      View.OnClickListener listener) {
        hintView = prepareHintView(context, showTitle, titleStr, msgStr, btnStr, listener);
        dialog = createDialog(context, hintView, false);
        dialog.show();
    }

    /**
     * 准备提示框View（无按钮响应监听）
     */
    private static View prepareHintView(final Context context,
                                        boolean showTitle,
                                        String title,
                                        String content,
                                        String btnString,
                                        final boolean exitCurrentActivity) {
        hintView = LayoutInflater.from(context).inflate(R.layout.dialog_hint, null);
        TextView tvTitle = (TextView) hintView.findViewById(R.id.tv_title);
        tvTitle.setText(title);

        TextView tvContent = (TextView) hintView.findViewById(R.id.tv_content);
        tvContent.setText(content);

        Button btnIKnow = (Button) hintView.findViewById(R.id.btn_iknow);
        btnIKnow.setText(btnString);
        btnIKnow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                if (exitCurrentActivity) {
                    dismissDialog();
                    ((Activity) context).finish();
                }
            }
        });

        hintView.findViewById(R.id.tv_title).setVisibility(
                showTitle ? View.VISIBLE : View.GONE);

        return hintView;
    }

    /**
     * 准备提示框View（带按钮响应监听）
     */
    private static View prepareHintView(final Context context,
                                        boolean showTitle,
                                        String title,
                                        String content,
                                        String btnString,
                                        View.OnClickListener listener) {
        hintView = LayoutInflater.from(context).inflate(R.layout.dialog_hint, null);

        TextView tvTitle = (TextView) hintView.findViewById(R.id.tv_title);
        tvTitle.setText(title);

        TextView tvContent = (TextView) hintView.findViewById(R.id.tv_content);
        tvContent.setText(content);

        Button btnIKnow = (Button) hintView.findViewById(R.id.btn_iknow);
        btnIKnow.setText(btnString);
        //设置监听器在按钮上
        if (listener != null) {
            btnIKnow.setOnClickListener(listener);
        }

        hintView.findViewById(R.id.tv_title).setVisibility(
                showTitle ? View.VISIBLE : View.GONE);

        return hintView;
    }

    /**
     * 显示自定义对话框
     *
     * @param context    上下文
     * @param view       自定义对话框显示的View
     * @param cancelable 点击Back或对话框以外时候取消对话框
     */
    public static void showCustomDialog(Context context, View view, boolean cancelable) {
        if (dialog == null) {
            dialog = new Dialog(context, R.style.MyDialogStyle);
        }
        dialog.setContentView(view);
        dialog.setCancelable(cancelable);
        dialog.show();
    }

    /**
     * 创建对话框
     */
    private static Dialog createDialog(Context context, View view,
                                       boolean cancelable) {
        if (dialog == null) {
            dialog = new Dialog(context, R.style.MyDialogStyle);
        }
        dialog.setContentView(view);
        dialog.setCancelable(cancelable);
        return dialog;
    }

    /**
     * 关闭对话框
     */
    public static void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
