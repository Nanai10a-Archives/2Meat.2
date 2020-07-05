package net.nanai10a.twomeat;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.nanai10a.twomeat.javaexecuter.JavaExecuter;
import net.nanai10a.twomeat.reminder.ReminderController;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws LoginException, IOException, InterruptedException {
        JDA jda = JDABuilder.createDefault(System.getenv("DISCORD_TOKEN_2MEAT_MAIN"))
                //.addEventListeners(new Measure())
                //.addEventListeners(new DiscordListener())
                //.addEventListeners(new DiscordCommandListener())
                .build();
        jda.awaitReady();

        //テスト用
        //jda.getTextChannelById("716658620887990312").sendMessage("***I'm Statistics gatheringer, 2Meat!***").queue();
        //

        //jda.addEventListener(new Remindering(jda));
        jda.addEventListener(new JavaExecuter());



        //実装が終わったらActive化させる

        //new ReminderController(jda);

        //new DiscordListener(jda);
        //new DiscordCommandListener();

        //IdController.active(jda);



    }
}
