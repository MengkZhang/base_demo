package com.zhang.androidbase.sqlitedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class SqliteDbHelper extends SQLiteOpenHelper {
    /**
     * 构造方法
     * @param context ：上下文
     * @param name    ：数据库名字
     * @param factory ：游标cursor工厂
     * @param version ：数据库版本号 默认从 1 开始
     */
    public SqliteDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 该方法做表结构的初始化
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table info(_id integer primary key autoincrement,name varchar(20))";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
