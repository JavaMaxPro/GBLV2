package ru.gb.java2.chat.clientserver.commands;

import java.io.Serializable;

public class AuthOKCommandData  implements Serializable {

    private final String username;

    public AuthOKCommandData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
