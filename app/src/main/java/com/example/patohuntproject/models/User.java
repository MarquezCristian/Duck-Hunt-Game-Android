package com.example.patohuntproject.models;

public class User {
    private String nick;
    private int patos;

    public User() {
    }

    public User(String nick, int patos) {
        this.nick = nick;
        this.patos = patos;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getPatos() {
        return patos;
    }

    public void setPatos(int patos) {
        this.patos = patos;
    }
}
