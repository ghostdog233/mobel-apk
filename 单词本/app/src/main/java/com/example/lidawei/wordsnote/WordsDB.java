package com.example.lidawei.wordsnote;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lidawei.wordsnote.word.Words;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordsDB {

    private static final String TAG = "myTag";

    private static WordsDBHelper mDbHelper;

    //采用单例模式
    private static WordsDB instance = new WordsDB();

    //get()方法
    public static WordsDB getWordsDB(){
        return WordsDB.instance;
    }

    private WordsDB() {
        if (mDbHelper == null) {
            mDbHelper = new WordsDBHelper(WordsApplication.getContext());
        }
    }


    public void close() {
        if (mDbHelper != null)
            mDbHelper.close();
    }

    //获得单个单词的全部信息
    public Words.WordDescription getSingleWord(String id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String sql = "select * from words where _ID=?";
        Cursor cursor = db.rawQuery(sql, new String[]{id});
        if (cursor.moveToNext()) {
            ;
            Words.WordDescription item = new Words.WordDescription(cursor.getString(cursor.getColumnIndex(Words.Word._ID)),
                    cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_WORD)),
                    cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_MEANING)),
                    cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_SAMPLE)));
            return item;
        }
        return null;

    }

    //有道根据单词内容查单词
    public Words.WordDescription getSingleWordByYouDao(String wor) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String sql = "select * from words where word=?";
        Cursor cursor = db.rawQuery(sql, new String[]{wor});
        if (cursor.moveToNext()) {
            ;
            Words.WordDescription item = new Words.WordDescription(cursor.getString(cursor.getColumnIndex(Words.Word._ID)),
                    cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_WORD)),
                    cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_MEANING)),
                    cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_SAMPLE)));
            return item;
        }
        return null;

    }

    //得到全部单词列表
    public ArrayList<Map<String, String>> getAllWords() {
        if (mDbHelper == null) {
            Log.v(TAG, "WordsDB::getAllWords()");
            return null;
        }

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                Words.Word._ID,
                Words.Word.COLUMN_NAME_WORD
        };

        //排序
        String sortOrder = Words.Word.COLUMN_NAME_WORD + " DESC";


        Cursor c = db.query(
                Words.Word.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return ConvertCursor2WordList(c);
    }

    //将游标转化为单词列表
    private ArrayList<Map<String, String>> ConvertCursor2WordList(Cursor cursor) {
        ArrayList<Map<String, String>> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<>();
            map.put(Words.Word._ID, String.valueOf(cursor.getString(cursor.getColumnIndex(Words.Word._ID))));
            map.put(Words.Word.COLUMN_NAME_WORD, cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_WORD)));
            result.add(map);
        }
        return result;
    }

    /**
     * 增加单词的两种方法
     * 1.使用Sql语句插入单词
     * 2.使用insert()方法增加单词
     */

    //使用Sql语句插入单词
    public void InsertUserSql(String strWord, String strMeaning, String strSample) {
        String sql = "insert into  words(_id,word,meaning,sample) values(?,?,?,?)";

        //以写入模式获取数据库
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(sql, new String[]{GUID.getGUID(),strWord, strMeaning, strSample});
    }

    //使用insert()方法增加单词
    public void Insert(String strWord) {
        //以写入模式获取数据库
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //对值创建一个新的map，列名是key
        ContentValues values = new ContentValues();
        values.put(Words.Word._ID, GUID.getGUID());
        values.put(Words.Word.COLUMN_NAME_WORD, strWord);


        //插入新的行，返回新行的主键值
        long newRowId;
        newRowId = db.insert(Words.Word.TABLE_NAME, null, values);
    }

    /**
     * 删除单词的两种方法
     * 1.使用Sql语句删除单词
     * 2.使用Delete()方法删除单词
     */

    //使用Sql语句删除单词
    public void DeleteUseSql(String strId) {
        String sql = "delete from words where _id='" + strId + "'";
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.execSQL(sql);
    }

    //使用Delete()方法删除单词
    public void Delete(String strId) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String selection = Words.Word._ID + " = ?";
        String[] selectionArgs = {strId};
        db.delete(Words.Word.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * 修改单词的两种方法
     * 1.使用Sql语句修改单词
     * 2.使用Update()方法修改单词
     */

    //使用Sql语句修改单词
    public void UpdateUseSql(String strId, String strWord, String strMeaning, String strSample) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String sql = "update words set word=?,meaning=?,sample=? where _id=?";
        db.execSQL(sql, new String[]{strWord,strMeaning, strSample, strId});
    }

    //使用Update()方法修改单词
    public void Update(String strId, String strWord, String strMeaning, String strSample) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Words.Word.COLUMN_NAME_WORD, strWord);
        values.put(Words.Word.COLUMN_NAME_MEANING, strMeaning);
        values.put(Words.Word.COLUMN_NAME_SAMPLE, strSample);

        String selection = Words.Word._ID + " = ?";
        String[] selectionArgs = {strId};
        int count = db.update(
                Words.Word.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    /**
     * 查找单词的两种方法
     * 1.使用Sql语句查找单词
     * 2.使用Search()方法查找单词
     */

    //使用Sql语句查找
    public ArrayList<Map<String, String>> SearchUseSql(String strWordSearch) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String sql = "select * from words where word like ? order by word desc";
        Cursor c = db.rawQuery(sql, new String[]{"%" + strWordSearch + "%"});
        return ConvertCursor2WordList(c);
    }

    //使用Search()方法查找
    public ArrayList<Map<String, String>> Search(String strWordSearch) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                Words.Word._ID,
                Words.Word.COLUMN_NAME_WORD
        };
        String sortOrder =
                Words.Word._ID + " DESC";
        String selection = Words.Word._ID + " LIKE ?";
        String[] selectionArgs = {"%" + strWordSearch + "%"};
        Cursor c = db.query(
                Words.Word.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        return ConvertCursor2WordList(c);
    }

}
