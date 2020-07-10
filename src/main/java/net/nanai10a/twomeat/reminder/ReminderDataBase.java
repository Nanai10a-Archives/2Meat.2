package net.nanai10a.twomeat.reminder;

import javax.annotation.Nullable;

interface ReminderDataBase {
    void dump(@Nullable Object data);
    @Nullable
    Object load();
}
