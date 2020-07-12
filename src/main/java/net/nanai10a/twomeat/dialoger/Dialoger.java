package net.nanai10a.twomeat.dialoger;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.nanai10a.twomeat.CommandProcessingService;
import net.nanai10a.twomeat.jda.ReactionBlockingQueue;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Dialoger extends ListenerAdapter {

    private final ConcurrentHashMap<String, String> REACTIONMAP = new ConcurrentHashMap<>();

    private final CopyOnWriteArrayList<String> PERFECTLIST = new CopyOnWriteArrayList<>();
    private final ConcurrentHashMap<String, Integer> SPECIFIEDLOCATIONMAP = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<String> INCLUSIVELIST = new CopyOnWriteArrayList<>();

    public Dialoger() {

    }

    /*
    ・input → reaction
    ・一つの入力に対して一つのリアクションを確実にする(reactionを決定するプログラムが必要) → 文字数が多い方
    ・一致の仕方は設定で変更可能
    ・
     */

    private String addPerfectReaction(String input, String reaction) {
        String message;

        if (REACTIONMAP.containsKey(input)) {
            message = "`もう既にその入力は登録されています`";
        } else {
            REACTIONMAP.put(input, reaction);
            PERFECTLIST.add(input);
            message = "`" + input + "→" + reaction + "(" + "完全一致" + ")、登録しました`";
        }

        return message;
    }

    private String addSpecifiedLocationReaction(String input, String reaction, int location) {
        String message;

        if (REACTIONMAP.containsKey(input)) {
            message = "`もう既にその入力は登録されています`";
        } else {
            REACTIONMAP.put(input, reaction);
            SPECIFIEDLOCATIONMAP.put(input, location);

            if (location > 0) {
                message = "`" + input + "→" + reaction + "(先頭から" + location + "文字目)、登録しました`";
            } else {
                message = "`" + input + "→" + reaction + "(末尾から" + (Math.abs(location)) + "文字目)、登録しました`";
            }
        }

        return message;
    }

    private String addInclusiveReaction(String input, String reaction) {
        String message;

        if (REACTIONMAP.containsKey(input)) {
            message = "`もう既にその入力は登録されています`";
        } else {
            REACTIONMAP.put(input, reaction);
            INCLUSIVELIST.add(input);
            message = "`" + input + "→" + reaction + "(" + "内包" + ")、登録しました`";
        }

        return message;
    }

    /*
    private String changeReaction(String input, String reaction) {
        String message;



        return message;
    }

    private String deleteReaction(String input) {
        String message;



        return message;
    }
     */

    private void isReactionMatch(String message, MessageChannel channel) {
            onPerfect(message, channel);
    }

    private void onPerfect(String message, MessageChannel channel) {
        if (PERFECTLIST.contains(message)) {
            react(REACTIONMAP.get(message), channel);
        } else {
            onSpecifiedLocation(message, channel);
        }
    }

    private void onSpecifiedLocation(String message, MessageChannel channel) {

        String betterInput = "";
        int betterLength = 0;

        var iterator = SPECIFIEDLOCATIONMAP.keySet().iterator();

        if (iterator.hasNext()) {
            var input = iterator.next();
            var location = SPECIFIEDLOCATIONMAP.get(input);
            if (location > 0) {
                if (message.startsWith(input, location - 1) && betterLength < input.length()) {
                    betterInput = input;
                    betterLength = input.length();
                }
            } else {
                if (message.startsWith(input, message.length() + location) && betterLength < input.length()) {
                    betterInput = input;
                    betterLength = input.length();
                }
            }
        }
        if (betterInput.equals("") && betterLength == 0) {
            onInclusive(message, channel);
        } else {
            react(REACTIONMAP.get(betterInput), channel);
        }
    }

    private void onInclusive(String message, MessageChannel channel) {

        String betterInput = "";
        int betterLength = 0;

        for (String input : INCLUSIVELIST) {
            if (message.contains(input) && betterLength < input.length()) {
                betterInput = input;
                betterLength = input.length();
            }
        }
        react(REACTIONMAP.get(betterInput), channel);
    }

    private void react(String reaction, MessageChannel channel) {
        if (reaction != null) {
            ReactionBlockingQueue.request(() -> {channel.sendMessage(reaction).queue();}, channel);
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!(event.getAuthor().isBot())) {
            isReactionMatch(event.getMessage().getContentRaw(), event.getChannel());
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

        if (command.startsWith("Dialoger.add")) {
            var input = command.substring(command.indexOf("(") + 1, command.indexOf(","));
            var reaction = command.substring(command.indexOf(",") + 1, command.indexOf("#"));
            var match = command.substring(command.indexOf("#") + 1, command.indexOf(")"));

            if (match.equals("perfect")) {
                message = addPerfectReaction(input, reaction);
            } else if (match.equals("include")) {
                message = addInclusiveReaction(input, reaction);
            } else if ((Integer.parseInt(match) > 0) || Integer.parseInt(match) < 0) {
                var location = Integer.parseInt(match);
                message = addSpecifiedLocationReaction(input, reaction, location);
            } else {
                message = "`matchLocationは\"perfect\"か\"[0以外の整数]\"か\"include\"で指定して下さい`";
            }

        /*} else if (command.startsWith("Dialoger.change")) {
            var listenerName = command.substring(command.indexOf("(") + 1, command.indexOf(")"));

        } else if (command.startsWith("Dialoger.delete")) {
            var listenerName = command.substring(command.indexOf("(") + 1, command.indexOf(")"));

        */} else {
            message = "`メソッド名が不正です`";
        }
        ReactionBlockingQueue.request(() -> {
            channel.sendMessage("2:Dialoger | " + message).queue();
        } , channel);
    }
}
