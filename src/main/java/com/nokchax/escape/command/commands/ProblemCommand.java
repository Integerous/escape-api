package com.nokchax.escape.command.commands;

import com.nokchax.escape.command.Command;
import com.nokchax.escape.command.CommandMapping;
import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.problem.repository.ProblemRepository;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collections;

@CommandMapping(commands = {"problem", "pr"})
public class ProblemCommand extends Command<ProblemRepository> {
    private static final String NUMBER = "n";

    public ProblemCommand(Message message, ApplicationContext processors) {
        super(message, processors, Collections.singletonList(NUMBER)); //number
    }

    @Override
    public String internalProcess() {

        return MessageMaker.toMessage(
                processor().findProblemSolveUser(Long.parseLong(getDefaultArgument())),
                "Problem not exist"
        );
    }
}
