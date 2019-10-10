package com.zum.escape.api.users.endpoint;

import com.zum.escape.api.observing.ObservingService;
import com.zum.escape.api.telegram.distributor.MessageDistributor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@RequiredArgsConstructor
public class EscapeHelpBot extends TelegramLongPollingBot {
    private final MessageDistributor messageDistributor;
    private final ObservingService observingService;
    @Value("${telegram.bot.name}")
    private String name;
    @Value("${telegram.bot.token}")
    private String token;
    @Value("${observing.noticeRoomNo}")
    private String noticeRoomNo;



    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        SendMessage message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText(messageDistributor.distributeMessage(update.getMessage()))
                .setParseMode(ParseMode.MARKDOWN);

        sendMessage(message);
    }


    @Scheduled(cron = "0 0/1 8-23 * * *")
    public void alarmPageUpdated() {
        log.info("Scheduling started");
        if(!observingService.scanPage())
            return;

        SendMessage message = new SendMessage()
                .setChatId(noticeRoomNo)
                .setText(observingService.getCurrentStatus());

        sendMessage(message);
    }

    private void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Fail to send response message : {}", e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return this.name;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }
}
