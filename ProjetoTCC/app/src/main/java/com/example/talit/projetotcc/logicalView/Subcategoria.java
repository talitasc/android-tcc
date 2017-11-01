package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 31/10/2017.
 */

public class Subcategoria {

    private int sub_categoria_id;
    private String sub_categoria_descricao;

    public Subcategoria(int sub_categoria_id, String sub_categoria_descricao) {
        this.sub_categoria_id = sub_categoria_id;
        this.sub_categoria_descricao = sub_categoria_descricao;
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
}
