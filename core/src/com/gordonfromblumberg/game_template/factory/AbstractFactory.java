package com.gordonfromblumberg.game_template.factory;

import com.gordonfromblumberg.game_template.utils.ConfigManager;

public abstract class AbstractFactory {
    public static AbstractFactory instance;

    private ConfigManager configManager;

    public ConfigManager configManager() {
        if (configManager != null)
            return configManager;

        configManager = createConfigManager();
        return configManager;
    }

    protected ConfigManager createConfigManager() {
        return new ConfigManager();
    }
}
