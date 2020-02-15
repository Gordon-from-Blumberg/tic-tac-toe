package com.gordonfromblumberg.game_template.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gordonfromblumberg.game_template.Main;
import com.gordonfromblumberg.game_template.desktop.factory.DesktopFactory;
import com.gordonfromblumberg.game_template.utils.ConfigManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		DesktopFactory factory = new DesktopFactory();
		ConfigManager configManager = factory.configManager();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.foregroundFPS = 0;
//		config.vSyncEnabled = false;
		config.title = "game_template";
		config.width = configManager.getInteger("screenWidth");
		config.height = configManager.getInteger("screenHeight");
		new LwjglApplication(Main.create(factory), config);
	}
}
