package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 11/06/2017.
 */

public class CategoriasProdutos {

    private int idCategoria;
    private String descricaoCategoria;
    private String imagem64;

    public CategoriasProdutos(int idCategoria, String descricaoCategoria, String imagem64) {
        this.idCategoria = idCategoria;
        this.descricaoCategoria = descricaoCategoria;
        this.imagem64 = imagem64;
    }


    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescricaoCategoria() {
        return descricaoCategoria;
    }

    public void setDescricaoCategoria(String descricaoCategoria) {
        this.descricaoCategoria = descricaoCategoria;
    }

    public String getImagem64() {
        return imagem64;
    }

    public void setImagem64(String imagem64) {
        this.imagem64 = imagem64;
    }
}
