package com.barnettwong.lovedoudou.util;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.app.MyApplication;

import java.io.File;
import java.io.IOException;

public class PhotoDetailUtil {
    public static boolean toSetWallPage(Uri data, Activity mActivity) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(mActivity);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            File wallpageFile = new File(data.getPath());
            Uri contentUri = getImageContentUri(mActivity,wallpageFile.getAbsolutePath());
            mActivity.startActivity(wallpaperManager.getCropAndSetWallpaperIntent(contentUri));
        }else{
            try {
                wallpaperManager.setStream(mActivity.getContentResolver().openInputStream(data));
//                mView.showMsg(MyApplication.getInstance().getString(R.string.set_wallpaper_success));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
//                mView.showMsg(e.getMessage());
                return false;
            }
        }
        return true;
    }

    // http://stackoverflow.com/questions/23207604/get-a-content-uri-from-a-file-uri
    private static Uri getImageContentUri(Context context, String absolutePath) {
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=?",
                new String[]{absolutePath},
                null);
        if(cursor != null && cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,Integer.toString(id));
        } else if (!absolutePath.isEmpty()){
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA,absolutePath);
            return context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        }else {
            return null;
        }
    }

    public static void share(Uri data,Activity mActivity) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM,data);
        intent.setType("image/jpeg");
        mActivity.startActivity(Intent.createChooser(intent, MyApplication.getAppResources().getString(R.string.share)));
    }
}
