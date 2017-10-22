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
    private int quantidade;
    private String imgBase64;

    public Sacola(int idProduto, int lote_id, String descrProduto, String marca, double preco, int quantidade, String imgBase64) {
        this.idProduto = idProduto;
        this.lote_id = lote_id;
        this.descrProduto = descrProduto;
        this.marca = marca;
        this.preco = preco;
        this.quantidade = quantidade;
        this.imgBase64 = imgBase64;
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
}
