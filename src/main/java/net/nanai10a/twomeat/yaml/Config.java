package net.nanai10a.twomeat.yaml;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Config {
    private static Config CONFIG;

    static {
        try {
            CONFIG = new Config();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void update(ConfigItem item, boolean isUsable) {
        Map<String, Boolean> changedConfig = null;
        try {
            changedConfig = CONFIG.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (item) {
            //case Reminder:
                //changedConfig.put(CONFIG.REMINDER, isUsable);
                //break;
            case Dialoger:
                changedConfig.put(CONFIG.DIALOGER, isUsable);
                break;
            //case ApproversStandardTime:
                //changedConfig.put(CONFIG.APPROVERSSTANDARDTIME, isUsable);
                //break;
        }

        try {
            CONFIG.dump(changedConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean contain(ConfigItem item, boolean isUsable) {
        Map<String, Boolean> config = null;
        try {
            config = CONFIG.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean isUsableByConfig = false;

        switch (item) {
            //case Reminder:
                //isUsableByConfig = config.get(CONFIG.REMINDER).equals(isUsable);
                //break;
            case Dialoger:
                isUsableByConfig = config.get(CONFIG.DIALOGER).equals(isUsable);
                break;
            //case ApproversStandardTime:
                //isUsableByConfig = config.get(CONFIG.APPROVERSSTANDARDTIME).equals(isUsable);
                //break;
        }

        boolean isContain;

        if (isUsableByConfig == isUsable) {
            isContain = true;
        } else {
            isContain = false;
        }

        return isContain;
    }

    public static boolean getIsUsable(ConfigItem item) {
        Map<String, Boolean> config = null;
        try {
            config = CONFIG.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean isUsableByConfig = true;

        switch (item) {
            //case Reminder:
                //isUsableByConfig = config.get(CONFIG.REMINDER);
                //break;
            case Dialoger:
                isUsableByConfig = config.get(CONFIG.DIALOGER);
                break;
            //case ApproversStandardTime:
                //isUsableByConfig = config.get(CONFIG.APPROVERSSTANDARDTIME);
                //break;
            }

        return isUsableByConfig;
    }



    private final Yaml YAML = new Yaml();
    private final File FILE = Paths.get("Config.yaml").toFile();

    public final String REMINDER = "ReminderIsUsable";
    public final String DIALOGER = "DialogerIsUsable";
    public final String APPROVERSSTANDARDTIME = "ApproversStandardTimeIsUsable";



    private Config() throws IOException {

        var configBroken = false;

        var reminderConfigIsBroken = false;
        var dialogerConfigIsBroken = false;
        var approversStandardTimeConfigIsBroken = false;

        var reminderConfig = false;
        var dialogerConfig = false;
        var approversStandardTimeConfig = false;

        Map<String, Boolean> config;

        try {
            config = load();
        } catch (NullPointerException | YAMLException e) {
            initialize();
        } catch (IOException e)  {
            e.printStackTrace();
        } catch (Exception e) {
            initialize();
        } finally {
            config = load();
        }

        try {
            reminderConfig = config.get(REMINDER);
        } catch (NullPointerException e) {
            configBroken = true;
            reminderConfigIsBroken = true;
            reminderConfig = true;
        }
        try {
            dialogerConfig = config.get(DIALOGER);
        } catch (NullPointerException e) {
            configBroken = true;
            dialogerConfigIsBroken = true;
            dialogerConfig = true;
        }

        try {
            approversStandardTimeConfig = config.get(APPROVERSSTANDARDTIME);
        } catch (NullPointerException e) {
            configBroken = true;
            approversStandardTimeConfigIsBroken = true;
            approversStandardTimeConfig = true;
        }

        if (configBroken) {
            Map<String, Boolean> fixedFile = new HashMap<>();
            fixedFile.put(REMINDER, reminderConfig);
            fixedFile.put(DIALOGER, dialogerConfig);
            fixedFile.put(APPROVERSSTANDARDTIME, approversStandardTimeConfig);

            var errMessage ="---" + System.lineSeparator();

            var isBroken = " is Broken - fixed to \"true\"";

            if (reminderConfigIsBroken) errMessage += REMINDER + isBroken + System.lineSeparator();
            if (dialogerConfigIsBroken) errMessage += DIALOGER + isBroken + System.lineSeparator();
            if (approversStandardTimeConfigIsBroken) errMessage += APPROVERSSTANDARDTIME + isBroken + System.lineSeparator();
            errMessage += "--- end of Details." + System.lineSeparator();

            System.err.println(errMessage);

            try {
                dump(fixedFile);
                System.err.println("Fixed \"Config.yaml\".");
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failure at fix \"Config.yaml\".");
            }
        }
    }

    private Map<String, Boolean> load() throws IOException {
        FileReader reader = null;
        reader = new FileReader(FILE, StandardCharsets.UTF_8);

        var chars = new ArrayList<Character>();

        while (true) {
            int _byte = 0;
                _byte = reader.read();

            if (_byte != -1) {
                chars.add((char)_byte);
            } else {
                break;
            }
        }

        var charsIterator = chars.iterator();
        var string = "";
        while (true) {
            if (charsIterator.hasNext()) {
                string += charsIterator.next();
            } else {
                break;
            }
        }

        return YAML.load(string);
    }

    private void dump(Map<String, Boolean> dumpFile) throws IOException {
        var dumpStream = new FileOutputStream(FILE, false);
        dumpStream.write(YAML.dump(dumpFile).getBytes(StandardCharsets.UTF_8));
        dumpStream.flush();
        dumpStream.close();
    }

    private void initialize() throws IOException {
        var defaultConfig = new HashMap<String, Boolean>();
        defaultConfig.put(REMINDER, true);
        defaultConfig.put(DIALOGER, true);
        defaultConfig.put(APPROVERSSTANDARDTIME, true);

        dump(defaultConfig);

        System.err.print("Config.yaml was initialized." + System.lineSeparator()
                + "Default Config is All true." + System.lineSeparator()
                + "Please check it." + System.lineSeparator());
    }
}
