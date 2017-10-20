package com.example.talit.projetotcc.sqlight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;


import com.example.talit.projetotcc.logicalView.Busca;
import com.example.talit.projetotcc.logicalView.Produtos;
import com.example.talit.projetotcc.logicalView.Usuario;

import java.util.ArrayList;

/**
 * Created by talit on 19/03/2017.
 */

public class DbConn {

    private final SQLiteDatabase db;

    public DbConn(Context c) {
        DbCore dbcore = new DbCore(c);
        db = dbcore.getWritableDatabase();
    }
    public void insertConsumidor(int idConsumidor,String usuario,String senha, int status, int tpAcesso) {
        ContentValues valor = new ContentValues();
        valor.put("id_cons ", idConsumidor);
        valor.put("usuario", usuario);
        valor.put("senha", senha);
        valor.put("status", status);
        valor.put("tp_acesso", tpAcesso);
        db.insert("consumidor", null, valor);
    }

    public void insertSacola(String nomeProd,String marca, double preco, Bitmap b, String codRef) {
        ContentValues valor = new ContentValues();
        valor.put("nomeProd", nomeProd);
        valor.put("marca", marca);
        valor.put("preco", preco);
        valor.put("imagem", String.valueOf(b));
        valor.put("codRef", codRef);
        db.insert("sacola", null, valor);

    }
    public void insertSearchView(String busca){
        ContentValues valor = new ContentValues();
        valor.put("busca",busca);
        db.insert("searchview", null, valor);
    }
    public MantemConsumidor selectConsumidor() {

        Cursor cursor = db.query(true, "consumidor", null, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            return new MantemConsumidor(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4));
        }
        return null;
    }
   /*mudar o oonstrtor depois
    public ArrayList<Produtos> selectProutos(){
        ArrayList<Produtos> listas = new ArrayList<Produtos>();

        String[] colunas_db = new String[]{"id_lista","nomeProd","marca","preco","imagem","codRef"};
        Cursor cursor = db.query(true, "sacola", null, null, null, null, null, null, null);

        if(cursor.moveToFirst()){

            do{
                listas.add(new Produtos(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getDouble(3),
                        cursor.getBlob(4),
                        cursor.getString(5)));

            }while(cursor.moveToNext());

        }

        return listas;
    }*/

    public ArrayList<Busca> selectHistorico(){
        ArrayList<Busca> listas = new ArrayList<Busca>();

        String[] colunas_db = new String[]{"id_hist","busca"};
        Cursor cursor = db.query(true, "searchview", null, null, null, null, null, null, null);

        if(cursor.moveToFirst()){

            do{
                listas.add(new Busca(cursor.getInt(0),
                        cursor.getString(1)));

            }while(cursor.moveToNext());

        }
        return listas;
    }
    public void updateSenha(String senha, int id_status,int id_cons){
        ContentValues valor = new ContentValues();
        valor.put("senha", senha);
        valor.put("status", id_status);
        db.update("consumidor",valor,"id_cons ="+id_cons,null);
    }
    public void updateConsumidor(int id_cons,String senha){

        ContentValues valor = new ContentValues();
        valor.put("senha", senha);
        db.update("consumidor",valor,"id_cons ="+id_cons,null);
    }
    /*mudr o construtor depois
    public Produtos selectIdProduto(String nome){

        String[] colunas_db = new String[]{"id_lista","nomeProd","marca","preco","imagem","codRef"};
        Cursor cursor  = db.query(true,"sacola", colunas_db, "nomeProd = '"+nome+"'", null,null, null, null,null);

        if(cursor.moveToFirst()){

            do{
                return new Produtos(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getDouble(3),
                        cursor.getBlob(4),
                        cursor.getString(5));
            }while(cursor.moveToNext());

        }
        return null;
    }*/

   public double totalCarrinho(){

        Cursor cursor = db.rawQuery("SELECT SUM(preco) FROM sacola",null);

        double amount = 0.00;
        if(cursor.moveToFirst())
        {
            amount = cursor.getDouble(0);
        }
        return amount;
    }

    public int totalItensCarrinho(){

        Cursor cursor = db.rawQuery("SELECT COUNT (*) FROM sacola",null);

        int count = 0;
        if(cursor.moveToFirst())
        {
            count = cursor.getInt(0);
        }
        return count;
    }

    public int totalBuscas(){

        Cursor cursor = db.rawQuery("SELECT COUNT (*) FROM searchview",null);

        int count = 0;
        if(cursor.moveToFirst())
        {
            count = cursor.getInt(0);
        }
        return count;
    }

    public void deleteConsumidor() {
        db.execSQL("delete from consumidor");
    }

    public void deleteSacola() {
        db.execSQL("delete from sacola");
    }

    public void deleteCarrinhoId(String codigo){
        db.delete("sacola","codRef  ="+ codigo,null);
    }

    public void deleteBusca(int id){
        db.delete("searchview","id_hist  ="+ id,null);
    }

    public void deleteHistorico() {
        db.execSQL("delete from searchview");
    }
}
