package com.lordralex.commandboat.config;

import com.lordralex.commandboat.bukkit.CommandBoat;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @version 0.1
 * @author Lord_Ralex
 * @since 0.1
 */
public abstract class Config {

    protected final String name;
    protected final File file;
    protected final FileConfiguration config;

    public Config(String fileName) {
        name = fileName;
        file = new File(CommandBoat.getInstance().getDataFolder(), fileName);
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void load() throws IOException, InvalidConfigurationException {

        if (!file.exists()) {
            BufferedWriter writer = null;
            BufferedReader reader = null;
            try {
                InputStream in = CommandBoat.getInstance().getResource("commands.yml");
                reader = new BufferedReader(new InputStreamReader(in));
                writer = new BufferedWriter(new FileWriter(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.write(System.lineSeparator());
                }
            } catch (IOException ex) {
                CommandBoat.getInstance().getLogger().log(Level.SEVERE, null, ex);
            } finally {
                try {
                    writer.close();
                } catch (IOException ex) {
                    CommandBoat.getInstance().getLogger().log(Level.SEVERE, null, ex);
                }
                try {
                    reader.close();
                } catch (IOException ex) {
                    CommandBoat.getInstance().getLogger().log(Level.SEVERE, null, ex);
                }
            }
        }
        config.load(file);
    }

    public String getString(String path) {
        return getString(path, null);
    }

    public String getString(String path, String def) {
        return config.getString(path, def);
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
