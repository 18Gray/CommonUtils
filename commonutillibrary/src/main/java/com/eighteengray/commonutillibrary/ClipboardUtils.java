package com.eighteengray.commonutillibrary;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * 剪贴板工具类
 * */
public class ClipboardUtils {

    public static void copyTextToClipboard(Context context, String text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(context.getPackageName(), text));
    }

    public static void clearClipboardText(Context context) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(null, ""));
    }

    public static CharSequence getClipboardText(Context context) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = cm.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            CharSequence text = clip.getItemAt(0).coerceToText(context);
            if (text != null) {
                return text;
            }
        }
        return "";
    }

    public static void addClipboardListener(Context context, final ClipboardManager.OnPrimaryClipChangedListener listener) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.addPrimaryClipChangedListener(listener);
    }

    public static void removeClipboardListener(Context context, final ClipboardManager.OnPrimaryClipChangedListener listener) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.removePrimaryClipChangedListener(listener);
    }

}
