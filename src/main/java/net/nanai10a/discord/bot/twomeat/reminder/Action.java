package net.nanai10a.discord.bot.twomeat.reminder;

interface Action {
    public void Output(Object item, ActionCommand command, Object... otherItems);
}

enum  ActionCommand {
    Discord_Send_Text_Message
}
