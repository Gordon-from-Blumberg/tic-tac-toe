package com.gordonfromblumberg.games.desktop.common;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gordonfromblumberg.games.core.common.Main;
import com.gordonfromblumberg.games.core.common.utils.ConfigManager;
import com.gordonfromblumberg.games.desktop.common.factory.DesktopFactory;

public class DesktopLauncher {
	public static void main (String[] arg) {
		DesktopFactory factory = new DesktopFactory();
		ConfigManager configManager = factory.configManager();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.foregroundFPS = 0;
//		config.vSyncEnabled = false;
		config.title = Main.NAME;

		new LwjglApplication(Main.createInstance(), config);
	}
}
