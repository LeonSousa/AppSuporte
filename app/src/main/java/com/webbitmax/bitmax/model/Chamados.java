package com.webbitmax.bitmax.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by leonardo on 08/09/17.
 */

public class Chamados extends RealmObject implements Serializable {

    @PrimaryKey
    int suporte_id;
    String numero;
    String prioridade;
    int cliente;
    String tipo;
    String prazodata;
    String status;
    String resolvido;
    String mensagem;
    String iniciado;
    String fechado;
    String tecnico;
    String relatorio;
    String fechamento;
    boolean execucao;
    String pendencia;
    String datapendencia;
    String notapendencia;
    String mcabo;
    String qconector;
    String ondem;
    String cliente_nome;
    String codigo;
    String telefone;
    String celular;
    String cliente_endereco;
    String cliente_numero;
    String cliente_complemento;
    String cliente_bairro;
    String cliente_cidade;
    String cliente_login;
    String cliente_senha;
    String cliente_mac;
    String tipoconexao;
    String groupname;
    String up;
    String down;
    boolean result;
    boolean initiated;
    boolean update;

    public Chamados(){}


    public boolean isInitiated() {
        return initiated;
    }

    public void setInitiated(boolean initiated) {
        this.initiated = initiated;
    }

    public int getSuporte_id() {
        return suporte_id;
    }

    public void setSuporte_id(int suporte_id) {
        this.suporte_id = suporte_id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrazodata() {
        return prazodata;
    }

    public void setPrazodata(String prazodata) {
        this.prazodata = prazodata;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResolvido() {
        return resolvido;
    }

    public void setResolvido(String resolvido) {
        this.resolvido = resolvido;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getIniciado() {
        return iniciado;
    }

    public void setIniciado(String iniciado) {
        this.iniciado = iniciado;
    }

    public String getFechado() {
        return fechado;
    }

    public void setFechado(String fechado) {
        this.fechado = fechado;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    public String getFechamento() {
        return fechamento;
    }

    public void setFechamento(String fechamento) {
        this.fechamento = fechamento;
    }

    public boolean isExecucao() {
        return  execucao;
    }

    public void setExecucao(boolean execucao) {
        this.execucao = execucao;
    }

    public String getPendencia() {
        return pendencia;
    }

    public void setPendencia(String pendencia) {
        this.pendencia = pendencia;
    }

    public String getDatapendencia() {
        return datapendencia;
    }

    public void setDatapendencia(String datapendencia) {
        this.datapendencia = datapendencia;
    }

    public String getNotapendencia() {
        return notapendencia;
    }

    public void setNotapendencia(String notapendencia) {
        this.notapendencia = notapendencia;
    }

    public String getMcabo() {
        return mcabo;
    }

    public void setMcabo(String mcabo) {
        this.mcabo = mcabo;
    }

    public String getQconector() {
        return qconector;
    }

    public void setQconector(String qconector) {
        this.qconector = qconector;
    }

    public String getOndem() {
        return ondem;
    }

    public void setOndem(String ondem) {
        this.ondem = ondem;
    }

    public String getCliente_nome() {
        return cliente_nome;
    }

    public void setCliente_nome(String cliente_nome) {
        this.cliente_nome = cliente_nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCliente_endereco() {
        return cliente_endereco;
    }

    public void setCliente_endereco(String cliente_endereco) {
        this.cliente_endereco = cliente_endereco;
    }

    public String getCliente_numero() {
        return cliente_numero;
    }

    public void setCliente_numero(String cliente_numero) {
        this.cliente_numero = cliente_numero;
    }

    public String getCliente_complemento() {
        return cliente_complemento;
    }

    public void setCliente_complemento(String cliente_complemento) {
        this.cliente_complemento = cliente_complemento;
    }

    public String getCliente_bairro() {
        return cliente_bairro;
    }

    public void setCliente_bairro(String cliente_bairro) {
        this.cliente_bairro = cliente_bairro;
    }

    public String getCliente_cidade() {
        return cliente_cidade;
    }

    public void setCliente_cidade(String cliente_cidade) {
        this.cliente_cidade = cliente_cidade;
    }

    public String getCliente_login() {
        return cliente_login;
    }

    public void setCliente_login(String cliente_login) {
        this.cliente_login = cliente_login;
    }

    public String getCliente_senha() {
        return cliente_senha;
    }

    public void setCliente_senha(String cliente_senha) {
        this.cliente_senha = cliente_senha;
    }

    public String getCliente_mac() {
        return cliente_mac;
    }

    public void setCliente_mac(String cliente_mac) {
        this.cliente_mac = cliente_mac;
    }

    public String getTipoconexao() {
        return tipoconexao;
    }

    public void setTipoconexao(String tipoconexao) {
        this.tipoconexao = tipoconexao;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getUp() {
        return up;
    }

    public void setUp(String up) {
        this.up = up;
    }

    public String getDown() {
        return down;
    }

    public void setDown(String down) {
        this.down = down;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
