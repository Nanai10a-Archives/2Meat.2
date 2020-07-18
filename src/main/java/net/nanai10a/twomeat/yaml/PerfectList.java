package net.nanai10a.twomeat.yaml;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PerfectList {
    private static PerfectList PERFECTLIST;

    static {
        try {
            PERFECTLIST = new PerfectList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Yaml YAML = new Yaml();
    private final File FILE = Paths.get("PerfectList.yaml").toFile();

    private PerfectList() throws IOException {
        if (!(new File("PerfectList.yaml").exists())) new File("PerfectList.yaml").createNewFile();
    }

    public static List<String> load() throws IOException {
        FileReader reader;
        reader = new FileReader(PERFECTLIST.FILE, StandardCharsets.UTF_8);

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

        return PERFECTLIST.YAML.load(string);
    }

    public static void dump(List<String> dumpFile) throws IOException {
        var dumpStream = new FileOutputStream(PERFECTLIST.FILE, false);
        dumpStream.write(PERFECTLIST.YAML.dump(dumpFile).getBytes(StandardCharsets.UTF_8));
        dumpStream.flush();
        dumpStream.close();
    }
}