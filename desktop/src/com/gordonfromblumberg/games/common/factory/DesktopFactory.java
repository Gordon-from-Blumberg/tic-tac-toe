package com.gordonfromblumberg.games.common.factory;

import com.gordonfromblumberg.games.common.utils.ConfigManager;
import com.gordonfromblumberg.games.common.utils.DesktopConfigManager;

public class DesktopFactory extends AbstractFactory {
    public DesktopFactory() {
        AbstractFactory.instance = this;
    }

    @Override
    protected ConfigManager createConfigManager() {
        return new DesktopConfigManager();
    }
}
