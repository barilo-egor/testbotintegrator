package by.integrator.testbot.processor;

import by.integrator.testbot.bot.TestBot;
import by.integrator.testbot.interfaces.CommandProcessor;
import by.integrator.testbot.models.User;
import by.integrator.testbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class EnterAddressProcessor implements CommandProcessor {

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
        String address = update.getMessage().getText();
        Integer userTelegramId = update.getMessage().getChatId().intValue();

        if(!userService.existByTelegramId(userTelegramId)){
            User user = new User();
            user.setAddress(address);
            user.setTelegramId(userTelegramId);
            userService.save(user);
        } else {
            User user = userService.getUser(userTelegramId);
            user.setAddress(address);
            userService.save(user);
        }

        testBot.sendMessage(update.getMessage(), "Saved.");
        startProcessor.run(update);
    }
}
