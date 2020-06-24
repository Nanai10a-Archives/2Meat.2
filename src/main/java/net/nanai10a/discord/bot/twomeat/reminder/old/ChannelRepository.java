package net.nanai10a.discord.bot.twomeat.reminder.old;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.HashMap;

public class ChannelRepository {
	private static JDA jda;
	private static HashMap<String, MessageChannel> channelRepository = new HashMap<>();
	
	public ChannelRepository(JDA jda) {
		this.jda = jda;
	}
	
	public static void addChannel(String registrationName, String channelId) {
		channelRepository.put(registrationName, jda.getTextChannelById(channelId));
	}
	public static void addChannel(String registrationName, MessageChannel channel) {
		channelRepository.put(registrationName, channel);
	}
	public static MessageChannel returnChannel(String registrationName) {
		//
		channelRepository.put("Test", jda.getTextChannelById("716658620887990312"));
		//
		return channelRepository.get(registrationName);
	}
}
