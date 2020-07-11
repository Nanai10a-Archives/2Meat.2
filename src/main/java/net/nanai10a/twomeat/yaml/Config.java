package net.nanai10a.twomeat.yaml;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlDump;
import com.amihaiemil.eoyaml.YamlMapping;

import java.io.*;

public class Config {
    private static File CONFIGFILE = new File("Config.yaml");
    private static FileWriter YAMLOUTPUT;
    private static int fetchNum = 0;
    private static int writeNum = 0;

    static {
        try {
            YAMLOUTPUT = new FileWriter(CONFIGFILE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!CONFIGFILE.exists()) {
            initialize();
        } else {
            fetch();
        }
    }

    private static boolean Reminder;
    private static boolean Dialoger;
    private static boolean ApproversStandardTime;

    public static void save(ConfigItem item, boolean isAble) {

        fetch();

        YamlDump changedConfig = null;

        switch (item) {
            case REMINDER:

                changedConfig = Yaml.createYamlDump(new Object() {
                    private final boolean Reminder = isAble;
                    private final boolean Dialoger = Config.Dialoger;
                    private final boolean ApproversStandardTime = Config.ApproversStandardTime;
                });

                break;
            case DIALOGER:

                changedConfig = Yaml.createYamlDump(new Object() {
                    private final boolean Reminder = Config.Reminder;
                    private final boolean Dialoger = isAble;
                    private final boolean ApproversStandardTime = Config.ApproversStandardTime;
                });

                break;
            case APPROVERSSTANDARDTIME:

                changedConfig = Yaml.createYamlDump(new Object() {
                    private final boolean Reminder = Config.Reminder;
                    private final boolean Dialoger = Config.Dialoger;
                    private final boolean ApproversStandardTime = isAble;
                });

                break;
        }

        if (changedConfig != null) {
            write(changedConfig);
        } else {
            throw new NullPointerException();
        }
    }

    public static boolean isReminder() {
        return Reminder;
    }
    public static boolean isDialoger() {
        return Dialoger;
    }
    public static boolean isApproversStandardTime() {
        return ApproversStandardTime;
    }

    private static void write(YamlDump config) {
        writeNum++;

        try {
            Yaml.createYamlPrinter(YAMLOUTPUT)
                    .print(config.dump());
        } catch (IOException e) {
            if (writeNum > 5) {
                e.printStackTrace();
                writeNum = 0;
            } else {
                write(config);
            }
        }
        writeNum = 0;
    }

    private static void fetch() {
        fetchNum++;

        YamlMapping configFileMapping = null;

        try {
            configFileMapping = Yaml.createYamlInput(CONFIGFILE)
                    .readYamlMapping();
        } catch (IOException e) {
            if (fetchNum > 5) {
                e.printStackTrace();
                fetchNum = 0;
            } else {
                fetch();
            }
            fetchNum = 0;
        }

        //ここは要検証。booleanがどうやって記録されるのかとか、keyがどうなるのか分からん
        Reminder = configFileMapping.value("Reminder").equals(true);
        Dialoger = configFileMapping.value("Dialoger").equals(true);
        ApproversStandardTime = configFileMapping.value("ApproversStandardTime").equals(true);

        fetchNum = 0;
    }

    private static void initialize() {

        var defaultConfig = Yaml.createYamlDump(new Object() {
            private final boolean Reminder = true;
            private final boolean Dialoger = true;
            private final boolean ApproversStandardTime = true;
        });

        write(defaultConfig);

    }

}