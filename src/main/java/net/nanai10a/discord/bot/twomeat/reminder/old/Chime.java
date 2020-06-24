package net.nanai10a.discord.bot.twomeat.reminder.old;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public class Chime {
	private Message message;
	private MessageChannel channel;
	
	public Chime(Message message, MessageChannel channel) {
		this.message = message;
		this.channel = channel;
	}
	public void chimeNotification() {
		this.channel.sendMessage(this.message);
	}
}
