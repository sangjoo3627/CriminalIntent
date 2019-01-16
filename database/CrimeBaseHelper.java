package com.bignerdranch.android.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable;

public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper (Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    // DB가 최초로 생성되는 경우 호출되는 메서드
    @Override
    public void onCreate (SQLiteDatabase db){
        db.execSQL("create table " + CrimeTable.NAME + "(" +
            " _id integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE + ", " +
                CrimeTable.Cols.SOLVED + ", " +
                CrimeTable.Cols.SUSPECT +
                ")"
        );


    }

    // 최초로 생성한 것이 아닌경우 DB의 버전 번호를 확인한 후 (여기서 버전이란, 우리가 정의한 스키마 버전을 말함)
    // CrimeOpenHelper의 생성자 인자로 전달한 번호가 DB에 저장된 것보다 높으면 호출되는 메서드
    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
