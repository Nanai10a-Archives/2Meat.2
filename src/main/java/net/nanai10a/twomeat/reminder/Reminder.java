package net.nanai10a.twomeat.reminder;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.nanai10a.twomeat.CommandProcessingService;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class Reminder extends ListenerAdapter {
    private final Timer TIMER = new Timer(true);
    private final ConcurrentHashMap<String, TimerTask> QUEUE = new ConcurrentHashMap<>();


    public Reminder() {



    }

    private void setOneSchedule() {

    }

    private void setMultipleSchedule() {

    }

    private void deleteSchedule() {

    }

    private void changeSchedule() {

    }

    private void getQueue() {

    }



    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            isMatch(event.getMessage().getContentRaw(), event.getChannel());
        }
    }

    private void isMatch(String rawMessage, MessageChannel channel) {

        if (CommandProcessingService.match(rawMessage)) {
            onMethod(CommandProcessingService.process(rawMessage), channel);
        }
    }

    private void onMethod(String command, MessageChannel channel) {
        final String message;

        if (command.startsWith("Reminder.add")) {
            var listenerName = command.substring(command.indexOf("(") + 1, command.indexOf(")"));

        } else if (command.startsWith("Reminder.change")) {
            var listenerName = command.substring(command.indexOf("(") + 1, command.indexOf(")"));

        } else if (command.startsWith("Reminder.delete")) {
            var listenerName = command.substring(command.indexOf("(") + 1, command.indexOf(")"));

        } else if (command.startsWith("Reminder.queue")) {
            var listenerName = command.substring(command.indexOf("(") + 1, command.indexOf(")"));

        } else if (command.startsWith("Reminder.delet")) {
            var listenerName = command.substring(command.indexOf("(") + 1, command.indexOf(")"));

        } else if (command.startsWith("Reminder.delet")) {
            var listenerName = command.substring(command.indexOf("(") + 1, command.indexOf(")"));

        } else {
            message = "`メソッド名が不正です`";
        }

        //channel.sendMessage("2:Reminder | " + message).queue();
    }
}
