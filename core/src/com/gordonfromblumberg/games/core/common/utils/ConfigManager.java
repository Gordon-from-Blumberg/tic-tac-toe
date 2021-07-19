package com.gordonfromblumberg.games.core.common.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.ObjectMap;
import com.gordonfromblumberg.games.core.common.Main;

import java.io.BufferedReader;
import java.io.IOException;

public class ConfigManager {
    private static final String DEFAULT_CONFIG_FILE = "config/default-config.properties";
    private static final String CONFIG_FILE = "config/config.properties";
    private static final String CONFIG_PREFERENCE = Main.NAME + ".config";

    protected final ObjectMap<String, String> configProperties = new ObjectMap<>();

    public ConfigManager() {
    }

    public void init() {
        loadConfig(DEFAULT_CONFIG_FILE);
        loadConfig(CONFIG_FILE);
    }

    public String getString(String propertyName) {
        return String.valueOf(configProperties.get(propertyName));
    }

    public int getInteger(String propertyName) {
        String property = configProperties.get(propertyName);
        if (property != null) {
            try {
                return Integer.parseInt(property);
            } catch (NumberFormatException e) {
                throw new RuntimeException(
                        StringUtils.format("Couldn't parse property # = #", propertyName, property),
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
        String property = configProperties.get(propertyName);
        if (property != null) {
            try {
                return Float.parseFloat(property);
            } catch (NumberFormatException e) {
                throw new RuntimeException(
                        StringUtils.format("Couldn't parse property # = #", propertyName, property),
                        e
                );
            }
        }
        return 0f;
    }

    protected void loadConfig(String configPath) {
        try (BufferedReader reader = new BufferedReader(Gdx.files.internal(configPath).reader())) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.trim().isEmpty())
                    continue;

                String[] keyAndValue = line.split("=");
                configProperties.put(keyAndValue[0].trim(), keyAndValue[1].trim());
            }
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load config from " + configPath, e);
        }
    }
}
