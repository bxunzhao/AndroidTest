package com.liang.databinding.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

/**
 * Created by ichongliang on 2017/5/7.
 */

public class RemoteDBUtil {

    public static boolean insert(SQLiteOpenHelper openHelper,
                               RemoteAppInfo appInfo) {
        if (null == appInfo) {
            return true;

        }
        SQLiteDatabase db = null;
        try {
            db = openHelper.getWritableDatabase();
            ContentValues values = appInfo.getContentValues();
            return -1 != db.insert(RemoteDBHelper.TABLE_APP_REMOTE, null,
                    values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != db) {
                db.close();
            }
        }
        return false;
    }

    public static boolean insert(SQLiteOpenHelper openHelper,
                               List<RemoteAppInfo> list) {
        boolean result = true;
        if (null == list || list.size() <= 0) {
            return true;

        }

        SQLiteDatabase db = null;

        try {
            db = openHelper.getWritableDatabase();
            db.beginTransaction();
            for (RemoteAppInfo remoteAppInfo : list) {
                ContentValues values = remoteAppInfo.getContentValues();
                if (db.insert(RemoteDBHelper.TABLE_APP_REMOTE, null, values) < 0) {
                    result = false;
                    break;
                }
            }
            if (result) {
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (null != db) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    public static boolean insertBySql(SQLiteOpenHelper openHelper,
                                    List<RemoteAppInfo> list) {
        if (null == openHelper || null == list || list.size() <= 0) {
            return false;
        }
        SQLiteDatabase db = null;
        try {
            db = openHelper.getWritableDatabase();
            String sql = "insert into " + RemoteDBHelper.TABLE_APP_REMOTE + "("
                    + RemoteDBHelper.COL_PKG_NAME + ","// 包名
                    + RemoteDBHelper.COL_USER_ACCOUNT + ","// 账号
                    + RemoteDBHelper.COL_APP_SOURCE + ","// 来源
                    + RemoteDBHelper.COL_SOURCE_UNIQUE + ","// PC mac 地址
                    + RemoteDBHelper.COL_MOBILE_UNIQUE + ","// 手机唯一标识
                    + RemoteDBHelper.COL_IMEI + ","// 手机IMEI
                    + RemoteDBHelper.COL_INSTALL_STATUS + ","// 安装状态
                    + RemoteDBHelper.COL_TRANSFER_RESULT + ","// 传输状态
                    + RemoteDBHelper.COL_REMOTE_RECORD_ID // 唯一标识
                    + ") " + "values(?,?,?,?,?,?,?,?,?)";
            SQLiteStatement stat = db.compileStatement(sql);
            db.beginTransaction();
            for (RemoteAppInfo remoteAppInfo : list) {
                stat.bindString(1, remoteAppInfo.getPkgName());
                stat.bindString(2, remoteAppInfo.getAccount());
                stat.bindLong(3, remoteAppInfo.getFrom());
                stat.bindString(4, remoteAppInfo.getFromDeviceMd5());
                stat.bindString(5, remoteAppInfo.getMoblieMd5());
                stat.bindString(6, remoteAppInfo.getImei());
                stat.bindLong(7, remoteAppInfo.getInstallStatus());
                stat.bindLong(8, remoteAppInfo.getTransferResult());
                stat.bindString(9, remoteAppInfo.getRecordId());
                long result = stat.executeInsert();
                if (result < 0) {
                    return false;
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (null != db) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
