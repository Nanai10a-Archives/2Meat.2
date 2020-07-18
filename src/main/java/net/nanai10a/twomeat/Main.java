package net.nanai10a.twomeat;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.nanai10a.twomeat.jda.Bot;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Main extends ListenerAdapter {

    public static void main(String[] args) throws LoginException, InterruptedException, IOException {
        new Bot();
    }
}
