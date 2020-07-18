package net.nanai10a.twomeat.dialoger;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.nanai10a.twomeat.CommandProcessingService;
import net.nanai10a.twomeat.jda.ReactionBlockingQueue;
import net.nanai10a.twomeat.yaml.InclusiveList;
import net.nanai10a.twomeat.yaml.PerfectList;
import net.nanai10a.twomeat.yaml.ReactionMap;
import net.nanai10a.twomeat.yaml.SpecifiedLocationMap;

import javax.swing.text.Position;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Dialoger extends ListenerAdapter {

    private final Map<String, String> REACTIONMAP;

    private final List<String> PERFECTLIST;
    private final Map<String, Integer> SPECIFIEDLOCATIONMAP;
    private final List<String> INCLUSIVELIST;

    private enum Position {
        perfect,
        anyPosition,
        startWith,
        endWith,
        include
    }

    public Dialoger() throws IOException {

        var _REACTIONMAP = ReactionMap.load();
        if (_REACTIONMAP != null) {
            REACTIONMAP = _REACTIONMAP;
        } else {
            REACTIONMAP = new ConcurrentHashMap<String, String>();
        }

        var _PERFECTLIST = PerfectList.load();
        if (_PERFECTLIST != null) {
            PERFECTLIST = _PERFECTLIST;
        } else {
            PERFECTLIST = new CopyOnWriteArrayList<String>();
        }

        var _SPECIFIEDLOCATIONMAP = SpecifiedLocationMap.load();
        if (_SPECIFIEDLOCATIONMAP != null) {
            SPECIFIEDLOCATIONMAP = _SPECIFIEDLOCATIONMAP;
        } else {
            SPECIFIEDLOCATIONMAP = new ConcurrentHashMap<String, Integer>();
        }

        var _INCLUSIVELIST = InclusiveList.load();
        if (_INCLUSIVELIST != null) {
            INCLUSIVELIST = _INCLUSIVELIST;
        } else {
            INCLUSIVELIST = new CopyOnWriteArrayList<String>();
        }
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
            message = "`\"" + input + "\" (完全一致) →" + reaction + " 、登録しました`";
        }

        return message;
    }

    private String addSpecifiedLocationReaction(String input, String reaction, int location, Position position) {
        String message;

        if (REACTIONMAP.containsKey(input)) {
            message = "`もう既にその入力は登録されています`";
        } else {
            REACTIONMAP.put(input, reaction);
            SPECIFIEDLOCATIONMAP.put(input, location);
            if (position.equals(Position.startWith)) {
                message = "`\"" + input + "\" (先頭) → " + reaction + " 、登録しました`";
            } else if (position.equals(Position.endWith)) {
                message = "`\"" + input + "\" (末尾) → " + reaction + " 、登録しました`";
            } else if (location > 0) {
                message = "`\"" + input + "\" (先頭から" + location + "文字目) → " + reaction + " 、登録しました`";
            } else {
                message = "`\"" + input + "\" (末尾から" + (Math.abs(location)) + "文字目) → " + reaction + " 、登録しました`";
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
            message = "`\"" + input + "\" (内包) →" + reaction + " 、登録しました`";
        }

        return message;
    }

    private String changeReaction(String input, String reaction, int location, Position position) {
        String message;

        if (REACTIONMAP.containsKey(input)) {

            final var _reaction = REACTIONMAP.get(input);
            final Position _position;
            int _location = 0;
            if (PERFECTLIST.contains(input)) {
                _position = Position.perfect;
                PERFECTLIST.remove(input);
            } else if (SPECIFIEDLOCATIONMAP.containsKey(input)) {

                _location = SPECIFIEDLOCATIONMAP.get(input);
                if (_location == 1) _position = Position.startWith;
                else if (_location == (input.length() * -1)) _position = Position.endWith;
                else _position = Position.anyPosition;

                SPECIFIEDLOCATIONMAP.remove(input);
            } else {
                _position = Position.include;
                INCLUSIVELIST.remove(input);
            }

            if (position.equals(Position.perfect)) message = addPerfectReaction(input, reaction);
            else if (position.equals(Position.anyPosition)) message = addSpecifiedLocationReaction(input, reaction, location, position);
            else if (position.equals(Position.startWith)) message = addSpecifiedLocationReaction(input, reaction, location, position);
            else if (position.equals(Position.endWith)) message = addSpecifiedLocationReaction(input, reaction, location, position);
            else message = addInclusiveReaction(input, reaction);

            String changeMessage;
            if (_position.equals(Position.perfect)) changeMessage = "`\"" + input + "\" (完全一致) →" + _reaction + " を削除し、`";
            else if (_position.equals(Position.startWith)) changeMessage = "`\"" + input + "\" (先頭) → " + _reaction + " を削除し、`";
            else if (_position.equals(Position.endWith)) changeMessage = "`\"" + input + "\" (末尾) → " + _reaction + " を削除し、`";
            else if (location > 0) changeMessage = "`\"" + input + "\" (先頭から" + _location + "文字目) → " + _reaction + " を削除し、`";
            else if (location < 0) changeMessage = "`\"" + input + "\" (末尾から" + (Math.abs(_location)) + "文字目) → " + _reaction + " を削除し、`";
            else changeMessage = "`\"" + input + "\" (内包) →" + _reaction + " を削除し、`";
            changeMessage += System.lineSeparator();

            message = changeMessage + message;

        } else {
            message = "`その入力は登録されていません`";
        }

        return message;
    }

    private String deleteReaction(String input) {
        String message;

        if (REACTIONMAP.containsKey(input)) {

            int location;
            location = SPECIFIEDLOCATIONMAP.getOrDefault(input, 0);

            Position position;
            if (PERFECTLIST.contains(input)) {
                position = Position.perfect;
            } else if (SPECIFIEDLOCATIONMAP.containsKey(input)) {
                if (location > 0) {
                    position = Position.startWith;
                } else if (location < 0) {
                    position = Position.endWith;
                } else {
                    position = Position.anyPosition;
                }
                position = Position.anyPosition;
            } else {
                position = Position.include;
            }

            if (position.equals(Position.perfect)) {
                var reaction = REACTIONMAP.get(input);
                message = "`\"" + input + "\" (完全一致) →" + reaction + " を削除しました`";
                REACTIONMAP.remove(input);
                PERFECTLIST.remove(input);
            } else if (position.equals(Position.startWith)) {
                var reaction = REACTIONMAP.get(input);
                message = "`\"" + input + "\" (先頭) → " + reaction + " を削除しました`";
                REACTIONMAP.remove(input);
                SPECIFIEDLOCATIONMAP.remove(input);
            } else if (position.equals(Position.endWith)) {
                var reaction = REACTIONMAP.get(input);
                message = "`\"" + input + "\" (末尾) → " + reaction + " を削除しました`";
                REACTIONMAP.remove(input);
                SPECIFIEDLOCATIONMAP.remove(input);
            } else if (location > 0) {
                var reaction = REACTIONMAP.get(input);
                message = "`\"" + input + "\" (先頭から" + location + "文字目) → " + reaction + " を削除しました`";
                REACTIONMAP.remove(input);
                SPECIFIEDLOCATIONMAP.remove(input);
            } else if (location < 0) {
                var reaction = REACTIONMAP.get(input);
                message = "`\"" + input + "\" (末尾から" + (Math.abs(location)) + "文字目) → " + reaction + " を削除しました`";
                REACTIONMAP.remove(input);
                SPECIFIEDLOCATIONMAP.remove(input);
            } else {
                var reaction = REACTIONMAP.get(input);
                message = "`\"" + input + "\" (内包) →" + reaction + " を削除しました`";
                REACTIONMAP.remove(input);
                INCLUSIVELIST.remove(input);
            }

        } else {
            message = "`\"" + input + "\" は登録されていません`";
        }

        return message;
    }

    private String containReaction(String input) {
        String message;

        if (REACTIONMAP.containsKey(input)) {

            if (PERFECTLIST.contains(input)) {
                var reaction = REACTIONMAP.get(input);
                message = "`\"" + input + "\" (完全一致) →" + reaction + " で登録されています`";
            } else if (SPECIFIEDLOCATIONMAP.containsKey(input)) {

                var reaction = REACTIONMAP.get(input);
                var location =  SPECIFIEDLOCATIONMAP.get(input);

                if (location == 1) {
                    message = "`\"" + input + "\" (先頭) → " + reaction + " で登録されています`";
                } else if (location == (input.length() * -1)) {
                    message = "`\"" + input + "\" (末尾) → " + reaction + " で登録されています`";
                } else if (location > 0) {
                    message = "`\"" + input + "\" (先頭から" + location + "文字目) → " + reaction + " で登録されています`";
                } else {
                    message = "`\"" + input + "\" (末尾から" + (Math.abs(location)) + "文字目) → " + reaction + " で登録されています`";
                }

            } else {
                var reaction = REACTIONMAP.get(input);
                message = "`\"" + input + "\" (内包) →" + reaction + " で登録されています`";
            }

        } else {
            message = "`\"" + input + "\" は登録されていません`";
        }

        return message;
    }

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
            channel.sendMessage(reaction).queue();
            //ReactionBlockingQueue.request(() -> {channel.sendMessage(reaction).queue();}, channel);
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

        if (command.startsWith("Dialoger")) {
            try {

                if (command.startsWith("Dialoger.add")) {

                    var input = command.substring(command.indexOf("(") + 1, command.indexOf("#"));
                    var match = command.substring(command.indexOf("#") + 1, command.indexOf(","));
                    var reaction = command.substring(command.indexOf(",") + 1, command.indexOf(")"));

                    if (match.equals("perfect")) {
                        message = addPerfectReaction(input, reaction);
                    } else if (match.equals("include")) {
                        message = addInclusiveReaction(input, reaction);
                    } else if (match.equals("startWith")) {
                        var location = 1;
                        message = addSpecifiedLocationReaction(input, reaction, location, Position.startWith);
                    } else if (match.equals("endWith")) {
                        var location = (input.length() * -1);
                        message = addSpecifiedLocationReaction(input, reaction, location, Position.endWith);
                    } else {
                        if (canParseAndNoZero(match)) {
                            var location = Integer.parseInt(match);
                            message = addSpecifiedLocationReaction(input, reaction, location, Position.anyPosition);
                        } else {
                            message = "`matchLocationは\"perfect\"か\"[0以外の整数]\",\"startWith\",\"endWith\"か\"include\"で指定して下さい`";
                        }
                    }

                } else if (command.startsWith("Dialoger.change")) {

                    var input = command.substring(command.indexOf("(") + 1, command.indexOf("#"));
                    var match = command.substring(command.indexOf("#") + 1, command.indexOf(","));
                    var reaction = command.substring(command.indexOf(",") + 1, command.indexOf(")"));

                    if (match.equals("perfect")) {
                        message = changeReaction(input, reaction, 0, Position.perfect);
                    } else if (match.equals("include")) {
                        message = changeReaction(input, reaction, 0, Position.include);
                    } else if (match.equals("startWith")) {
                        var location = 1;
                        message = changeReaction(input, reaction, location, Position.startWith);
                    } else if (match.equals("endWith")) {
                        var location = (input.length() * -1);
                        message = changeReaction(input, reaction, location, Position.endWith);
                    } else {
                        if (canParseAndNoZero(match)) {
                            var location = Integer.parseInt(match);
                            message = changeReaction(input, reaction,location, Position.anyPosition);
                        } else {
                            message = "`matchLocationは\"perfect\"か\"[0以外の整数]\",\"startWith\",\"endWith\"か\"include\"で指定して下さい`";
                        }
                    }

                } else if (command.startsWith("Dialoger.delete")) {
                    var input = command.substring(command.indexOf("(") + 1, command.indexOf(")"));
                    message = deleteReaction(input);
                } else if (command.startsWith("Dialoger.contain")) {
                    var input = command.substring(command.indexOf("(") + 1, command.indexOf(")"));
                    message = containReaction(input);
                } else if (command.startsWith("Dialoger.help")) {
                    message = "```" + System.lineSeparator()
                            + "*2:Dialoger ... methodList" + System.lineSeparator()
                            + "- - - - - - - - - - - - - - -" + System.lineSeparator()
                            + System.lineSeparator()
                            + ".add([input]#[match],[reaction])" + System.lineSeparator()
                            + "- inputをmatchで照合して合致すればreactionを返します" + System.lineSeparator()
                            + System.lineSeparator()
                            + ".change([input]#[match],[reaction])" + System.lineSeparator()
                            + "- 既存のinputを上書きします" + System.lineSeparator()
                            + System.lineSeparator()
                            + ".delete([input])" + System.lineSeparator()
                            + "- 既存のinputを消去します" + System.lineSeparator()
                            + System.lineSeparator()
                            + ".contain([input])" + System.lineSeparator()
                            + "- inputが登録されているか、また登録されていればreactionとmatchを返します" + System.lineSeparator()
                            + System.lineSeparator()
                            + ".help([※この引数は無視されます])" + System.lineSeparator()
                            + "- this one..." + System.lineSeparator()
                            + System.lineSeparator()
                            + "- - - - - - - - - - - - - - -" + System.lineSeparator()
                            + "```";
                } else {
                    message = "`メソッド名が不正です`";
                }
                channel.sendMessage(message).queue();
                ReactionMap.dump(REACTIONMAP);
                PerfectList.dump(PERFECTLIST);
                SpecifiedLocationMap.dump(SPECIFIEDLOCATIONMAP);
                InclusiveList.dump(INCLUSIVELIST);
                //ReactionBlockingQueue.request(() -> {
                    //channel.sendMessage("2:Dialoger | " + message).queue();
                //} , channel);
            } catch (StringIndexOutOfBoundsException | IOException e) {
                var exceptionMessage = "`例外のお知らせです。責任者来いやｵﾗｯ!`" + "@Nanai10a#0382" + System.lineSeparator()
                        + "```" + System.lineSeparator()
                        + e.getMessage() + System.lineSeparator()
                        + "```";
                channel.sendMessage(exceptionMessage).queue();
                //ReactionBlockingQueue.request(() -> {
                    //channel.sendMessage("2:Dialoger | " + exceptionMessage).queue();
                //} , channel);
            }
        }
    }

    private boolean canParseAndNoZero(String string) {
        boolean can;

        try {
            var parse = Integer.parseInt(string);
            if (parse != 0) can = true;
            else can = false;
        } catch (NumberFormatException e) {
            can = false;
        }

        return can;
    }
}
