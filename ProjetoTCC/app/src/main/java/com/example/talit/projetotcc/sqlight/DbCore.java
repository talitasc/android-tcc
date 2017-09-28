package com.example.talit.projetotcc.sqlight;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by talit on 19/03/2017.
 */

public class DbCore extends SQLiteOpenHelper {

    private static final String DB_NAME = "SUPERMERCADO_CLIENTE";
    private static final int DB_VERSION = 10;

    public DbCore(Context c){
        super(c,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table consumidor(id_cons integer,usuario text,senha text,status integer,tp_acesso integer)");
        db.execSQL("create table sacola(id_lista integer primary key autoincrement,nomeProd text,marca text,preco real, imagem blob,codRef text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table consumidor;");
        db.execSQL("drop table sacola;");
        onCreate(db);
    }
}
