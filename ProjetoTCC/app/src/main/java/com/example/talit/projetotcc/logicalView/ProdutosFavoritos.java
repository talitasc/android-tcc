package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 02/12/2017.
 */

public class ProdutosFavoritos {

    private int favorito_id;
    private int usuario_id;
    private int tipo_usuario_id;
    private int produto_id;
    private String produto_descricao;
    private String produto_status;
    private String produto_img_b64;
    private String estabelecimento_nome_fantasia;
    private int marca_id;
    private String marca_descricao;
    private int categoria_id;
    private String categoria_descricao;
    private int quantidade;
    private String unidade_medida_sigla;
    private int sub_categoria_id;
    private String sub_categoria_descricao;
    private int favorito;
    private int disponivel;

    public ProdutosFavoritos(int favorito_id, int usuario_id, int tipo_usuario_id, int produto_id, String produto_descricao, String produto_status, String produto_img_b64, String estabelecimento_nome_fantasia, int marca_id, String marca_descricao, int categoria_id, String categoria_descricao, int quantidade, String unidade_medida_sigla, int sub_categoria_id, String sub_categoria_descricao, int favorito, int disponivel) {
        this.favorito_id = favorito_id;
        this.usuario_id = usuario_id;
        this.tipo_usuario_id = tipo_usuario_id;
        this.produto_id = produto_id;
        this.produto_descricao = produto_descricao;
        this.produto_status = produto_status;
        this.produto_img_b64 = produto_img_b64;
        this.estabelecimento_nome_fantasia = estabelecimento_nome_fantasia;
        this.marca_id = marca_id;
        this.marca_descricao = marca_descricao;
        this.categoria_id = categoria_id;
        this.categoria_descricao = categoria_descricao;
        this.quantidade = quantidade;
        this.unidade_medida_sigla = unidade_medida_sigla;
        this.sub_categoria_id = sub_categoria_id;
        this.sub_categoria_descricao = sub_categoria_descricao;
        this.favorito = favorito;
        this.disponivel = disponivel;
    }

    public int getFavorito_id() {
        return favorito_id;
    }

    public void setFavorito_id(int favorito_id) {
        this.favorito_id = favorito_id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getTipo_usuario_id() {
        return tipo_usuario_id;
    }

    public void setTipo_usuario_id(int tipo_usuario_id) {
        this.tipo_usuario_id = tipo_usuario_id;
    }

    public int getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(int produto_id) {
        this.produto_id = produto_id;
    }

    public String getProduto_descricao() {
        return produto_descricao;
    }

    public void setProduto_descricao(String produto_descricao) {
        this.produto_descricao = produto_descricao;
    }

    public String getProduto_status() {
        return produto_status;
    }

    public void setProduto_status(String produto_status) {
        this.produto_status = produto_status;
    }

    public String getProduto_img_b64() {
        return produto_img_b64;
    }

    public void setProduto_img_b64(String produto_img_b64) {
        this.produto_img_b64 = produto_img_b64;
    }

    public String getEstabelecimento_nome_fantasia() {
        return estabelecimento_nome_fantasia;
    }

    public void setEstabelecimento_nome_fantasia(String estabelecimento_nome_fantasia) {
        this.estabelecimento_nome_fantasia = estabelecimento_nome_fantasia;
    }

    public int getMarca_id() {
        return marca_id;
    }

    public void setMarca_id(int marca_id) {
        this.marca_id = marca_id;
    }

    public String getMarca_descricao() {
        return marca_descricao;
    }

    public void setMarca_descricao(String marca_descricao) {
        this.marca_descricao = marca_descricao;
    }

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }

    public String getCategoria_descricao() {
        return categoria_descricao;
    }

    public void setCategoria_descricao(String categoria_descricao) {
        this.categoria_descricao = categoria_descricao;
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

    public int getSub_categoria_id() {
        return sub_categoria_id;
    }

    public void setSub_categoria_id(int sub_categoria_id) {
        this.sub_categoria_id = sub_categoria_id;
    }

    public String getSub_categoria_descricao() {
        return sub_categoria_descricao;
    }

    public void setSub_categoria_descricao(String sub_categoria_descricao) {
        this.sub_categoria_descricao = sub_categoria_descricao;
    }

    public int getFavorito() {
        return favorito;
    }

    public void setFavorito(int favorito) {
        this.favorito = favorito;
    }

    public int getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(int disponivel) {
        this.disponivel = disponivel;
    }
}
