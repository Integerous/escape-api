package com.nokchax.escape.command.commands;

import com.nokchax.escape.command.Command;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

public class UnknownCommand extends Command<Object> {

    public UnknownCommand(Message message, ApplicationContext processors) {
        super(message, processors);
    }

    @Override
    public String internalProcess() {
        return "Unknown command";
    }
}
