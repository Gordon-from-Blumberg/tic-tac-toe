package com.gordonfromblumberg.game_template.desktop.factory;

import com.gordonfromblumberg.games.common.utils.DesktopConfigManager;
import com.gordonfromblumberg.games.common.factory.AbstractFactory;
import com.gordonfromblumberg.games.common.utils.ConfigManager;

public class DesktopFactory extends AbstractFactory {
    public DesktopFactory() {
        AbstractFactory.instance = this;
    }

    @Override
    protected ConfigManager createConfigManager() {
        return new DesktopConfigManager();
    }
}
