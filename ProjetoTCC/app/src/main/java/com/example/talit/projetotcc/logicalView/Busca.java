package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 14/10/2017.
 */

public class Busca {
    private int idString;
    private String strBusca;

    public Busca(int idString, String strBusca) {
        this.idString = idString;
        this.strBusca = strBusca;
    }

    public int getIdString() {
        return idString;
    }

    public void setIdString(int idString) {
        this.idString = idString;
    }

    public String getTxtBusca() {
        return strBusca;
    }

    public void setTxtBusca(String strBusca) {
        this.strBusca = strBusca;
    }
}
