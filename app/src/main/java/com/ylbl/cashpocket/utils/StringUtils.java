package com.ylbl.cashpocket.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class StringUtils {
    /**
     * 图片转base64
     * @param bitmap
     * @return
     */
    public static String BitmaptoBase64(Bitmap bitmap){
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
    public static List<String> BitmaptoBase64(List<Bitmap> images){
        ByteArrayOutputStream baos = null;
        List<String> result = new ArrayList<>();
        try {
            if (images != null) {
                for (int i = 0 ; i < images.size() ;i++) {
                    baos = new ByteArrayOutputStream();
                    images.get(i).compress(Bitmap.CompressFormat.JPEG, 100, baos);

                    baos.flush();
                    baos.close();

                    byte[] bitmapBytes = baos.toByteArray();
                    result.add(Base64.encodeToString(bitmapBytes, Base64.DEFAULT)) ;
                }
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
     * base64 转图片
     * @param string
     * @return
     */
    public static Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
           byte[] bytes = Base64.decode(string, Base64.DEFAULT);
           bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    /**
     * double转String,保留小数点后两位
     * @param num
     * @return
     */
    public static String doubleToString(double num){
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }
    /**
     * double转String,保留小数点后两位
     * @param string
     * @return
     */
    public static String stringToString(String string){
        //使用0.00不足位补0，#.##仅保留有效位
        DecimalFormat format = new DecimalFormat("0.00");
        String s = format.format(new BigDecimal(string));
        return s;
    }
}
