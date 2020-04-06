package com.nokchax.escape.command;

import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Message;

public class HelpCommand extends Command<Object> {

    public HelpCommand(Message message, ApplicationContext processors) {
        super(message, processors);
    }

    @Override
    public String internalProcess() {
        return "1. 문제풀고 나서 기록 업데이트 : /u or /update username\n" +
                "2. 미션 완료 사용자 리스트 : /d or /done\n" +
                "3. 금주 미션 point 달성 현황 리스트 : /t or /todo\n" +
                "4. 보너스(방학) 포인트 확인 : /po or /point\n" +
                "5. 미납 벌금 조회 : /f or /fine\n" +
                "6. 총 푼 문제 내역조회 : /h or /history username\n" +
                "7. 문제별 푼 사용자 리스트 : /pr or /problem 문제번호\n" +
                "8. 금주 미션 참가자 현황 : /l or /list\n\n" +
                "예) 문제를 푼다 > /update username > /todo";
    }
}
