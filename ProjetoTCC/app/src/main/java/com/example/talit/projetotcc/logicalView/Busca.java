package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 14/10/2017.
 */

public class Busca {
    private int idString;
    private String txtBusca;

    public Busca(int idString, String txtBusca) {
        this.idString = idString;
        this.txtBusca = txtBusca;
    }

    public int getIdString() {
        return idString;
    }

    public void setIdString(int idString) {
        this.idString = idString;
    }

    public String getTxtBusca() {
        return txtBusca;
    }

    public void setTxtBusca(String txtBusca) {
        this.txtBusca = txtBusca;
    }
}
