package com.gordonfromblumberg.game_template;

import com.badlogic.gdx.Game;
import com.gordonfromblumberg.game_template.screens.AbstractScreen;
import com.gordonfromblumberg.game_template.screens.MainMenuScreen;

public class Main extends Game {

	public static Main INSTANCE;
	private final MainMenuScreen mainMenuScreen;

	public static Main createInstance() {
		INSTANCE = new Main();
		return INSTANCE;
	}

	private Main() {
	    mainMenuScreen = new MainMenuScreen();
    }
	
	@Override
	public void create() {
		setScreen(mainMenuScreen);
	}

	public AbstractScreen getCurrentScreen() {
		return (AbstractScreen) screen;
	}

	public void goToMainMenu() {
	    setScreen(mainMenuScreen);
    }

	@Override
	public void dispose() {
		super.dispose();
	}
}
