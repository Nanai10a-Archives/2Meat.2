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

public class SpecifiedLocationMap {
    private static SpecifiedLocationMap SPECIFIEDLOCATIONMAP;

    static {
        try {
            SPECIFIEDLOCATIONMAP = new SpecifiedLocationMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Yaml YAML = new Yaml();
    private final File FILE;

    private SpecifiedLocationMap() throws IOException {
        if (!(new File("SpecifiedLocationMap.yaml").exists())) {
            new File("SpecifiedLocationMap.yaml").createNewFile();
            FILE = new File("SpecifiedLocationMap.yaml");
        }
        else FILE = Paths.get("SpecifiedLocationMap.yaml").toFile();
    }

    public static Map<String, Integer> load() throws IOException {
        FileReader reader;
        reader = new FileReader(SPECIFIEDLOCATIONMAP.FILE, StandardCharsets.UTF_8);

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

        return SPECIFIEDLOCATIONMAP.YAML.load(string);
    }

    public static void dump(Map<String, Integer> dumpFile) throws IOException {
        var dumpStream = new FileOutputStream(SPECIFIEDLOCATIONMAP.FILE, false);
        dumpStream.write(SPECIFIEDLOCATIONMAP.YAML.dump(dumpFile).getBytes(StandardCharsets.UTF_8));
        dumpStream.flush();
        dumpStream.close();
    }
}