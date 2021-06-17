package ru.gb.java2.chat.server.chat.auth;

import java.util.Objects;

public class User {
    private final String login;
    private final String password;
    private final String username;

    private  boolean connect;

    public User(String login, String password, String username,boolean connect) {
        this.login = login;
        this.password = password;
        this.username = username;
        this.connect = connect;
    }

    public User(String login, String password) {
        this(login,password,null,false);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) && Objects.equals(password, user.password);
    }

    public boolean isConnect() {
        return connect;
    }

    public void setConnect(boolean connect) {
        this.connect = connect;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
