package ru.gb.java2.chat.client;

import ru.gb.java2.chat.clientserver.Command;

public interface ReadCommandListener {
    void processReceivedCommand(Command command);
}
