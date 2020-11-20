package by.integrator.testbot.processor;

import by.integrator.testbot.bot.TestBot;
import by.integrator.testbot.interfaces.CommandProcessor;
import by.integrator.testbot.models.User;
import by.integrator.testbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MyInfoProcessor implements CommandProcessor {

    private UserService userService;
    private TestBot testBot;
    private StartProcessor startProcessor;

    @Autowired
    public void setStartProcessor(StartProcessor startProcessor) {
        this.startProcessor = startProcessor;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTestBot(TestBot testBot) {
        this.testBot = testBot;
    }

    @Override
    public void run(Update update) {
        Integer userTelegramId = update.getMessage().getChatId().intValue();
        if(userService.existByTelegramId(userTelegramId)){
            User user = userService.getUser(userTelegramId);
            testBot.sendMessage(update.getMessage(), "Full name: " + user.getFullName() + "\nAge: " +
                    + user.getAge() + "\nAddress: " + user.getAddress());
        } else testBot.sendMessage(update.getMessage(), "No data.");
        startProcessor.run(update);
    }
}
