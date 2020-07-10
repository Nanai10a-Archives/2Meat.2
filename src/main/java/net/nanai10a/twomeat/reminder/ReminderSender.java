package net.nanai10a.twomeat.reminder;

import javax.annotation.Nullable;

interface ReminderSender {
    void Send(@Nullable Object item);
}
