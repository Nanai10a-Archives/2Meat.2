package net.nanai10a.discord.bot.twomeat.reminder.old;

import java.io.IOException;

abstract class Io {
    public abstract <T> T InputMap();
    //public abstract void Input(Object data);
    public abstract void Output(Object data) throws IOException;
}
