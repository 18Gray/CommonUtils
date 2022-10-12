package com.eighteengray.commonutillibrary;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 图像相关工具类
 */
public class ImageUtils {

    /**
     * 调用系统拍照
     * @param activity
     */
    public static void takeCamera(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(intent, "选择图片"), 1);
    }


    /**
     * 调用系统相册
     * @param activity
     */
    public static void takePhoto(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, 2);
    }

    /**
     * 调用系统裁剪
     * @param aty
     * @param uri
     * @param imagePath
     */
    public static void takeCut(Activity aty, Uri uri, String imagePath) {
        File f = new File(imagePath);
        if (!f.exists())
        {
            File parentFile = f.getParentFile();
            if (!parentFile.exists())
            {
                parentFile.mkdirs();
            }
            try
            {
                f.createNewFile();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("output", Uri.fromFile(new File(imagePath)));// 保存到原文件
        intent.putExtra("outputFormat", "JPEG");// 返回格式
        aty.startActivityForResult(intent, 3);
    }


    /**
     * 在activityOnResult中拿到图片
     * @param data  在onActivityResult中拿到的Intent
     * @param context
     * @return
     */
    public static Bitmap getDataFromResult(Intent data, Context context) {
        Bitmap bitmap = null;
        Uri uri = data.getData();
        ContentResolver cr = context.getContentResolver();
        try {
            bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    //图像数据大小相关

    /**
     * 图像数据大小压缩到指定大小
     * @param image  原始图像
     * @param fileCount 压缩到的图像数据大小
     * @return
     */
    public static Bitmap compressBitmap(Bitmap image, int fileCount) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩。很傻很天真，这个toByteArray根本不能返回真的图像大小。
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }


    /**
     * 保存图像到指定路径
     * @param bitmap  原始图像
     * @param path  保存路径
     * @param name  保存名称
     */
    public static void saveBitmap(Bitmap bitmap, String path, String name) {
        File f = FileUtils.createFile(path, name);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        try {
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存图像到手机图片库中
     */
    public static String saveBitmap2Album(Context context, Bitmap bitmap){
        //将生成的Bitmap插入到手机的图片库当中，获取到图片路径
        String filePath = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, null, null);
        //及时回收Bitmap对象，防止OOM
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        //转uri之前必须判空，防止保存图片失败
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        return getRealPathFromURI(context, Uri.parse(filePath));
    }

    private static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            if (cursor == null) {
                return "";
            }
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    /**
     * 将多幅图像合并，存储到一个文件中。
     * 一般用于同时发送多张图片，网络接口中加上另一个字段，用于存储每副图像的大小，这样服务端就可以按照每副图像大小逐个取出图像，存在服务端，同时保证了图像的顺序性。
     * @param path
     * @param bitmapArrayList
     * @throws Exception
     */
    public static void unionMultiImages(String path, String name, ArrayList<Bitmap> bitmapArrayList) throws Exception {
        File file = FileUtils.createFile(path, name);
        FileOutputStream fos = new FileOutputStream(file);
        for(int i = 0; i < bitmapArrayList.size(); i++) {
            Bitmap currentBitmap = bitmapArrayList.get(i);
            currentBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        }
        fos.flush();
        fos.close();
    }



    //图像尺寸大小相关
    public static Bitmap getBitmapFromPathSimple(String path){
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return bitmap;
    }

    /**
     * 从指定路径获取图片，按照指定长宽尺寸获取图片
     * 原理：用流去获取图像，计算得到所需Options，然后用options加载图片。
     * @param imagePath
     * @param imageSize
     * @param requestWidth
     * @param requestHeight
     * @return
     */
    public static Bitmap getBitmapFromPath(String imagePath, int imageSize, int requestWidth, int requestHeight) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        File file = new File(imagePath);
        long size = file.length();
        long multiple = size / (imageSize * 1024);
        double multiSqrt = Math.sqrt(multiple);
        int multiAbs = (int) Math.abs(multiSqrt);
        options.inSampleSize = multiAbs;
        try {
            bitmap = BitmapFactory.decodeFile(imagePath, options);
        }
        catch (OutOfMemoryError ooe) {
            return null;
        }
        catch (Exception e) {
            return null;
        }

        //如果图片的长、宽大于指定长宽，则强制大小，并做等比缩放；否则用原图
        int bitmapActualWidth = bitmap.getWidth();
        int bitmapActualHeight = bitmap.getHeight();
        int bitmapResultWidth = 0;
        int bitmapResultHeight = 0;
        //如果实际宽高有一个比要求宽高要大，则需要做等比缩放
        if(bitmapActualWidth > requestWidth || bitmapActualHeight > requestHeight) {
            //计算缩放比例，如果宽度缩放更大，则按照宽度等比缩放；如果高度缩放更大，则按照高度等比缩放
            float widthScale = bitmapActualWidth / requestWidth;
            float heightScale = bitmapActualHeight / requestHeight;
            if(widthScale > heightScale) {
                bitmapResultWidth = requestWidth;
                bitmapResultHeight = bitmapResultWidth * bitmapActualHeight / bitmapActualWidth;
            }
            else {
                bitmapResultHeight = requestHeight;
                bitmapResultWidth = bitmapResultHeight * bitmapActualWidth / bitmapActualHeight;
            }
            bitmap = zoomBitmap(bitmap, bitmapResultWidth, bitmapResultHeight);
        }
        return bitmap;
    }


    /**
     * 等比例缩放图片到指定尺寸的长宽
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, double width, double height) {
        Bitmap zoomBitmap = null;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidht, scaleHeight);
        try {
            zoomBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        } catch (Exception e) {
        }
        return zoomBitmap;
    }


    /**
     * 比例缩放图片到指定尺寸的长宽-方法2
     * @param imagePath
     * @param width
     * @param height
     * @return
     */
    public Bitmap zoomBitmap(String imagePath, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false;
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        }
        else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }


    /**
     * 从原图像上截取指定大小的图像
     * @param bitmap 原始图像
     * @param x 截取起点的x坐标
     * @param y 截取起点的y坐标
     * @param width 截取宽度
     * @param height 截取高度
     * @return
     */
    public static Bitmap cutBitmap(Bitmap bitmap, int x, int y, int width, int height) {
        return Bitmap.createBitmap(bitmap, x, y, width, height);
    }


    /**
     * 获取视频缩略图
     * @param videoPath
     * @param width
     * @param height
     * @param kind
     * @return
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap = null;
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }


    /**
     * View生成Bitmap
     */
    public static Bitmap loadBitmapFromView(Context context, View view, int width, int height){
        int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        view.measure(widthSpec, heightSpec);
        view.layout(0, 0, width, height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }



    /// 图片格式转换

    /**
     * Bitmap转byte[]
     * @param bm
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bas);
        return bas.toByteArray();
    }

    /**
     * byte[]转Bitmap
     * @param b
     * @return
     */
    public static Bitmap bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /**
     * Drawable转Bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        return bitmapDrawable.getBitmap();
    }

    /**
     * Bitmap转Drawable
     * @param bitmap
     * @return
     */
    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        Drawable drawable = new BitmapDrawable(bitmap);
        return drawable;
    }

    /**
     * Bitmap转16进制字符串
     * @param bitmap
     * @return
     */
    public static String bitmap2HexString(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = FormatTransformUtil.bytes2HexString(bitmapBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * Bitmap转Base64 String
     * @param bitmap
     * @return
     */
    public static String bitmap2Base64String(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * Base64 String转Bitmap
     * @param base64Data
     * @return
     */
    public static Bitmap base64String2Bitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * resouceId转Bitmap
     * @return
     */
    public static Bitmap resouceId2Bitmap(Context context, int resouceId) {
        Bitmap resultBitmap = null;
        Drawable drawable = context.getResources().getDrawable(resouceId);
        resultBitmap = drawable2Bitmap(drawable);
        return resultBitmap;
    }

}