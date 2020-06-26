package net.nanai10a.discord.bot.twomeat.reminder;

import javax.annotation.Nonnull;

interface Listener {
    void onEvent(@Nonnull Object event);
}
