package by.integrator.testbot.bot;

import by.integrator.testbot.enums.Command;
import by.integrator.testbot.processor.*;
import by.integrator.testbot.service.CommandDispatcher;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
public class TestBot extends TelegramLongPollingBot {

    private Command currentChatState;
    private CommandDispatcher commandDispatcher;

    private EnterFullNameProcessor enterFullNameProcessor;
    private EnterAgeProcessor enterAgeProcessor;
    private EnterAddressProcessor enterAddressProcessor;
    private StartProcessor startProcessor;
    private SendPhotoProcessor sendPhotoProcessor;

    {
        currentChatState = Command.NONE;
    }
    public void setCurrentChatState(Command currentChatState) {
        this.currentChatState = currentChatState;
    }

    @Autowired
    public void setSendPhotoProcessor(SendPhotoProcessor sendPhotoProcessor) {
        this.sendPhotoProcessor = sendPhotoProcessor;
    }

    @Autowired
    public void setStartProcessor(StartProcessor startProcessor) {
        this.startProcessor = startProcessor;
    }

    @Autowired
    public void setEnterFullNameProcessor(EnterFullNameProcessor enterFullNameProcessor) {
        this.enterFullNameProcessor = enterFullNameProcessor;
    }

    @Autowired
    public void setEnterAgeProcessor(EnterAgeProcessor enterAgeProcessor) {
        this.enterAgeProcessor = enterAgeProcessor;
    }

    @Autowired
    public void setEnterAddressProcessor(EnterAddressProcessor enterAddressProcessor) {
        this.enterAddressProcessor = enterAddressProcessor;
    }

    @Autowired
    public void setCommandDispatcher(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        switch (currentChatState){
            case ENTER_FULL_NAME:
                enterFullNameProcessor.run(update);
                this.currentChatState = Command.NONE;
                break;
            case ENTER_AGE:
                enterAgeProcessor.run(update);
                this.currentChatState = Command.NONE;
                break;
            case ENTER_ADDRESS:
                enterAddressProcessor.run(update);
                this.currentChatState = Command.NONE;
                break;
            case SEND_PHOTO:
                sendPhotoProcessor.run(update);
                this.currentChatState = Command.NONE;
                break;
            default:
                commandDispatcher.dispatch(update);
        }
    }

    @Override
    public String getBotUsername() {
        return "testintegratorbot";
    }

    @Override
    public String getBotToken() {
        return "1462682986:AAExI3tyjHF7BhTqjcbdMQASFoSjtdO3TdU";
    }


    public void sendMessage(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId((message.getChatId().toString()));
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }

    public void sendMessage(Message message, String text, ReplyKeyboard replyKeyboard){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId((message.getChatId().toString()));
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(replyKeyboard);
        try {
            execute(sendMessage);
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }

    public void sendPhoto(Message message, String f_id){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(message.getChatId());
        sendPhoto.setPhoto(f_id);
        sendPhoto.setCaption("Can be with caption.");
        try {
            execute(sendPhoto);
        } catch (TelegramApiException ex){
            ex.printStackTrace();
        }
    }

    static {
        ApiContextInitializer.init();
    }
}
