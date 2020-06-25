package net.nanai10a.discord.bot.twomeat.reminder;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import static net.nanai10a.discord.bot.twomeat.reminder.ActionCommand.Discord_Send_Text_Message;

public class DiscordAction extends Object implements Action {
    @Override
    public void Output(Object item, ActionCommand command, Object... otherItems) {

        try {
            switch (command) {
                case Discord_Send_Text_Message:
                    //otherItems[0]にMessageChannelを入れる必要がある

                    if (otherItems[0] != null && item instanceof Message && otherItems[0] instanceof MessageChannel) {
                        MessageChannel channel = (MessageChannel)otherItems[0];
                        channel.sendMessage((Message)item).queue();
                    } else {
                        //インスタンスが違いますよ通知
                    }
                    break;
            }
        } catch (NullPointerException nulle) {
            nulle.printStackTrace();
            //ぬるぽした通知
        }
    }
}
