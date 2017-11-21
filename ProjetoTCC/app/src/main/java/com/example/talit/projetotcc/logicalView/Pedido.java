package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 21/11/2017.
 */

public class Pedido {

    private String pedido_id;
    private String estabelecimento_id;
    private String pedido_valor;
    private String pedido_data;
    private String pedido_status_id;
    private String status_pedido_descricao;

    public Pedido(String pedido_id, String estabelecimento_id, String pedido_valor, String pedido_data, String pedido_status_id, String status_pedido_descricao) {
        this.pedido_id = pedido_id;
        this.estabelecimento_id = estabelecimento_id;
        this.pedido_valor = pedido_valor;
        this.pedido_data = pedido_data;
        this.pedido_status_id = pedido_status_id;
        this.status_pedido_descricao = status_pedido_descricao;
    }

    public String getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(String pedido_id) {
        this.pedido_id = pedido_id;
    }

    public String getEstabelecimento_id() {
        return estabelecimento_id;
    }

    public void setEstabelecimento_id(String estabelecimento_id) {
        this.estabelecimento_id = estabelecimento_id;
    }

    public String getPedido_valor() {
        return pedido_valor;
    }

    public void setPedido_valor(String pedido_valor) {
        this.pedido_valor = pedido_valor;
    }

    public String getPedido_data() {
        return pedido_data;
    }

    public void setPedido_data(String pedido_data) {
        this.pedido_data = pedido_data;
    }

    public String getPedido_status_id() {
        return pedido_status_id;
    }

    public void setPedido_status_id(String pedido_status_id) {
        this.pedido_status_id = pedido_status_id;
    }

    public String getStatus_pedido_descricao() {
        return status_pedido_descricao;
    }

    public void setStatus_pedido_descricao(String status_pedido_descricao) {
        this.status_pedido_descricao = status_pedido_descricao;
    }
}

