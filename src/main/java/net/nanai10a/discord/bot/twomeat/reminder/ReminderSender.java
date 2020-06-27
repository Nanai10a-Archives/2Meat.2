package net.nanai10a.discord.bot.twomeat.reminder;

import javax.annotation.Nullable;

interface ReminderSender {
    void Send(@Nullable Object item);
}
