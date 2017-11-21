package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 20/11/2017.
 */

public class CarrinhoItens {


    private String carrinho_id;
    private String status_carrinho_id;
    private String status_carrinho_descricao;
    private String usuario_id;
    private String tipo_usuario_id;
    private String estabelecimento_id;
    private String carrinho_valor_total;

    public CarrinhoItens(String idCarrinho){
        this.carrinho_id = idCarrinho;
    }

    public CarrinhoItens(String valorTotal, String carrinhoId){
        this.carrinho_valor_total = valorTotal;
        this.carrinho_id = carrinhoId;
    }
    public CarrinhoItens(String carrinho_id, String status_carrinho_id, String status_carrinho_descricao, String usuario_id, String tipo_usuario_id, String estabelecimento_id, String carrinho_valor_total) {
        this.carrinho_id = carrinho_id;
        this.status_carrinho_id = status_carrinho_id;
        this.status_carrinho_descricao = status_carrinho_descricao;
        this.usuario_id = usuario_id;
        this.tipo_usuario_id = tipo_usuario_id;
        this.estabelecimento_id = estabelecimento_id;
        this.carrinho_valor_total = carrinho_valor_total;
    }

    public String getCarrinho_id() {
        return carrinho_id;
    }

    public void setCarrinho_id(String carrinho_id) {
        this.carrinho_id = carrinho_id;
    }

    public String getStatus_carrinho_id() {
        return status_carrinho_id;
    }

    public void setStatus_carrinho_id(String status_carrinho_id) {
        this.status_carrinho_id = status_carrinho_id;
    }

    public String getStatus_carrinho_descricao() {
        return status_carrinho_descricao;
    }

    public void setStatus_carrinho_descricao(String status_carrinho_descricao) {
        this.status_carrinho_descricao = status_carrinho_descricao;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getTipo_usuario_id() {
        return tipo_usuario_id;
    }

    public void setTipo_usuario_id(String tipo_usuario_id) {
        this.tipo_usuario_id = tipo_usuario_id;
    }

    public String getEstabelecimento_id() {
        return estabelecimento_id;
    }

    public void setEstabelecimento_id(String estabelecimento_id) {
        this.estabelecimento_id = estabelecimento_id;
    }

    public String getCarrinho_valor_total() {
        return carrinho_valor_total;
    }

    public void setCarrinho_valor_total(String carrinho_valor_total) {
        this.carrinho_valor_total = carrinho_valor_total;
    }
}
