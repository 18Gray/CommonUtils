package com.eighteengray.commonutillibrary;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;


/**
 * 通知相关工具类
 */
public class NotificationUtil
{
	private Context mContext;
	private NotificationManager nm;
	private Notification notification;
	NotificationCompat.Builder ncBuilder;

	private static int NOTIFICATION_ID;
	int requestCode = (int) SystemClock.uptimeMillis();

	
	public NotificationUtil(Context context, int ID)
	{
		this.NOTIFICATION_ID = ID;
		mContext = context;
		// 获取系统服务来初始化对象
		nm = (NotificationManager) mContext.getSystemService(Activity.NOTIFICATION_SERVICE);
		ncBuilder = new NotificationCompat.Builder(mContext);
		
		ncBuilder.setWhen(System.currentTimeMillis());
		ncBuilder.setAutoCancel(true);
		ncBuilder.setOngoing(false);
		ncBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
		/*
		 * Notification.DEFAULT_ALL：铃声、闪光、震动均系统默认。
		 * Notification.DEFAULT_SOUND：系统默认铃声。
		 * Notification.DEFAULT_VIBRATE：系统默认震动。
		 * Notification.DEFAULT_LIGHTS：系统默认闪光。
		 * notifyBuilder.setDefaults(Notification.DEFAULT_ALL);
		 */
		ncBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS);
	}
	
	
	public NotificationUtil setSmallIcon(int smallIcon)
	{
		ncBuilder.setSmallIcon(smallIcon);
		return this;
	}
	
	
	public NotificationUtil setLargeIcon(Bitmap largeIcon)
	{
		ncBuilder.setLargeIcon(largeIcon);
		return this;
	}
	
	public NotificationUtil setTickerText(String ticker)
	{
		ncBuilder.setTicker(ticker);
		return this;
	}
	
	public NotificationUtil setContentTitle(String title)
	{
		ncBuilder.setContentTitle(title);
		return this;
	}
	
	public NotificationUtil setContentText(String contentText)
	{
		ncBuilder.setContentText(contentText);
		return this;
	}
	
	public NotificationUtil setClickIntent(Intent clickIntent)
	{
		clickIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent clickPendingIntent = PendingIntent.getActivity(mContext, requestCode, clickIntent, PendingIntent.FLAG_ONE_SHOT);
		ncBuilder.setContentIntent(clickPendingIntent);
		return this;
	}
	
	
	public NotificationUtil setDeleteIntent(Intent deleteIntent)
	{
		deleteIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent deletePendingIntent = PendingIntent.getActivity(mContext, requestCode, deleteIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		ncBuilder.setDeleteIntent(deletePendingIntent);
		return this;
	}
	

	public NotificationUtil setNumber(int number)
	{
		ncBuilder.setNumber(number);
		return this;
	}
	
	public NotificationUtil setLight(int argb, int onMs, int offMs)
	{
		ncBuilder.setLights(argb, onMs, offMs);
		return this;
	}

	public NotificationUtil setVibrate(long[] vibrates)
	{
		ncBuilder.setVibrate(vibrates);
		return this;
	}
	
	public NotificationUtil setRemoteViews(RemoteViews remoteViews)
	{
		ncBuilder.setContent(remoteViews);
		return this;
	}
	
	public NotificationUtil setProgress(int max, int progress, boolean indeterminate)
	{
		ncBuilder.setProgress(max, progress, indeterminate);
		return this;
	}


	/**
	 * 发送通知
	 */
	public void sentNotification()
	{
		notification = ncBuilder.build();
		// 发送该通知
		nm.notify(NOTIFICATION_ID, notification);
	}


	/**
	 * 清除通知
	 */
	public void clear()
	{
		// 取消通知
		nm.cancelAll();
	}
	
}
