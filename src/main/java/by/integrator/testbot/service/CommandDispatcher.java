package by.integrator.testbot.service;

import by.integrator.testbot.bot.TestBot;
import by.integrator.testbot.enums.Command;
import by.integrator.testbot.processor.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CommandDispatcher {
    private static final Logger log = LoggerFactory.getLogger(CommandDispatcher.class);

    private TestBot testBot;
    private StartProcessor startProcessor;
    private EnterInfoProcessor enterInfoProcessor;
    private MyInfoProcessor myInfoProcessor;

    @Autowired
    public void setTestBot(TestBot testBot) {
        this.testBot = testBot;
    }

    @Autowired
    public void setEnterInfoProcessor(EnterInfoProcessor enterInfoProcessor) {
        this.enterInfoProcessor = enterInfoProcessor;
    }

    @Autowired
    public void setStartProcessor(StartProcessor startProcessor) {
        this.startProcessor = startProcessor;
    }

    @Autowired
    public void setMyInfoProcessor(MyInfoProcessor myInfoProcessor) {
        this.myInfoProcessor = myInfoProcessor;
    }

    public void dispatch(Update update) {
        if(update.hasMessage()) {
            switch (getCommandFromMessage(update)) {
                case ENTER_INFO:
                    enterInfoProcessor.run(update);
                    break;
                case MY_INFO:
                    myInfoProcessor.run(update);
                    break;
                case SEND_PHOTO:
                    testBot.sendMessage(update.getMessage(), "Send your photo.");
                    testBot.setCurrentChatState(Command.SEND_PHOTO);
                    break;
                case START:
                    startProcessor.run(update);
                    break;
                default:
                    log.warn("Error.");
                    break;

            }
        } else if (update.hasCallbackQuery()) setCurrentChatState(update);
    }

    private Command getCommandFromMessage(Update update){
            Message message = update.getMessage();
            if(message != null && message.hasText()){
                String text = message.getText();
                if(text.startsWith(Command.START.getCommand())){
                    return Command.START;
                } if(text.startsWith(Command.ENTER_INFO.getCommand())){
                    return Command.ENTER_INFO;
                } if(text.startsWith(Command.MY_INFO.getCommand())){
                    return Command.MY_INFO;
                } if(text.startsWith(Command.SEND_PHOTO.getCommand())){
                    return Command.SEND_PHOTO;
                }
            }
        return Command.ERROR;
    }

    private void setCurrentChatState(Update update){
        CallbackQuery callbackQuery = update.getCallbackQuery();
        if(callbackQuery != null && callbackQuery.getData() != null){
            String text = callbackQuery.getData();
            if(text.startsWith(Command.ENTER_FULL_NAME.getCommand())){
                testBot.sendMessage(callbackQuery.getMessage(), "Enter full name.");
                testBot.setCurrentChatState(Command.ENTER_FULL_NAME);
            } if(text.startsWith(Command.ENTER_AGE.getCommand())){
                testBot.sendMessage(callbackQuery.getMessage(), "Enter age.");
                testBot.setCurrentChatState(Command.ENTER_AGE);
            } if(text.startsWith(Command.ENTER_ADDRESS.getCommand())){
                testBot.sendMessage(callbackQuery.getMessage(), "Enter address.");
                testBot.setCurrentChatState(Command.ENTER_ADDRESS);
            }
        }
    }
}
