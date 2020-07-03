package net.nanai10a.twomeat;

import javax.annotation.Nonnull;

public interface CommandListener {
    void onCommandEvent(@Nonnull ProcessedCommand item);
}
