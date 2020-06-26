package net.nanai10a.discord.bot.twomeat.reminder;

import javax.annotation.Nullable;

interface ReminderDataBase {
    void dump(@Nullable Object data);
    @Nullable
    Object load();
}
