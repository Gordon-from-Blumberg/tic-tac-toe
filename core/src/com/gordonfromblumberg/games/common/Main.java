package com.gordonfromblumberg.games.common;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gordonfromblumberg.games.common.factory.AbstractFactory;
import com.gordonfromblumberg.games.common.screens.AbstractScreen;
import com.gordonfromblumberg.games.common.screens.MainMenuScreen;

public class Main extends Game {
	private static Main instance;

	public static final String NAME = "game_template";

	private final AssetManager assetManager;
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
		this.assetManager = new AssetManager();
		this.factory = factory;
	    this.mainMenuScreen = new MainMenuScreen();
    }
	
	@Override
	public void create() {
	    assetManager.load("image/texture_pack.atlas", TextureAtlas.class);
		loadUiAssets();

		setScreen(mainMenuScreen);
	}

	public AbstractScreen getCurrentScreen() {
		return (AbstractScreen) screen;
	}

	public void goToMainMenu() {
	    setScreen(mainMenuScreen);
    }

    public AssetManager assets() {
		return assetManager;
	}

	private void loadUiAssets() {
		assetManager.load("ui/uiskin.atlas", TextureAtlas.class);
		assetManager.load("ui/uiskin.json", Skin.class);
		assetManager.finishLoading();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
