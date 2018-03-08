package com.webbitmax.bitmax.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by leonardo on 10/09/17.
 */

public class User extends RealmObject implements Serializable {

    @PrimaryKey
    private String usuario_login;
    private String usuario_nome;
    private String usuario_senha;
    private String usuario_email;
    private boolean logged;

    public User(){};

    public String getUsuario_login() {
        return usuario_login;
    }

    public void setUsuario_login(String usuario_login) {
        this.usuario_login = usuario_login;
    }

    public String getUsuario_nome() {
        return usuario_nome;
    }

    public void setUsuario_nome(String usuario_nome) {
        this.usuario_nome = usuario_nome;
    }

    public String getUsuario_senha() {
        return usuario_senha;
    }

    public void setUsuario_senha(String usuario_senha) {
        this.usuario_senha = usuario_senha;
    }

    public String getUsuario_email() {
        return usuario_email;
    }

    public void setUsuario_email(String usuario_email) {
        this.usuario_email = usuario_email;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}
