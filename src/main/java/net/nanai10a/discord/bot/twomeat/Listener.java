package net.nanai10a.discord.bot.twomeat;

import javax.annotation.Nonnull;

public interface Listener {
    void onEvent(@Nonnull Object event);
}
