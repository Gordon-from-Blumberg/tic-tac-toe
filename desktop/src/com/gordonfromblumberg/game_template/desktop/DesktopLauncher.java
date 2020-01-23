package com.gordonfromblumberg.game_template.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gordonfromblumberg.game_template.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.foregroundFPS = 0;
//		config.vSyncEnabled = false;
		new LwjglApplication(Main.createInstance(), config);
	}
}
