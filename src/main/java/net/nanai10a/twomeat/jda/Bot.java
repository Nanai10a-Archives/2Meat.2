package net.nanai10a.twomeat.jda;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.nanai10a.twomeat.CommandProcessingService;
import net.nanai10a.twomeat.dialoger.Dialoger;
import net.nanai10a.twomeat.yaml.Config;
import net.nanai10a.twomeat.yaml.ConfigItem;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.HashMap;

public class Bot extends ListenerAdapter {

    private final JDA JDA;
    private final HashMap<String, ListenerAdapter> LISTENERS = new HashMap<>();
    private final HashMap<String, Boolean> ISABLE = new HashMap<>();

    public Bot() throws LoginException, InterruptedException, IOException {
        JDA = JDABuilder.createDefault(System.getenv("DISCORD_TOKEN_2MEAT"))
                .addEventListeners(this)
                .build();
        JDA.awaitReady();

        fetchConfig();
        //Configと連携しようね…(今後に期待)

        LISTENERS.put("Dialoger", new Dialoger());

        if (ISABLE.get("Dialoger")) {
            JDA.addEventListener(LISTENERS.get("Dialoger"));
        }
    }

    private void fetchConfig() {
        //ISABLE.put("Reminder", Config.getIsUsable(ConfigItem.Reminder));
        ISABLE.put("Dialoger", Config.getIsUsable(ConfigItem.Dialoger));
        //ISABLE.put("ApproversStandardTime", Config.getIsUsable(ConfigItem.ApproversStandardTime));
    }

    private String enableListener(String listenerName) {
        final String result;
        if (LISTENERS.containsKey(listenerName)) {
            result = "0";
            JDA.addEventListener(LISTENERS.get(listenerName));
        } else {
            result = "listenerの名前が不正です";
        }
        return result;
    }

    private String disableListener(String listenerName) {
        final String result;
        if (LISTENERS.containsKey(listenerName)) {
            result = "0";
            JDA.removeEventListener(LISTENERS.get(listenerName));
        } else {
            result = "listenerの名前が不正です";
        }
        return result;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //ぬるぽするらしい
        if (!event.getAuthor().isBot()) {
            isMatch(event.getMessage().getContentRaw(), event.getChannel());
        }

    }

    private void isMatch(String rawMessage, MessageChannel channel) {

        switch (CommandProcessingService.matchx(rawMessage)) {
            case IGNORE:
                break;
            case NOMATCH:
                channel.sendMessage("2:Bot | `コマンドの書式が間違っています`").queue();
                break;
            case MATCH:
                onMethod(CommandProcessingService.process(rawMessage), channel);
                break;
        }
    }

    private void onMethod(String command, MessageChannel channel) {
        final String message;

        if (command.startsWith("Bot")) {
            if (command.startsWith("Bot.enableListener")) {
                var listenerName = command.substring(command.indexOf("(") + 1, command.indexOf(")"));
                fetchConfig();
                if (!ISABLE.get(listenerName)) {
                    enableListener(listenerName);
                    ISABLE.put(listenerName, true);
                    message = "`\"" + listenerName + "\"は有効化されました`";
                } else {
                    message = "`\"" + listenerName + "\"は既に有効化されています`";
                }

            } else if (command.startsWith("Bot.disableListener")) {
                var listenerName = command.substring(command.indexOf("(") + 1, command.indexOf(")"));
                if (ISABLE.get(listenerName)) {
                    disableListener(listenerName);
                    ISABLE.put(listenerName, false);
                    message = "`\"" + listenerName + "\"は無効化されました`";
                } else {
                    message = "`\"" + listenerName + "\"は既に無効化されています`";
                }

            } else {
                message = "`メソッド名が不正です`";
            }
            channel.sendMessage("2:Bot | " + message).queue();
        }
    }
}