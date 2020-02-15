package com.gordonfromblumberg.games.common.factory;

import com.gordonfromblumberg.games.common.utils.ConfigManager;
import com.gordonfromblumberg.games.space_shooter.utils.DesktopConfigManager;

public class DesktopFactory extends AbstractFactory {
    public DesktopFactory() {
        AbstractFactory.instance = this;
    }

    @Override
    protected ConfigManager createConfigManager() {
        return new DesktopConfigManager();
    }
}
