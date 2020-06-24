package net.nanai10a.discord.bot.twomeat.reminder.old;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import net.dv8tion.jda.api.JDA;

public class Period {
	private JDA jda;
	private Subject subject;
	private Timer periodTimer;
	//java.util.Dateでいいんすか？
	private HashMap<Date, PeriodTimerTask> periodTimerTaskRepository = new HashMap<>();
	
	
	
	public Period(JDA jda) throws IOException {
		this.jda = jda;
		//※configファイルの読み出し
		//※情報ファイルの読み出し
		this.subject = new Subject();
		//※subjectインスタンスからマップを取得
		//※マップに情報を代入
		
		this.periodTimer = new Timer();
		//※情報を処理してスケジュールする
		
		jda.addEventListener(new CommandListener(subject, this));
	}
	
	public void addSchedule(String item) {
		
	}
	public void changeSchedule(String item) {
		
	}
	public void deleteSchedule(String item) {
		
	}
}

class PeriodTimerTask extends TimerTask {
	private Subject subject;
	private JDA jda;
	
	public PeriodTimerTask(Subject subject, JDA jda) {
		this.subject = subject;
		this.jda = jda;
	}

	@Override
	public void run() {
	}
}