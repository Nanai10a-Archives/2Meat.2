package net.nanai10a.discord.bot.twomeat.reminder.old;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Main {
	
	public static void main(String[] args) throws LoginException, InterruptedException {
		JDA jda = JDABuilder.createDefault("NzEwNTA3NzY2MzM0MDk1Mzcw.XtO6Yg.RtIdUWBWqtPJ9x-Ak4ophBQpbbc").build();
		jda.awaitReady();
		//Period period = new Period(jda);
	}
}
