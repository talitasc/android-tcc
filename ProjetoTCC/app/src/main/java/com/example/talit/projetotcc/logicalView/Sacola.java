package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 20/10/2017.
 */

public class Sacola {

    private int idProduto;
    private int lote_id;
    private String descrProduto;
    private String marca;
    private double preco;
    private double preco_un;
    private String und_med;
    private int quantidade;
    private String imgBase64;
    private String qtdLote;

    public Sacola(int idProduto, int lote_id, String descrProduto, String marca, double preco, double preco_un, String und_med, int quantidade, String imgBase64, String qtdLote) {
        this.idProduto = idProduto;
        this.lote_id = lote_id;
        this.descrProduto = descrProduto;
        this.marca = marca;
        this.preco = preco;
        this.preco_un = preco_un;
        this.und_med = und_med;
        this.quantidade = quantidade;
        this.imgBase64 = imgBase64;
        this.qtdLote = qtdLote;
    }

    public int getLote_id() {
        return lote_id;
    }

    public void setLote_id(int lote_id) {
        this.lote_id = lote_id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getDescrProduto() {
        return descrProduto;
    }

    public void setDescrProduto(String descrProduto) {
        this.descrProduto = descrProduto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getImgBase64() {
        return imgBase64;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }

    public double getPreco_un() {
        return preco_un;
    }

    public void setPreco_un(double preco_un) {
        this.preco_un = preco_un;
    }

    public String getUnd_med() {
        return und_med;
    }

    public void setUnd_med(String und_med) {
        this.und_med = und_med;
    }

    public String getQtdLote() {
        return qtdLote;
    }

    public void setQtdLote(String qtdLote) {
        this.qtdLote = qtdLote;
    }
}
