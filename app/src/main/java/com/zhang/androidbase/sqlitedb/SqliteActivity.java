package com.zhang.androidbase.sqlitedb;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zhang.androidbase.R;

public class SqliteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
    }

    /**
     * 创建数据库
     * @param view
     */
    public void createDb(View view) {
        String dbName = "mengk.db";
        int version = 1;
        SqliteDbHelper dbHelper = new SqliteDbHelper(this, dbName, null, version);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
    }
}
