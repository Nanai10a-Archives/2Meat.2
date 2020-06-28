package net.nanai10a.discord.bot.twomeat;

import javax.annotation.Nonnull;

public interface CommandListener {
    void onCommandEvent(@Nonnull ProcessedCommand item);
}
