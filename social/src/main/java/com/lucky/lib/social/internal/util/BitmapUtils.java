package com.lucky.lib.social.internal.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class BitmapUtils {

    public static Bitmap resToBitmap(Context context, int id) {
        return BitmapFactory.decodeResource(context.getResources(), id);
    }

    public static File saveBitmapFile(Bitmap bitmap, String path) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new File(path);
    }

    public static byte[] bitmap2Bytes(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        if (bitmap != null && !bitmap.isRecycled()) {
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                if (byteArrayOutputStream.toByteArray() == null) {
                }
                return byteArrayOutputStream.toByteArray();
            } catch (Exception e) {
            } finally {
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException var14) {
                        ;
                    }
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static byte[] compressBitmap(byte[] data, int byteCount) {
        boolean isFinish = false;
        if (data != null && data.length > byteCount) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Bitmap tmpBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            int times = 1;
            double percentage = 1.0D;

            while (!isFinish && times <= 10) {
                percentage = Math.pow(0.8D, (double) times);
                int compress_datas = (int) (100.0D * percentage);
                tmpBitmap.compress(Bitmap.CompressFormat.JPEG, compress_datas, outputStream);
                if (outputStream != null && outputStream.size() < byteCount) {
                    isFinish = true;
                } else {
                    outputStream.reset();
                    ++times;
                }
            }

            if (outputStream != null) {
                byte[] outputStreamByte = outputStream.toByteArray();
                if (!tmpBitmap.isRecycled()) {
                    tmpBitmap.recycle();
                }

                if (outputStreamByte.length > byteCount) {
                }

                return outputStreamByte;
            }
        }

        return data;
    }
}
