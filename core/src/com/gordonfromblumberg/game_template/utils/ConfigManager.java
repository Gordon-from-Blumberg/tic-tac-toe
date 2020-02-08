package com.gordonfromblumberg.game_template.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.gordonfromblumberg.game_template.Main;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class ConfigManager {
    private static final String DEFAULT_CONFIG_FILE = "config/default-config.properties";
    private static final String CONFIG_FILE = "config/config.properties";
    private static final String CONFIG_PREFERENCE = Main.NAME + ".config";

    protected final Properties configProperties = new Properties();

    public ConfigManager() {
        loadConfig(DEFAULT_CONFIG_FILE);
        loadConfig(CONFIG_FILE);
    }

    public int getInteger(String propertyName) {
        String property = configProperties.getProperty(propertyName);
        if (property != null) {
            try {
                return Integer.parseInt(property);
            } catch (NumberFormatException e) {
                throw new RuntimeException(
                        String.format("Couldn't parse property %s = %s", propertyName, property),
                        e
                );
            }
        }
        return 0;
    }

    public int getInteger(String propertyName, boolean usePreference) {
        if (usePreference) {
            Preferences prefs = Gdx.app.getPreferences(CONFIG_PREFERENCE);
            int value = prefs.getInteger(propertyName);
            if (value != 0)
                return value;
        }

        return getInteger(propertyName);
    }

    public float getFloat(String propertyName) {
        String property = configProperties.getProperty(propertyName);
        if (property != null) {
            try {
                return Float.parseFloat(property);
            } catch (NumberFormatException e) {
                throw new RuntimeException(
                        String.format("Couldn't parse property %s = %s", propertyName, property),
                        e
                );
            }
        }
        return 0f;
    }

    protected void loadConfig(String configPath) {
        try (Reader reader = Gdx.files.internal(configPath).reader()) {
            configProperties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load config from " + configPath, e);
        }
    }
}
