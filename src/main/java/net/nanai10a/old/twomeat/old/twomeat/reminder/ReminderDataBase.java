package net.nanai10a.old.twomeat.old.twomeat.reminder;

import javax.annotation.Nullable;

interface ReminderDataBase {
    void dump(@Nullable Object data);
    @Nullable
    Object load();
}
