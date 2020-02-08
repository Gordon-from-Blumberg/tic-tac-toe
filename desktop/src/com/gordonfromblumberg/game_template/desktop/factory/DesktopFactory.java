package com.gordonfromblumberg.game_template.desktop.factory;

import com.gordonfromblumberg.game_template.desktop.utils.DesktopConfigManager;
import com.gordonfromblumberg.game_template.factory.AbstractFactory;
import com.gordonfromblumberg.game_template.utils.ConfigManager;

public class DesktopFactory extends AbstractFactory {
    public DesktopFactory() {
        AbstractFactory.instance = this;
    }

    @Override
    protected ConfigManager createConfigManager() {
        return new DesktopConfigManager();
    }
}
