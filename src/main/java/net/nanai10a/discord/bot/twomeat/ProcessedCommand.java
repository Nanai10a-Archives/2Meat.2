package net.nanai10a.discord.bot.twomeat;

import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ProcessedCommand {
    private final String methodName;
    private final String arg;
    private final MessageChannel messageChannel;

    public ProcessedCommand(@Nonnull String methodName, @Nonnull String arg, @Nonnull MessageChannel messageChannel) {
        this.methodName = methodName;
        this.arg = arg;
        this.messageChannel = messageChannel;
    }

    @Nonnull
    public Boolean judgeMethodName(String methodName) {
        if (methodName != null) {
            return this.methodName.equals(methodName);
        }
        return false;
    }

    @Nonnull
    public String getArg() {
        return arg;
    }
}
