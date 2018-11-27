package com.beyondthecode.pithubproject.domain;

import com.google.gson.annotations.SerializedName;

public class Cliente {

    @SerializedName("idusuario")
    private String idcli;

    @SerializedName("email")
    private String emailcli;

    @SerializedName("clave")
    private String clavecli;

    @SerializedName("idtipo_usuario")
    private String tipoCli;

    @SerializedName("vigente")
    private String vigente;

    public String getIdcli() {
        return idcli;
    }

    public void setIdcli(String idcli) {
        this.idcli = idcli;
    }

    public String getEmailcli() {
        return emailcli;
    }

    public void setEmailcli(String emailcli) {
        this.emailcli = emailcli;
    }

    public String getClavecli() {
        return clavecli;
    }

    public void setClavecli(String clavecli) {
        this.clavecli = clavecli;
    }

    public String getTipoCli() {
        return tipoCli;
    }

    public void setTipoCli(String tipoCli) {
        this.tipoCli = tipoCli;
    }

    public String getVigente() {
        return vigente;
    }

    public void setVigente(String vigente) {
        this.vigente = vigente;
    }
}
