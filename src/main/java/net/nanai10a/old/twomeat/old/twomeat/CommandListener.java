package net.nanai10a.old.twomeat.old.twomeat;

import javax.annotation.Nonnull;

public interface CommandListener {
    void onCommandEvent(@Nonnull ProcessedCommand item);
}
