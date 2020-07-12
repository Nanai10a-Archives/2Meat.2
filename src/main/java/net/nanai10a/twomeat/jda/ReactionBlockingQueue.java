package net.nanai10a.twomeat.jda;

import net.dv8tion.jda.api.entities.MessageChannel;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ReactionBlockingQueue {
    private static LinkedBlockingQueue<QueueRunnable> QUEUE = new LinkedBlockingQueue<>();
    private static ConcurrentHashMap<Instant, QueueRunnable> QUEUETIME = new ConcurrentHashMap<>();

    static {
        new Thread(() -> new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ReactionBlockingQueue.remove();
            }
        }, 0, TimeUnit.SECONDS.toMillis(5))).start();

        new Thread(() -> new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ReactionBlockingQueue.run();
            }
        }, 0, 10)).start();
    }

    public static void request(Runnable request, MessageChannel channel) {
        var queueRunnable = new QueueRunnable(request, channel);
        QUEUE.offer(queueRunnable);
        QUEUETIME.put(Instant.now(), queueRunnable);
    }

    private static void run() {
        var runnable = QUEUE.poll();
        if (runnable != null) {
            new Thread(runnable).start();
        }
    }

    private static void remove() {
            var removedQueueChannelList = new HashMap<String, Integer>();

            for (Instant queueTime : QUEUETIME.keySet()) {
                MessageChannel firstRemovedChannel = null;
                var removedChannelNum = 0;
                if (queueTime.plusSeconds(10).isBefore(Instant.now())) {

                    if (firstRemovedChannel == null) {
                        firstRemovedChannel = QUEUETIME.get(queueTime).getChannel();
                    }
                    QUEUE.remove(QUEUETIME.get(queueTime));
                    removedChannelNum++;

                    var channelId = QUEUETIME.get(queueTime).getChannel().getId();
                    if (!(removedQueueChannelList.containsKey(channelId))) {
                        removedQueueChannelList.put(channelId, 1);
                    } else {
                        removedQueueChannelList.put(channelId, removedQueueChannelList.get(channelId) + 1);
                    }
                    QUEUETIME.remove(queueTime);
                }

                if (firstRemovedChannel != null) {

                    var removedChannelList = new ArrayList<String>();
                    for (var channel : removedQueueChannelList.keySet()) {
                        removedChannelList.add("<#" + channel + "> - `" + removedQueueChannelList.get(channel) + "requests`");
                    }
                    String removedChannelListString = "";
                    for (var listLine : removedChannelList) {
                        removedChannelListString += (listLine + System.lineSeparator());
                    }
                    //log出力に切り替えで。
                    /*
                    firstRemovedChannel.sendMessage("2:Bot | `" + removedChannelNum + "個のリクエストをブロックしました。内訳は以下の通りです。`" +
                            System.lineSeparator() +
                            removedChannelListString).queue();
                     */
                }
            }
    }
}

class QueueRunnable implements Runnable {
    private final Runnable runnable;
    private final MessageChannel channel;

    public QueueRunnable(Runnable runnable, MessageChannel channel) {
        this.runnable = runnable;
        this.channel = channel;
    }

    @Override
    public void run() {
        runnable.run();
    }

    public MessageChannel getChannel() {
        return channel;
    }
}