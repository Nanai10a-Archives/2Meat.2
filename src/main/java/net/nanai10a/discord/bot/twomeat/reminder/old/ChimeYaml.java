package net.nanai10a.discord.bot.twomeat.reminder.old;

import org.yaml.snakeyaml.Yaml;

import java.io.*;

public class ChimeYaml {
    private Yaml yaml = new Yaml();
    private Io yamlIo;

    public ChimeYaml(String fileName) throws IOException {
        this.yamlIo = new YamlIo(new File(fileName));
    }

    public void DampToYaml(Object data) throws IOException {
        yamlIo.Output(data);
    }
}

class YamlIo extends Io {
    private Yaml yaml = new Yaml();
    private File targetFile;
    private Writer outputer;
    private Reader inputter;

    public YamlIo(File targetFile) throws IOException {
        this.targetFile = targetFile;
        outputer = new FileWriter(this.targetFile);
        inputter = new FileReader(this.targetFile);
    }

    @Override
    public <T> T InputMap() {
        return yaml.load(inputter);
    }

    @Override
    public void Output(Object data) throws IOException {
        outputer.write(yaml.dump(data));
    }
}
