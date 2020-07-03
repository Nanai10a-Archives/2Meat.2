package net.nanai10a.twomeat;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

public class DiscordCommandProcessingService {
    /*
    ここはServiceなクラス。コマンドの書式は統一されるべきなのでここで処理する。
    ※Discord専用になってしまった、抽象化求む
     */

    @Nonnull
    public static ProcessedCommand process(@Nonnull MessageReceivedEvent event) {
        String command = event.getMessage().getContentRaw();
        if (Pattern.matches(Pattern.quote("*") + "2:.+<\\d+>\\..+\\(.*\\)", command)) {
            return new ProcessedCommand(true,
                    command.substring(command.indexOf(":") + 1, command.indexOf("<")),
                    Integer.parseInt(command.substring(command.indexOf("<") + 1, command.indexOf(">"))),
                    command.substring(command.indexOf(".") + 1, command.indexOf("(")),
                    command.substring(command.indexOf("(") + 1, command.indexOf(")")),
                    event.getChannel()
            );
        } else {
            return new ProcessedCommand(false,"",-1,"","",event.getChannel());
        }
    }
}
