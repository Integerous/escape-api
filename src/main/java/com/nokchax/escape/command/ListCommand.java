package com.nokchax.escape.command;

import com.nokchax.escape.message.template.MessageMaker;
import com.nokchax.escape.mission.service.MissionService;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ListCommand extends Command<MissionService>{

    public ListCommand(Message message) {
        super(message);
    }

    @Override
    public String process() {
        MissionService processor = getProcessor();

        return MessageMaker.toMessage(
                processor.getAllUserInLatestMission(),
                "No users"
        );
    }
}