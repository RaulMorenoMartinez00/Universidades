package com.pmdm.university.implementacion;

import android.content.ContentValues;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import com.pmdm.university.entidad.UniversityDetail;
import com.pmdm.university.interfaz.Repositorio;


public class UniversityDetailSQLiteHelper extends SQLiteOpenHelper implements Repositorio<UniversityDetail> {
    public UniversityDetailSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "University.db";

    public UniversityDetailSQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static class ContratoUniversity{
        private ContratoUniversity(){}
        public static class EntradaUniversity implements BaseColumns{
            public static final String TABLE_NAME = "UNIVERSITY_DETAIL";
            public static final String NAME = "NAME";
            public static final String IMAGE_URL = "IMAGE_URL";
            public static final String DESCRIPTION = "DESCRIPTION";

        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ContratoUniversity.EntradaUniversity.TABLE_NAME + " (" +
                ContratoUniversity.EntradaUniversity.NAME + " TEXT NOT NULL," +
                ContratoUniversity.EntradaUniversity.IMAGE_URL + " TEXT NOT NULL," +
                ContratoUniversity.EntradaUniversity.DESCRIPTION + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void insert(UniversityDetail universityDetail) {

        SQLiteDatabase db = getWritableDatabase();
        if(db == null)
            db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContratoUniversity.EntradaUniversity.IMAGE_URL, universityDetail.getImageUrl());
        values.put(ContratoUniversity.EntradaUniversity.DESCRIPTION, universityDetail.getDescription());
        db.insert(ContratoUniversity.EntradaUniversity.TABLE_NAME, null, values);
    }

    @Override
    public void update(UniversityDetail university) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContratoUniversity.EntradaUniversity.IMAGE_URL, university.getImageUrl());
        values.put(ContratoUniversity.EntradaUniversity.DESCRIPTION, university.getDescription());
        db.update(ContratoUniversity.EntradaUniversity.TABLE_NAME,
                values,
                "nombre=?",
                new String[] {university.getName()});
    }

    @Override
    public void delete(UniversityDetail university) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(ContratoUniversity.EntradaUniversity.TABLE_NAME,
                "nombre=?",
                new String[] {university.getName()});
    }
}

