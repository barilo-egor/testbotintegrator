package by.integrator.testbot.processor;

import by.integrator.testbot.bot.TestBot;
import by.integrator.testbot.enums.BotMessage;
import by.integrator.testbot.interfaces.CommandProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


@Service
public class EnterInfoProcessor implements CommandProcessor {

    private TestBot testBot;

    @Autowired
    public void setTestBot(TestBot testBot) {
        this.testBot = testBot;
    }

    @Override
    public void run(Update update) {
        testBot.sendMessage(update.getMessage(), BotMessage.ENTER_INFO_MESSAGE, getKeyboard());
    }

    private InlineKeyboardMarkup getKeyboard(){
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(new InlineKeyboardButton().setText("Enter full name.").setCallbackData("/enterfullname"));

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(new InlineKeyboardButton().setText("Enter age.").setCallbackData("/enterage"));

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(new InlineKeyboardButton().setText("Enter address.").setCallbackData("/enteraddress"));

        List<List<InlineKeyboardButton>> rows = List.of(row1, row2, row3);

        keyboard.setKeyboard(rows);
        return keyboard;
    }
}
