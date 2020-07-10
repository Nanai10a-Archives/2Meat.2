package net.nanai10a.twomeat;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Measure extends ListenerAdapter {
    //private Yaml yaml = new Yaml();
    private FileWriter writer = new FileWriter(new File("ApproversRemarkTimes.yaml"));
    private Timer timer = new Timer(true);
    private AtomicInteger remarkTimes = new AtomicInteger();
    private int dumpTimes = 0;

    public Measure() throws IOException {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String record = "Recorded at " + ZonedDateTime.now(ZoneId.of("Asia/Tokyo")).toString() + " : " + remarkTimes.get() + "times";
                remarkTimes.set(0);
                //yaml.dump(record, writer);
                dumpTimes++;
        System.out.println(dumpTimes + ", Dump Done.");
            }
        }
        ,0, TimeUnit.MINUTES.toMillis(5));
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        remarkTimes.getAndIncrement();
    }
}
