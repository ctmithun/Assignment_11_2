package com.billionman.com.assignment_11_2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {
    private static final String SQL_QUERY = "SELECT * FROM BOOK_TABLE";
    SQLiteDatabase sqlDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BookDB db = new BookDB(this);
        sqlDb = db.getWritableDatabase();
        if(!isTableExists(sqlDb)) {
            insertSampleData();
        }
        String[] data;
        Cursor c = sqlDb.rawQuery(SQL_QUERY,null);
        data  = fetchDBData(c);
        sqlDb.close();
        ArrayAdapter ad = new ArrayAdapter(this,android.R.layout.select_dialog_item,data);
        AutoCompleteTextView act = (AutoCompleteTextView) findViewById(R.id.act);
        act.setThreshold(1);
        act.setAdapter(ad);
        act.setTextColor(Color.BLUE);
    }

    private boolean isTableExists(SQLiteDatabase db) {
            Cursor cursor = db.rawQuery(SQL_QUERY, null);
            if(cursor!=null) {
                if(cursor.getCount()>0) {
                    cursor.close();
                    System.out.println("-----------------------------------------------Table exists");
                    return true;
                }
                cursor.close();
            }
        return false;
    }

    private String[] fetchDBData(Cursor c) {
        String data[] = new String[c.getCount()];
        for (int i = 0; i< data.length;i++) {
            c.moveToPosition(i);
            data[i] = c.getString(0);
        }
        return data;
    }

    private void insertSampleData() {
        String sql1 = "insert into BOOK_TABLE (BOOK_NAME) values('BookA');";
        sqlDb.execSQL(sql1);
        sql1 = "insert into BOOK_TABLE (BOOK_NAME) values('BookB');";
        sqlDb.execSQL(sql1);
        sql1 = "insert into BOOK_TABLE (BOOK_NAME) values('BookC');";
        sqlDb.execSQL(sql1);
        sql1 = "insert into BOOK_TABLE (BOOK_NAME) values('BookD');";
        sqlDb.execSQL(sql1);
        sql1 = "insert into BOOK_TABLE (BOOK_NAME) values('BookZ');";
        sqlDb.execSQL(sql1);
    }
}
