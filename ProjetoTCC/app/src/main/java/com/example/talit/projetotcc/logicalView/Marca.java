package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 29/10/2017.
 */

public class Marca {
    private int idMarca;
    private String descricao;
    private String imgbase64;

    public Marca(int idMarca, String descricao, String imgbase64) {
        this.idMarca = idMarca;
        this.descricao = descricao;
        this.imgbase64 = imgbase64;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImgbase64() {
        return imgbase64;
    }

    public void setImgbase64(String imgbase64) {
        this.imgbase64 = imgbase64;
    }
}
