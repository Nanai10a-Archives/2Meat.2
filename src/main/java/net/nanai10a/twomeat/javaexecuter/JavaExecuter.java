package net.nanai10a.twomeat.javaexecuter;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.swing.*;
import java.io.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class JavaExecuter extends ListenerAdapter {
    private final ConcurrentLinkedQueue<JavaExecuterInfo> queue = new ConcurrentLinkedQueue<>();
    private final CopyOnWriteArrayList<User> waiting = new CopyOnWriteArrayList<>();

    public JavaExecuter() {
        new Timer((int) TimeUnit.MILLISECONDS.convert(3, TimeUnit.SECONDS), e -> queuePoller()).start();
    }

    private void run(JavaExecuterInfo info) {
        try {

            var javaFile = new File("C:\\Workspace\\JavaExecuter\\Main.java");
            var classFile = new File("C:\\Workspace\\JavaExecuter\\Main.class");
            javaFile.createNewFile();
            classFile.createNewFile();

            var programWriter = new FileWriter(javaFile);
            programWriter.write(info.program);
            programWriter.flush();

            var logFile = new File("C:\\Workspace\\JavaExecuter\\JavaExecuterLog.txt");

            var runner = new ProcessBuilder("C:\\Workspace\\JavaExecuter\\JavaExecuter.bat");
            runner.redirectOutput(logFile);
            runner.redirectError(logFile);

            var process = runner.start();
            process.waitFor();
            process.destroy();

            javaFile.delete();
            classFile.delete();
            var logRaw = new FileInputStream(logFile).readAllBytes();
            var log = new String(logRaw);

            var message = new MessageBuilder()
                    .append(info.event.getAuthor())
                    .append("2:JavaExecuter | `出力は以下の通りです。`")
                    .append(System.lineSeparator())
                    .append("```" + log + "```")
                    .append(System.lineSeparator())
                    .append("2:JavaExecuter | `以上です。実行は終了されました。`")
                    .build();

            info.event.getChannel().sendMessage(message)
                    .queue();
        } catch (IOException | InterruptedException e) {
            var message = new MessageBuilder()
                    .append(info.event.getAuthor())
                    .append(System.lineSeparator())
                    .append("2:JavaExecuter | `例外が発生しました。おい誠意見せろよ`")
                    .append(info.event.getJDA().getUserByTag("Nanai10a#0382"))
                    .append("`、カス野郎じゃん？`" + System.lineSeparator())
                    .append("```" + e.getMessage() + "```" + System.lineSeparator())
                    .append("2:JavaExecuter | `以上だ、生きててごめんなさいは？`")
                    .build();
            info.event.getMessage().getChannel().sendMessage(message).queue();
        } finally {
            waiting.remove(info.event.getAuthor());
        }
    }

    private void queue(String program, MessageReceivedEvent event) {
        queue.add(new JavaExecuterInfo(program, event));
        var message = new MessageBuilder()
                .append(event.getAuthor())
                .append(System.lineSeparator())
                .append("2:JavaExecuter | `キューに追加しました。結果が出るまでお待ち下さい。`")
                .build();
        event.getMessage().getChannel().sendMessage(message).queue();
    }

    private void queuePoller() {
            var q = queue.poll();
            if (q != null)
            run(q);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        var command = event.getMessage().getContentRaw();
        var channel = event.getChannel();

        if (waiting.contains(event.getAuthor())) {
                var program = command.substring(command.indexOf("```java") + 7 , command.indexOf("```end"));
                queue(program, event);

        } else if (!event.getAuthor().isBot() && command.equals("*2:JavaExecuter")) {
            waiting.add(event.getAuthor());
            var message = new MessageBuilder()
                    .append(event.getAuthor())
                    .append(System.lineSeparator())
                    .append("2:JavaExecuter | `プログラムを入力してください。`")
                    .build();
            event.getMessage().getChannel().sendMessage(message).queue();
        }

        if (event.getMessage().getContentRaw().equals("2:exit")) {
            event.getJDA().shutdown();
            System.exit(0);
        }
    }
}

class JavaExecuterInfo {
    final String program;
    final MessageReceivedEvent event;

    JavaExecuterInfo(String program, MessageReceivedEvent event) {
        this.program = program;
        this.event = event;
    }
}