package com.webbitmax.bitmax.model;

import java.util.List;

/**
 * Created by leonardo on 14/09/17.
 */

public class ServerRequest {

    private boolean result;
    private String mensagem;
    private int suporteID;
    private User user;
    private List<Chamados> chamados;


    public boolean isResult() {
        return result;
    }

    public String getMensagem() {
        return mensagem;
    }

    public int getSuporteID() {
        return suporteID;
    }

    public User getUser() {
        return user;
    }

    public List<Chamados> getChamados() {
        return chamados;
    }
}
