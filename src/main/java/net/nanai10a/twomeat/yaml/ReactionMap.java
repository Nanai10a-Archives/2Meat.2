package net.nanai10a.twomeat.yaml;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class ReactionMap {
    private static ReactionMap REACTIONMAP;

    static {
        try {
            REACTIONMAP = new ReactionMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Yaml YAML = new Yaml();
    private final File FILE;

    private ReactionMap() throws IOException {
        if (!(new File("ReactionMap.yaml").exists())) {
            new File("ReactionMap.yaml").createNewFile();
            FILE = new File("ReactionMap.yaml");
        }
        else FILE = Paths.get("ReactionMap.yaml").toFile();
    }

    public static Map<String, String> load() throws IOException {
        FileReader reader;
        reader = new FileReader(REACTIONMAP.FILE, StandardCharsets.UTF_8);

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

        return REACTIONMAP.YAML.load(string);
    }

    public static void dump(Map<String, String> dumpFile) throws IOException {
        var dumpStream = new FileOutputStream(REACTIONMAP.FILE, false);
        dumpStream.write(REACTIONMAP.YAML.dump(dumpFile).getBytes(StandardCharsets.UTF_8));
        dumpStream.flush();
        dumpStream.close();
    }
}
