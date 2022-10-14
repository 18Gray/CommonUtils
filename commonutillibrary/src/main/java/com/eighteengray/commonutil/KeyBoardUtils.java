package com.eighteengray.commonutil;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


/**
 * 键盘相关工具类
 */
public class KeyBoardUtils {
    /**
     * 打开/关闭软键盘
     * @param mEditText 输入框
     * @param mContext  上下文
     *  其他切换方法：activity的adjustPan/adjustResize
     *  其他切换方法：<request/>   requestFocus  clearFocus
     */
    public static void toggleKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
    }


    /**
     * 打开软键盘
     * @param mEditText  输入框
     * @param mContext  上下文
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.SHOW_FORCED);
    }


    /**
     * 关闭软键盘
     * @param mEditText  输入框
     * @param mContext  上下文
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

}
