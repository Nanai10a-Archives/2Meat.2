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
import java.util.Map;

public class InclusiveList {
    private static InclusiveList INCLUSIVELIST;

    static {
        try {
            INCLUSIVELIST = new InclusiveList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Yaml YAML = new Yaml();
    private final File FILE = Paths.get("InclusiveList.yaml").toFile();

    private InclusiveList() throws IOException {
        if (!(new File("InclusiveList").exists())) new File("InclusiveList").createNewFile();
    }

    public static List<String> load() throws IOException {
        FileReader reader;
        reader = new FileReader(INCLUSIVELIST.FILE, StandardCharsets.UTF_8);

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

        return INCLUSIVELIST.YAML.load(string);
    }

    public static void dump(List<String> dumpFile) throws IOException {
        var dumpStream = new FileOutputStream(INCLUSIVELIST.FILE, false);
        dumpStream.write(INCLUSIVELIST.YAML.dump(dumpFile).getBytes(StandardCharsets.UTF_8));
        dumpStream.flush();
        dumpStream.close();
    }
}