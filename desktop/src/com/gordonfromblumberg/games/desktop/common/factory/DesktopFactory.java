package com.gordonfromblumberg.games.desktop.common.factory;

import com.gordonfromblumberg.games.core.common.factory.AbstractFactory;
import com.gordonfromblumberg.games.core.common.utils.ConfigManager;
import com.gordonfromblumberg.games.desktop.common.utils.DesktopConfigManager;

public class DesktopFactory extends AbstractFactory {
    public DesktopFactory() {
        AbstractFactory.instance = this;
    }

    @Override
    protected ConfigManager createConfigManager() {
        return new DesktopConfigManager();
    }
}
