package by.integrator.testbot.processor;

import by.integrator.testbot.bot.TestBot;
import by.integrator.testbot.interfaces.CommandProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Comparator;
import java.util.List;

@Service
public class SendPhotoProcessor implements CommandProcessor {

    private TestBot testBot;
    private StartProcessor startProcessor;


    @Autowired
    public void setStartProcessor(StartProcessor startProcessor) {
        this.startProcessor = startProcessor;
    }

    @Autowired
    public void setTestBot(TestBot testBot) {
        this.testBot = testBot;
    }

    @Override
    public void run(Update update) {
        if(update.getMessage().hasPhoto()){
            List<PhotoSize> photos = update.getMessage().getPhoto();
            String f_id = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getFileId();

            testBot.sendPhoto(update.getMessage(), f_id);
        } else testBot.sendMessage(update.getMessage(), "Image not found.");
        startProcessor.run(update);
    }
}
