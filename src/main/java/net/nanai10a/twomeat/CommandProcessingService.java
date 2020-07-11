package net.nanai10a.twomeat;

import java.util.regex.Pattern;

public class CommandProcessingService {

    public static String process(String rawCommand) {
        var command = rawCommand.substring(3);
        return command;
    }

    public static boolean match(String rawCommand) {
        final boolean result;

        if (Pattern.matches(Pattern.quote("*") + "2:.+\\..+(\\.*)", rawCommand)) {
            result = true;
        } else if (rawCommand.startsWith("*2:") && !Pattern.matches(Pattern.quote("*") + "2:.+\\..+(\\.*)", rawCommand)) {
            result = false;
        } else {
            result = false;
        }
        return result;
    }

    public static CommandMatch matchx(String rawCommand) {
        final CommandMatch result;

        if (Pattern.matches(Pattern.quote("*") + "2:.+\\..+(\\.*)", rawCommand)) {
            result = CommandMatch.MATCH;
        } else if (rawCommand.startsWith("*2:") && !Pattern.matches(Pattern.quote("*") + "2:.+\\..+(\\.*)", rawCommand)) {
            result = CommandMatch.NOMATCH;
        } else {
            result = CommandMatch.IGNORE;
        }
        return result;
    }
}