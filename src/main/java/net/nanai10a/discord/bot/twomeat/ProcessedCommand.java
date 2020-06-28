package net.nanai10a.discord.bot.twomeat;

import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ProcessedCommand {
    private final Boolean patternMatched;
    private final String serviceName;
    private final Integer id;
    private final String methodName;
    private final String arg;
    private final MessageChannel messageChannel;

    public ProcessedCommand(@Nonnull Boolean patternMatched,
                            @Nonnull String serviceName,
                            @Nonnull Integer id,
                            @Nonnull String methodName,
                            @Nonnull String arg,
                            @Nonnull MessageChannel messageChannel
    ) {
        this.patternMatched = patternMatched;
        this.serviceName = serviceName;
        this.id = id;
        this.methodName = methodName;
        this.arg = arg;
        this.messageChannel = messageChannel;
    }


    @Nonnull
    public Boolean getPatternMatched() {
        return patternMatched;
    }

    @Nonnull
    public String getServiceName() {
        return serviceName;
    }

    @Nonnull
    public Integer getId() {
        return id;
    }

    @Nonnull
    public String getMethodName() {
        return methodName;
    }

    @Nonnull
    public String getArg() {
        return arg;
    }

    @Nonnull
    public MessageChannel getMessageChannel() {
        return messageChannel;
    }
}
