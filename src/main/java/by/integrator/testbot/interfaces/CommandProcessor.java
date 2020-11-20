package by.integrator.testbot.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandProcessor {
    void run(Update update) throws InterruptedException;
}
