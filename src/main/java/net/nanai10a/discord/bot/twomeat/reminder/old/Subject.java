package net.nanai10a.discord.bot.twomeat.reminder.old;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;

public class Subject {
	//Dateはjava.util.Dateでいいのか？
	private static HashMap<ZonedDateTime, String> Names = new HashMap<>();
	private static HashMap<ZonedDateTime, String> Explanations = new HashMap<>();
	private static HashMap<ZonedDateTime, String> Preparations = new HashMap<>();
	private static HashMap<ZonedDateTime, String> Reviews = new HashMap<>();
	private static HashMap<ZonedDateTime, String> Tasks = new HashMap<>();
	private static HashMap<ZonedDateTime, String> ThingsToPrepares = new HashMap<>();
	private static HashMap<ZonedDateTime, String> Others = new HashMap<>();

	/*private String Name;
	private String Explanation;
	private String Preparation;
	private String Review;
	private String Task;
	private String ThingsToPrepare;
	private String Other;
	private ZonedDateTime ZonedTime;
	 */

	private static ChimeYaml NamesYaml;
	private static ChimeYaml ExplanationsYaml;
	private static ChimeYaml PreparationsYaml;
	private static ChimeYaml ReviewsYaml;
	private static ChimeYaml TasksYaml;
	private static ChimeYaml ThingsToPreparesYaml;
	private static ChimeYaml OthersYaml;

	static {
		try {
			NamesYaml = new ChimeYaml("Chime_Subject_Names");
			ExplanationsYaml = new ChimeYaml("Chime_Subject_Explanations");
			PreparationsYaml = new ChimeYaml("Chime_Subject_Preparations");
			ReviewsYaml = new ChimeYaml("Chime_Subject_Reviews");
			TasksYaml = new ChimeYaml("Chime_Subject_Tasks");
			ThingsToPreparesYaml = new ChimeYaml("Chime_Subject_ThingsToPrepares");
			OthersYaml = new ChimeYaml("Chime_Subject_Others");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Subject() {

	}

	/*private Subject(String name,
				   String explanation,
				   String preparation,
				   String review,
				   String task,
				   String thingsToPrepare,
				   String other,
				   String rawTime
	) throws IOException {
		this.Name = name;
		this.Explanation = explanation;
		this.Preparation = preparation;
		this.Review = review;
		this.Task = task;
		this.ThingsToPrepare = thingsToPrepare;
		this.Other = other;
		//yyyy/MM/dd-HH:mm
		//Messageの改行には\r\nを使ってみる。

	}

	 */

	/*
	 * public Message 情報開示(Date Key)
	 * ・こいつは内部の方、外部アクセスするのはaccessInformationです
	 * Keyを使って情報を取り出す
	 * 情報でMessageを作成
	 * Messageを返す
	 * ↓
	 */

	public Message returnInformation(Date key) {
		Message message = new MessageBuilder()
				.appendCodeBlock("", "")
				.build();
		Names.get(key);
		Explanations.get(key);
		Preparations.get(key);
		Reviews.get(key);
		Tasks.get(key);
		ThingsToPrepares.get(key);
		Others.get(key);

		return message;
	}
	public void addInformation(String item) {
		String items[] = item.split("", 8);

		//yyyy/MM/dd-HH:mm
		String[] rawTimes = items[7].split("-", 2);

		String[] yMd = rawTimes[0].split("/", 3);
		String[] Hm = rawTimes[1].split(":", 2);

		ZonedDateTime ZonedTime = ZonedDateTime.of(
				Integer.parseInt(yMd[0]),
				Integer.parseInt(yMd[1]),
				Integer.parseInt(yMd[2]),
				Integer.parseInt(Hm[0]),
				Integer.parseInt(Hm[1]),
				0,
				0,
				ZoneId.of("Asia/Tokyo")
		);

		Names.put(ZonedTime, items[0]);
		Explanations.put(ZonedTime, items[1]);
		Preparations.put(ZonedTime, items[2]);
		Reviews.put(ZonedTime, items[3]);
		Tasks.put(ZonedTime, items[4]);
		ThingsToPrepares.put(ZonedTime, items[5]);
		Others.put(ZonedTime, items[6]);

	}
	public void changeInformation(String item) {
		
	}
	public void deleteInformation(String item) {
		
	}
	public void accessInformation(String item) {
		
	}

	public static void DumpInformation() throws IOException {
		NamesYaml.DampToYaml(Names);
		ExplanationsYaml.DampToYaml(Explanations);
		PreparationsYaml.DampToYaml(Preparations);
		ReviewsYaml.DampToYaml(Reviews);
		TasksYaml.DampToYaml(Tasks);
		ThingsToPreparesYaml.DampToYaml(ThingsToPrepares);
		OthersYaml.DampToYaml(Others);
	}

	public static void LoadInformation() {
	}
}
