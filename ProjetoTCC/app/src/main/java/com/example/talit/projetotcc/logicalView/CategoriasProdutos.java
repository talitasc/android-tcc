package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 11/06/2017.
 */

public class CategoriasProdutos {

    private int idCategoria;
    private String descricaoCategoria;

    public CategoriasProdutos(int idCategoria, String descricaoCategoria) {
        this.idCategoria = idCategoria;
        this.descricaoCategoria = descricaoCategoria;
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
}
