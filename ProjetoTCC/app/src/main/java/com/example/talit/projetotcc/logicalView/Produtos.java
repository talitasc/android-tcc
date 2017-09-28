package com.example.talit.projetotcc.logicalView;

import android.graphics.Bitmap;

/**
 * Created by talit on 11/06/2017.
 */

public class Produtos {

    private int idImagem;
    private int idProduto;
    private String marca;
    private String nome;
    private double preco;
    private String informacoes;
    private String prazoValidade;
    private String codReferencia;
    private byte[] b;


    public Produtos(int idImagem, String nome, String marca, double preco, byte[] b, String codRef) {


        this.idImagem = idImagem;
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.b = b;
        this.codReferencia = codRef;

    }

    public Produtos(int idImagem, int idProduto,  String nome,String marca,double preco, String informacoes,String prazoValidade) {

        this.idImagem = idImagem;
        this.idProduto = idProduto;
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.informacoes = informacoes;
        this.prazoValidade = prazoValidade;
    }

    public Produtos(int idImagem, int idProduto,  String nome,String marca,double preco, String informacoes,String prazoValidade,String codReferencia) {

        this.idImagem = idImagem;
        this.idProduto = idProduto;
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.informacoes = informacoes;
        this.prazoValidade = prazoValidade;
        this.codReferencia = codReferencia;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getIdImagem() {
        return idImagem;
    }

    public void setIdImagem(int idImagem) {
        this.idImagem = idImagem;
    }

    public String getInformacoes() {
        return informacoes;
    }

    public void setInformacoes(String informacoes) {
        this.informacoes = informacoes;
    }

    public String getPrazoValidade() {
        return prazoValidade;
    }

    public void setPrazoValidade(String prazoValidade) {
        this.prazoValidade = prazoValidade;
    }

    public byte[] getB() {
        return b;
    }

    public void setB(byte[] b) {
        this.b = b;
    }

    public String getCodReferencia() {
        return codReferencia;
    }

    public void setCodReferencia(String codReferencia) {
        this.codReferencia = codReferencia;
    }
}
