package com.gordonfromblumberg.games.space_shooter.utils;

import com.gordonfromblumberg.games.common.utils.ConfigManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class DesktopConfigManager extends ConfigManager {
    @Override
    protected void loadConfig(String configPath) {
        configPath = "/" + configPath;
        URL url = getClass().getResource(configPath);
        if (url != null) {

            try (InputStreamReader isr = new InputStreamReader(url.openStream())) {
                configProperties.load(isr);
            } catch (IOException e) {
                throw new RuntimeException("Couldn't load config from " + configPath, e);
            }

        } else {
            throw new RuntimeException("Config " + configPath + " not found");
        }
    }
}
