package net.nanai10a.discord.bot.twomeat;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

public class CommandProcessingService {
    /*
    ここはServiceなクラス。コマンドの書式は統一されるべきなのでここで処理する。
     */

    @Nonnull
    public static ProcessedCommand process(@Nonnull Object event, @Nonnull String serviceName, @Nonnull Integer id) {
        if (event instanceof MessageReceivedEvent) {
            String command = ((MessageReceivedEvent) event).getMessage().getContentRaw();
            Boolean patternMatched = Pattern.matches(Pattern.quote("*") + "2:.+<\\d+>" + Pattern.quote(".") + ".+\\(.*\\)", command);
            Boolean serviceNameMatched = command.substring(command.indexOf(":") + 1, command.indexOf("<")).equals(serviceName);
            Boolean idMatched = Integer.parseInt(command.substring(command.indexOf("<") + 1, command.indexOf(">"))) == id;
            if (patternMatched && serviceNameMatched && idMatched){
                return new ProcessedCommand(
                        command.substring(command.indexOf(".") + 1, command.indexOf("(")),
                        command.substring(command.indexOf("(") + 1, command.indexOf(")")),
                        ((MessageReceivedEvent) event).getChannel()
                );
            } //serviceNameと照合して存在するサービス名かどうか→idに照会して存在するidなのかどうか→→→NotFound
            //どうしようね、判断基準が分からん
        }
            //どこからならIllegalCommand判定するのか…?
        return new ProcessedCommand(null, null, null);
        //↑どうにかならんのかな
    }
}
