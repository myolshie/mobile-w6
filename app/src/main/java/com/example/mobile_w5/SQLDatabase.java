package com.example.mobile_w5;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLDatabase extends SQLiteOpenHelper {
    private Context context;
    public  static final String DATABASE_NAME = "Students.db";
    private static final int DATABASE_VERSION = 1;
    public  static final String TABLE_STUDENTS = "HOCSINH";
    public  static final String STUDENT_ID ="MaHS";
    public  static final String STUDENT_NAME ="TenHS";
    public  static final String STUDENT_SCORE ="Diem";
    public  static final String STUDENT_CLASSID = "MaLop";
    public  static final String TABLE_CLASSES = "LOP";
    public  static final String CLASS_ID ="MaLop";
    public  static final String CLASS_NAME ="TenLop";

    public SQLDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStudentsTable = "CREATE TABLE " + TABLE_STUDENTS + "(" +
                STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                STUDENT_NAME + " TEXT," +
                STUDENT_SCORE + " REAL," +
                CLASS_ID + " INTEGER)";
        db.execSQL(createStudentsTable);

        // Tạo bảng Classes
        String createClassesTable = "CREATE TABLE " + TABLE_CLASSES + "(" +
                CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CLASS_NAME + " TEXT)";
        db.execSQL(createClassesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSES);
        onCreate(db);
    }

    public long addStudent(String name, double score, int classId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_NAME, name);
        values.put(STUDENT_SCORE, score);
        values.put(CLASS_ID, classId);
        long id = db.insert(TABLE_STUDENTS, null, values);
        db.close();
        return id;
    }
    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_STUDENTS, null);
    }
    public long addClass(String className) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CLASS_NAME, className);
        long id = db.insert(TABLE_CLASSES, null, values);
        db.close();
        return id;
    }
    public Cursor getAllClasses() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CLASSES, null);
    }
}
