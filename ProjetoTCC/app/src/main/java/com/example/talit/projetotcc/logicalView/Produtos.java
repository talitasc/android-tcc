package com.example.talit.projetotcc.logicalView;

import android.graphics.Bitmap;

/**
 * Created by talit on 11/06/2017.
 */

public class Produtos {

    private int idProduto;
    private String descricao;
    private String imbase64;
    private String nomeFantasia;
    private String marcaDescricao;
    private String categoria_descr;
    private int quantidade;
    private String unidade_medida_sigla;
    private String sub_categoria_string;
    private String lote_data_fabricacao;
    private String lote_data_vencimento;
    private String lote_preco;
    private String lote_obs;
    private String lote_quantidade;

    public Produtos(int idProduto, String descricao, String imbase64, String nomeFantasia, String marcaDescricao, String categoria_descr, int quantidade, String unidade_medida_sigla, String sub_categoria_string, String lote_data_fabricacao, String lote_data_vencimento, String lote_preco, String lote_obs, String lote_quantidade) {

        this.idProduto = idProduto;
        this.descricao = descricao;
        this.imbase64 = imbase64;
        this.nomeFantasia = nomeFantasia;
        this.marcaDescricao = marcaDescricao;
        this.categoria_descr = categoria_descr;
        this.quantidade = quantidade;
        this.unidade_medida_sigla = unidade_medida_sigla;
        this.sub_categoria_string = sub_categoria_string;
        this.lote_data_fabricacao = lote_data_fabricacao;
        this.lote_data_vencimento = lote_data_vencimento;
        this.lote_preco = lote_preco;
        this.lote_obs = lote_obs;
        this.lote_quantidade = lote_quantidade;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImbase64() {
        return imbase64;
    }

    public void setImbase64(String imbase64) {
        this.imbase64 = imbase64;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getMarcaDescricao() {
        return marcaDescricao;
    }

    public void setMarcaDescricao(String marcaDescricao) {
        this.marcaDescricao = marcaDescricao;
    }

    public String getCategoria_descr() {
        return categoria_descr;
    }

    public void setCategoria_descr(String categoria_descr) {
        this.categoria_descr = categoria_descr;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getUnidade_medida_sigla() {
        return unidade_medida_sigla;
    }

    public void setUnidade_medida_sigla(String unidade_medida_sigla) {
        this.unidade_medida_sigla = unidade_medida_sigla;
    }

    public String getSub_categoria_string() {
        return sub_categoria_string;
    }

    public void setSub_categoria_string(String sub_categoria_string) {
        this.sub_categoria_string = sub_categoria_string;
    }

    public String getLote_data_fabricacao() {
        return lote_data_fabricacao;
    }

    public void setLote_data_fabricacao(String lote_data_fabricacao) {
        this.lote_data_fabricacao = lote_data_fabricacao;
    }

    public String getLote_data_vencimento() {
        return lote_data_vencimento;
    }

    public void setLote_data_vencimento(String lote_data_vencimento) {
        this.lote_data_vencimento = lote_data_vencimento;
    }

    public String getLote_preco() {
        return lote_preco;
    }

    public void setLote_preco(String lote_preco) {
        this.lote_preco = lote_preco;
    }

    public String getLote_obs() {
        return lote_obs;
    }

    public void setLote_obs(String lote_obs) {
        this.lote_obs = lote_obs;
    }

    public String getLote_quantidade() {
        return lote_quantidade;
    }

    public void setLote_quantidade(String lote_quantidade) {
        this.lote_quantidade = lote_quantidade;
    }
}
