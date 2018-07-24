package com.android.base_tools;


import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by 25011 on 2018/6/22.
 */
public enum MediaStoreManager {
    INSTANCE;

    public Cursor getVideoInfo(Context context) {
        String[] videoColumns = {
                MediaStore.Video.Media._ID, MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DATA, MediaStore.Video.Media.ARTIST,
                MediaStore.Video.Media.MIME_TYPE, MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DURATION, MediaStore.Video.Media.RESOLUTION,
        };
        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoColumns, null, null, null);
        if (cursor.moveToFirst()) {
            int i = 0;
            do {
                String _id = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                String TITLE = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                String DATA = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                String ARTIST = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.ARTIST));
                String MIME_TYPE = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE));
                String SIZE = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                String DURATION = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                String RESOLUTION = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.RESOLUTION));
                Log.i("i --> cursor", "_id:" + _id + " TITLE:" + TITLE + " DATA;" + DATA + " ARTIST:" + ARTIST + " MIME_TYPE:" + MIME_TYPE + " SIZE:" + SIZE + " DURATION:" + DURATION + " RESOLUTION:" + RESOLUTION);
                i++;
            } while (cursor.moveToNext());
        }

        //cursor: _id:29
        // TITLE:OPPO R9s 这一刻 更清晰
        // DATA;/storage/emulated/0/Movies/OPPO R9s 这一刻 更清晰.mp4
        // ARTIST:<unknown>
        // MIME_TYPE:video/mp4
        // SIZE:45916832
        // DURATION:60024
        // RESOLUTION:1920x1080
        return cursor;
    }

    MediaStoreManager() {
    }
}
