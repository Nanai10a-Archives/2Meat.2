package net.nanai10a.discord.bot.twomeat;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.nanai10a.discord.bot.twomeat.approvers.standardtime.Measure;
import net.nanai10a.discord.bot.twomeat.reminder.Reminder;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws LoginException, IOException, InterruptedException {
        JDA jda = JDABuilder.createDefault("")
                .addEventListeners(new Measure())
                .build();
        jda.awaitReady();

        //テスト用
        jda.getTextChannelById("716658620887990312").sendMessage("***I'm Statistics gatheringer, 2Meat!***").queue();
        //

        /*実装が終わったらActive
        Reminder.ActiveReminder(jda);
         */
    }
}
