package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 11/11/2017.
 */

public class EstabelecimentoFullText  {

    private int estabelecimento_id;
    private String estabelecimento_cnpj;
    private String estabelecimento_nome_fantasia;
    private String estabelecimento_cidade;
    private String image64;
    private String nota;

    public EstabelecimentoFullText(int estabelecimento_id, String estabelecimento_cnpj, String estabelecimento_nome_fantasia, String estabelecimento_cidade, String image64, String nota) {
        this.estabelecimento_id = estabelecimento_id;
        this.estabelecimento_cnpj = estabelecimento_cnpj;
        this.estabelecimento_nome_fantasia = estabelecimento_nome_fantasia;
        this.estabelecimento_cidade = estabelecimento_cidade;
        this.image64 = image64;
        this.nota = nota;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getImage64() {
        return image64;
    }

    public void setImage64(String image64) {
        this.image64 = image64;
    }

    public int getEstabelecimento_id() {
        return estabelecimento_id;
    }

    public void setEstabelecimento_id(int estabelecimento_id) {
        this.estabelecimento_id = estabelecimento_id;
    }

    public String getEstabelecimento_cnpj() {
        return estabelecimento_cnpj;
    }

    public void setEstabelecimento_cnpj(String estabelecimento_cnpj) {
        this.estabelecimento_cnpj = estabelecimento_cnpj;
    }

    public String getEstabelecimento_nome_fantasia() {
        return estabelecimento_nome_fantasia;
    }

    public void setEstabelecimento_nome_fantasia(String estabelecimento_nome_fantasia) {
        this.estabelecimento_nome_fantasia = estabelecimento_nome_fantasia;
    }

    public String getEstabelecimento_cidade() {
        return estabelecimento_cidade;
    }

    public void setEstabelecimento_cidade(String estabelecimento_cidade) {
        this.estabelecimento_cidade = estabelecimento_cidade;
    }
}
