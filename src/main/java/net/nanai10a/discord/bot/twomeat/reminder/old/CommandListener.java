package net.nanai10a.discord.bot.twomeat.reminder.old;

import java.util.HashMap;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {
	private HashMap<String, CommandRunnable> commandRunnableMap = new HashMap<>();
	//private Subject subject;
	private Period period;
	public CommandListener(Subject subject, Period period) {
		//this.subject = subject;
		this.period = period;
		//Mapに匿名クラスでCommandRunnableを実装したクラスをコマンドのStringと一緒に突っ込む
		commandRunnableMap.put("chime!addSchedule", new CommandRunnable() {
			@Override
			public void run(String item) {
				period.addSchedule(item);
			}
		});
		
		commandRunnableMap.put("chime!changeSchedule", new CommandRunnable() {
			@Override
			public void run(String item) {
				period.changeSchedule(item);
			}
		});
		
		commandRunnableMap.put("chime!deleteSchedule", new CommandRunnable() {
			@Override
			public void run(String item) {
				period.deleteSchedule(item);
			}
		});
		
		commandRunnableMap.put("chime!addInformation", new CommandRunnable() {
			@Override
			public void run(String item) {
				subject.addInformation(item);
			}
		});
		
		commandRunnableMap.put("chime!changeInformation", new CommandRunnable() {
			@Override
			public void run(String item) {
				subject.changeInformation(item);
			}
		});
		
		commandRunnableMap.put("chime!deleteInformation", new CommandRunnable() {
			@Override
			public void run(String item) {
				subject.deleteInformation(item);
			}
		});
		
		commandRunnableMap.put("chime!accessInformation", new CommandRunnable() {
			@Override
			public void run(String item) {
				subject.accessInformation(item);
			}
		});
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		//コマンド文を抜き出します(コマンド文・付加情報文)
		String message = event.getMessage().getContentRaw();
			//コマンド文で何のコマンドか判定させます(MapにKeyを渡してCommandRunnableを返してもらう)
			String subMessage[] = message.split(" ");
			if (subMessage[0].startsWith("chime!") && subMessage.length >= 2 && !subMessage[1].isBlank()) {
				//受け取ったCommandRunnableを付加情報文を渡して動かす
				commandRunnableMap.get(subMessage[0]).run(subMessage[1]);
				//結構ぬるぽする、あとスペースで区切って付加情報を渡すと思うので.splitは良くないかも、
				//やっても[0]と[1~]で結合しなきゃいけない
				//なんなら.lengthは==で、付加情報は別の記号で区切るとかあるかなぁ
			}
	}
}

interface CommandRunnable {
	public void run(String item);
}