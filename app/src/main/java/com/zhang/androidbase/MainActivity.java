package com.zhang.androidbase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zhang.androidbase.sqlitedb.SqliteActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void jump(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    public void sqliteDb(View view) {
        jump(SqliteActivity.class);
    }
}
