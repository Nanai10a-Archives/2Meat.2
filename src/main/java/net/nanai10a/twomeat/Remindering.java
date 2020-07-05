package net.nanai10a.twomeat;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class Remindering extends ListenerAdapter {
    static final ConcurrentHashMap<Integer, Remindering> reminderingInstance = new ConcurrentHashMap<>();

    private final int id;
    private final JDA jda;

    public Remindering(JDA jda) {
        this.id = 0;
        this.jda = jda;
    }

    Remindering(int id, JDA jda) {
        this.id = id;
        this.jda = jda;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        var command = event.getMessage().getContentRaw();

        if (!event.getAuthor().isBot() &&
                Pattern.matches(Pattern.quote("*") + "2:.*<\\d*>\\..*\\(.*\\)", command)) {

        }
    }

    private static void create(MessageReceivedEvent event) {

        new RemideringUserCommandProcess("Remindering", 0, "create", 1,"\\d+").process(event);

        /*
        var command = event.getMessage().getContentRaw();
        if (!event.getAuthor().isBot() &&
                Pattern.matches(Pattern.quote("*") + "2:Remidnering<0>\\.create\\(.*\\)", command)) {

            var arg = command.substring(command.indexOf("(") + 1, command.indexOf(")"));
            arg = arg.replaceAll("\\s","");
            var count = arg.chars().filter(ch -> ch == ',').count();
            var channel = event.getMessage().getChannel();

            if (Pattern.matches("", arg)) {
                channel.sendMessage("2:Remidnering | `引数が必要です`").queue();

            } else if (count > 0) {
                channel.sendMessage("2:Remidnering | `引数は2つ以上渡せません`").queue();

            } else if (Pattern.matches("\\d+", arg)) {

                var num = Integer.parseInt(arg);

                if (num < 1) {
                    channel.sendMessage("2:Remindering | `1以上の値を指定してください。`");

                } else if (num > 0) {

                    if (reminderingInstance.containsKey(num)) {
                        reminderingInstance.put(num, new Remindering(num, this.jda));
                        channel.sendMessage("2:Remindering | `id: " + num + "で作成しました。`").queue();

                    } else {
                        channel.sendMessage("2:Remindering | `id: " + num + "は既に存在しています。`").queue();
                    }
                }
            }
        }
        */
    }


}


class RemideringUserCommandProcess extends UserCommandProcess {
    private final String serviceName;
    private final int id;
    private final String methodName;
    private final int argNum;
    private final String argPattern;
    protected Object[] returnObj;

    public RemideringUserCommandProcess(String serviceName, int id, String methodName, int argNum, String argPattern) {
        this.serviceName = serviceName;
        this.id = id;
        this.methodName = methodName;
        this.argNum = argNum;
        this.argPattern = argPattern;
    }

    public void process(MessageReceivedEvent event) {
        UserCommandProcess.process(this, event, (arg, instance, event1) -> {
            var channel = event1.getMessage().getChannel();
            if (instance.getArgPattern().equals("\\d+")) {
                var num = Integer.parseInt(arg);
                if (num < 1) {
                    channel.sendMessage("2:" + instance.getServiceName() + " | `1以上の値を指定してください。`").queue();
                } else {

                    if (Remindering.reminderingInstance.containsKey(num)) {
                        Remindering.reminderingInstance.put(num, new Remindering(num, event1.getJDA()));
                        channel.sendMessage("2:" + instance.getServiceName() + " | `id: " + num + "で作成しました。`").queue();
                    } else {
                        channel.sendMessage("2:" + instance.getServiceName() + " | `id: " + num + "は既に存在しています。`").queue();
                    }
                }
            }
        }
        );
    }


    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public int getArgNum() {
        return argNum;
    }

    @Override
    public String getArgPattern() {
        return argPattern;
    }

    @Override
    public Object[] getReturnObj() {
        return returnObj;
    }
}