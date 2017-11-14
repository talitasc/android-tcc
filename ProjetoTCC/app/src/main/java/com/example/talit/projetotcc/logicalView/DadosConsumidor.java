package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 13/11/2017.
 */

public class DadosConsumidor {

    private int usuario_id;
    private int tipo_usuario_id;
    private String consumidor_nome;
    private String consumidor_sobrenome;
    private int tipo_telefone_id;
    private String telefone_ddd;
    private String telefone_numero;

    public DadosConsumidor(int usuario_id, int tipo_usuario_id, String consumidor_nome, String consumidor_sobrenome, int tipo_telefone_id, String telefone_ddd, String telefone_numero) {
        this.usuario_id = usuario_id;
        this.tipo_usuario_id = tipo_usuario_id;
        this.consumidor_nome = consumidor_nome;
        this.consumidor_sobrenome = consumidor_sobrenome;
        this.tipo_telefone_id = tipo_telefone_id;
        this.telefone_ddd = telefone_ddd;
        this.telefone_numero = telefone_numero;
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

    public String getConsumidor_nome() {
        return consumidor_nome;
    }

    public void setConsumidor_nome(String consumidor_nome) {
        this.consumidor_nome = consumidor_nome;
    }

    public String getConsumidor_sobrenome() {
        return consumidor_sobrenome;
    }

    public void setConsumidor_sobrenome(String consumidor_sobrenome) {
        this.consumidor_sobrenome = consumidor_sobrenome;
    }

    public int getTipo_telefone_id() {
        return tipo_telefone_id;
    }

    public void setTipo_telefone_id(int tipo_telefone_id) {
        this.tipo_telefone_id = tipo_telefone_id;
    }

    public String getTelefone_ddd() {
        return telefone_ddd;
    }

    public void setTelefone_ddd(String telefone_ddd) {
        this.telefone_ddd = telefone_ddd;
    }

    public String getTelefone_numero() {
        return telefone_numero;
    }

    public void setTelefone_numero(String telefone_numero) {
        this.telefone_numero = telefone_numero;
    }
}
