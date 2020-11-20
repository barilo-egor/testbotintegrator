package by.integrator.testbot.processor;

import by.integrator.testbot.bot.TestBot;
import by.integrator.testbot.enums.BotMessage;
import by.integrator.testbot.interfaces.CommandProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class StartProcessor implements CommandProcessor {

    private TestBot testBot;

    @Autowired
    public void setTestBot(TestBot testBot) {
        this.testBot = testBot;
    }

    @Override
    public void run(Update update) {
        testBot.sendMessage(update.getMessage(), BotMessage.START_MESSAGE, getKeyboard());
    }

    private ReplyKeyboardMarkup getKeyboard(){
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();

        row1.add("Enter my info.");
        row2.add("My info.");
        row3.add("Send photo.");

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(row3);

        keyboard.setKeyboard(keyboardRows);
        keyboard.setOneTimeKeyboard(true);
        return keyboard;
    }

}
