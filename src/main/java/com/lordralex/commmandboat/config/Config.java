package com.lordralex.commmandboat.config;

import com.lordralex.commandboat.bukkit.CommandBoat;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @version 0.1
 * @author Lord_Ralex
 * @since 0.1
 */
public class Config {

    private FileConfiguration advanced;
    private boolean useAdvanced;

    public Config() {
    }

    public void load() {
        File advancedFile = new File(CommandBoat.getInstance().getDataFolder(), "advanced-config.yml");

        if (!advancedFile.exists()) {
            BufferedWriter writer = null;
            BufferedReader reader = null;
            try {
                InputStream in = CommandBoat.getInstance().getResource("advanced-config.yml");
                reader = new BufferedReader(new InputStreamReader(in));
                writer = new BufferedWriter(new FileWriter(advancedFile));
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
        advanced = YamlConfiguration.loadConfiguration(advancedFile);
    }

    public String get(String path) {
        return advanced.getString(path);
    }
}
