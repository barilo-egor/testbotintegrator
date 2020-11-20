package by.integrator.testbot.enums;

public enum Command {
    START("/start"),
    ENTER_INFO("Enter my info."),
    MY_INFO("My info."),
    SEND_PHOTO("Send photo."),
    ERROR("/none"),
    ENTER_FULL_NAME("/enterfullname"),
    ENTER_AGE("/enterage"),
    ENTER_ADDRESS("/enteraddress"),
    NONE("/none");

    String command;

    Command(String command){
        this.command = command;
    }

    public String getCommand(){
        return command;
    }
}
