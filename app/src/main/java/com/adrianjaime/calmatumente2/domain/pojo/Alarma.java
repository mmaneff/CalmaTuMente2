package com.adrianjaime.calmatumente2.domain.pojo;

public class Alarma {

    private int id;
    private int hora;
    private int minuto;
    private int dia;
    private int lunes;
    private int martes;
    private int miercoles;
    private int jueves;
    private int viernes;
    private int sabados;
    private int domingos;
    private int isnotified;

    public Alarma(){}

    public Alarma(int hora, int minuto, int dia, int lunes, int martes, int miercoles,
                  int jueves, int viernes, int sabados, int domingos) {
        this.hora = hora;
        this.minuto = minuto;
        this.dia = dia;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabados = sabados;
        this.domingos = domingos;
        this.isnotified = 0;
    }

    public int getId() { return id; }
    public int getHora() {
        return this.hora;
    }
    public int getMinuto() {
        return this.minuto;
    }
    public int getDia() { return this.dia; }
    public int getLunes() {
        return this.lunes;
    }
    public int getMartes() {
        return this.martes;
    }
    public int getMiercoles() { return this.miercoles; }
    public int getJueves() {
        return this.jueves;
    }
    public int getViernes() {
        return this.viernes;
    }
    public int getSabados() {
        return this.sabados;
    }
    public int getDomingos() {
        return this.domingos;
    }
    public int getIsNotified() { return this.isnotified; }

    public void setId(int id) { this.id = id; }
    public void setHora(int hora) {
        this.hora = hora;
    }
    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }
    public void setDia(int dia) { this.dia = dia; }
    public void setLunes(int lunes) {
        this.lunes = lunes;
    }
    public void setMartes(int martes) {
        this.martes = martes;
    }
    public void setMiercoles(int miercoles) {
        this.miercoles = miercoles;
    }
    public void setJueves(int jueves) {
        this.jueves = jueves;
    }
    public void setViernes(int viernes) {
        this.viernes = viernes;
    }
    public void setSabados(int sabados) {
        this.sabados = sabados;
    }
    public void setDomingos(int domingos) {
        this.domingos = domingos;
    }
    public void setIsNotified(int isNotified) { this.isnotified = isNotified; }


    @Override
    public String toString() {
        String hora = (this.hora < 10) ? ("0" + Integer.toString(this.hora)) : Integer.toString(this.hora);
        String minuto = (this.minuto < 10) ? ("0" + Integer.toString(this.minuto)) : Integer.toString(this.minuto);
        return "Alarma: " + hora + ":" + minuto;
    }
}
