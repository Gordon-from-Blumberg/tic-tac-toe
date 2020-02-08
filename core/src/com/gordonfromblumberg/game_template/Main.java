package com.gordonfromblumberg.game_template;

import com.badlogic.gdx.Game;
import com.gordonfromblumberg.game_template.factory.AbstractFactory;
import com.gordonfromblumberg.game_template.screens.AbstractScreen;
import com.gordonfromblumberg.game_template.screens.MainMenuScreen;

public class Main extends Game {
	private static Main instance;

	public static final String NAME = "space_shooter";

	private final AbstractFactory factory;

	private final MainMenuScreen mainMenuScreen;

	public static Main create(AbstractFactory factory) {
		instance = new Main(factory);
		return instance;
	}

	public static Main getInstance() {
		return instance;
	}

	private Main(AbstractFactory factory) {
		this.factory = factory;
		this.mainMenuScreen = new MainMenuScreen();
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
