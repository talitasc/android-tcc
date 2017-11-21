package com.example.talit.projetotcc.sqlight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;


import com.example.talit.projetotcc.logicalView.Busca;
import com.example.talit.projetotcc.logicalView.Produtos;
import com.example.talit.projetotcc.logicalView.Sacola;
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
    public void insertIdSacola(int idSacola){
        ContentValues valor = new ContentValues();
        valor.put("id_pod", idSacola);
        db.insert("sacola", null, valor);
    }
    public void insertSacola(int id_prod,int lote_id,String nomeProd,String marca, double preco, double preco_un,String un_med ,int qtd, String imagem, String qtdLote) {
        ContentValues valor = new ContentValues();
        valor.put("id_pod", id_prod);
        valor.put("lote_id", lote_id);
        valor.put("nomeProd", nomeProd);
        valor.put("marca", marca);
        valor.put("preco", preco);
        valor.put("preco_un", preco_un);
        valor.put("un_med", un_med);
        valor.put("qtd",qtd);
        valor.put("imagem",imagem);
        valor.put("qtdLote",qtdLote);
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
    public Sacola selectDadosSacola() {

        Cursor cursor = db.query(true, "sacola", null, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            return new Sacola(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getDouble(4),
                    cursor.getDouble(5),
                    cursor.getString(6),
                    cursor.getInt(7),
                    cursor.getString(8),
                    cursor.getString(9));
        }
        return null;
    }

    public ArrayList<Sacola> selectProutos(){
        ArrayList<Sacola> listas = new ArrayList<Sacola>();

        String[] colunas_db = new String[]{"id_pod","lote_id","nomeProd","marca","preco","preco_un","un_med","qtd","imagem","qtdLote"};
        Cursor cursor = db.query(true, "sacola", null, null, null, null, null, null, null);


        if(cursor.moveToFirst()){

            do{
                listas.add(new Sacola(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4),
                        cursor.getDouble(5),
                        cursor.getString(6),
                        cursor.getInt(7),
                        cursor.getString(8),
                        cursor.getString(9)));

            }while(cursor.moveToNext());
        }

        return listas;
    }

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
    public void updateQtdEpreco(int idLista,int qtd, double preco){

        ContentValues valor = new ContentValues();
        valor.put("qtd", qtd);
        valor.put("preco",preco);
        db.update("sacola",valor,"id_pod ="+idLista,null);
    }
    public Sacola selectIdProduto(int id){

        String[] colunas_db = new String[]{"id_pod","lote_id","nomeProd","marca","preco","preco_un","un_med","qtd","imagem","qtdLote"};
        Cursor cursor  = db.query(true,"sacola", colunas_db, "id_pod = '"+id+"'", null,null, null, null,null);

        if(cursor.moveToFirst()){

            do{
                return new Sacola(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4),
                        cursor.getDouble(5),
                        cursor.getString(6),
                        cursor.getInt(7),
                        cursor.getString(8),
                        cursor.getString(9));
            }while(cursor.moveToNext());

        }
        return null;
    }

   public double totalCarrinho(){

        Cursor cursor = db.rawQuery("SELECT SUM(preco * qtd ) FROM sacola",null);

        double amount = 0.00;
        if(cursor.moveToFirst())
        {
            amount = cursor.getDouble(0);
        }
        return amount;
    }

    public double totalPorItem(int id){

        Cursor cursor = db.rawQuery("SELECT (preco * qtd) FROM sacola WHERE id_pod =1",null);

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

        Cursor cursor = db.rawQuery("SELECT COUNT (*) FROM searchview ",null);

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

    public void deleteCarrinhoId(int codigo){
        db.delete("sacola","id_pod  ="+ codigo,null);
    }

    public void deleteCarrinhoNome(String nome){
        db.delete("sacola","nomeProd  ="+ nome,null);
    }

    public void deleteBusca(int id){
        db.delete("searchview","id_hist  ="+ id,null);
    }

    public void deleteHistorico() {
        db.execSQL("delete from searchview");
    }
}
