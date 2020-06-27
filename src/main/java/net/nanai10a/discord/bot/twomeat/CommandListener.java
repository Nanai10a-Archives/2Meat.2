package net.nanai10a.discord.bot.twomeat;

import javax.annotation.Nonnull;

public interface CommandListener extends Listener {
    void onEvent(@Nonnull Object event);
    //ここでなんか動作を定義できないか
}
