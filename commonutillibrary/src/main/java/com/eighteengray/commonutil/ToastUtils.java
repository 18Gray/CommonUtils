package com.eighteengray.commonutil;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 *  Toast
 * */
public class ToastUtils {
   private static Toast instanceShort = null;
   private static Toast instanceLong = null;

   public ToastUtils() {
   }

   public static void ToastShort(Context mContext, String content) {
      if (TextUtils.isEmpty(content)) {
         return;
      }

      if (instanceShort == null) {
         instanceShort = Toast.makeText(mContext, content.trim(), Toast.LENGTH_SHORT);
      } else {
         instanceShort.setText(content);
      }

      instanceShort.show();
   }

   public static void ToastLong(Context mContext, String content) {
      if (TextUtils.isEmpty(content)) {
         return;
      }

      if (instanceLong == null) {
         instanceLong = Toast.makeText(mContext, content.trim(), Toast.LENGTH_LONG);
      } else {
         instanceLong.setText(content);
      }

      instanceLong.show();
   }

   public static void ToastShort(Context mContext, int resId) {
      ToastShort(mContext, mContext.getResources().getString(resId));
   }

   public static void ToastLong(Context mContext, int resId) {
      ToastLong(mContext, mContext.getResources().getString(resId));
   }

   public static void cancelToast() {
      if (instanceLong != null) {
         instanceLong.cancel();
      }
      if (instanceShort != null) {
         instanceShort.cancel();
      }
   }

}

