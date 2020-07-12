package net.nanai10a.old.twomeat.old.twomeat;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.regex.Pattern;

public abstract class UserCommandProcess implements UserCommandProcessInterface {

    protected static void process(UserCommandProcess instance, MessageReceivedEvent event, CommandReaction reaction) {

        var command = event.getMessage().getContentRaw();
        var channel = event.getMessage().getChannel();

        var commandServiceName = command.substring(command.indexOf(":") + 1, command.indexOf("<"));
        var commandId = Integer.parseInt(command.substring(command.indexOf("<") + 1, command.indexOf(">")));
        var commandMethodName = command.substring(command.indexOf(".") + 1, command.indexOf("("));


        if (commandServiceName.equals("")) {
            channel.sendMessage("2: | `サービス名を指定してください`").queue();

        } else if (String.valueOf(commandId).equals("")) {
            channel.sendMessage("2: | `idを指定してください`").queue();

        } else if (commandId < 0) {
            channel.sendMessage("2: | `idは0以上の値を指定してください`").queue();

        } else if (commandMethodName.equals("")) {
            channel.sendMessage("2: | `メソッド名を指定してください`").queue();

        } else {

            if (!event.getAuthor().isBot() &&
                    Pattern.matches(Pattern.quote("*") + "2:" + instance.getServiceName() + "<" + instance.getId() + ">\\."  + instance.getMethodName() + "\\(.*\\)", command)) {

                var arg = command.substring(command.indexOf("(") + 1, command.indexOf(")"));
                arg = arg.replaceAll("\\s","");
                var commaCount = arg.chars().filter(ch -> ch == ',').count();
                int commaNum;
                if (instance.getArgNum() > 0) {
                    commaNum = (instance.getArgNum() - 1);
                } else {
                    commaNum = -1;
                }
                if (Pattern.matches("", arg) && (commaNum > -1 )) {
                    channel.sendMessage("2:" + instance.getServiceName() + " | `引数が必要です`").queue();

                } else if (commaCount > commaNum) {
                    channel.sendMessage("2:" + instance.getServiceName() + " | `引数は" + (instance.getArgNum() + 1) + "つ以上渡せません`").queue();

                } else if (Pattern.matches(instance.getArgPattern(), arg)) {
                    reaction.run(arg, instance, event);

                }
            }

        }
    }
}



interface UserCommandProcessInterface {
    String getServiceName();
    int getId();
    String getMethodName();
    int getArgNum();
    String getArgPattern();
    Object[] getReturnObj();
}

interface CommandReaction {
    void run(String arg, UserCommandProcess instance, MessageReceivedEvent event);
}
