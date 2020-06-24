package net.nanai10a.discord.bot.twomeat.reminder.old;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public class MessageSender {
	private MessageChannel channel;
	
	public MessageSender(MessageChannel channel) {
		this.channel = channel;
	}
	
	public void sendMessage(Message message) {
		channel.sendMessage(message).queue();
	}
}
