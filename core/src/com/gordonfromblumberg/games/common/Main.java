package com.gordonfromblumberg.games.common;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gordonfromblumberg.games.common.factory.AbstractFactory;
import com.gordonfromblumberg.games.common.screens.AbstractScreen;
import com.gordonfromblumberg.games.common.screens.MainMenuScreen;
import com.gordonfromblumberg.games.common.utils.JsonConfigLoader;

import java.util.function.Consumer;

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
//		setJsonConfigLoader(class, function);

		this.factory = factory;
	    this.mainMenuScreen = new MainMenuScreen();
    }
	
	@Override
	public void create() {
	    assetManager.load("image/texture_pack.atlas", TextureAtlas.class);
		loadUiAssets();

		assetManager.finishLoading();

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

	/**
	 * Adds custom json loader to asset manager
	 * @param type To this class json data will be mapped
	 * @param onLoadHandler This function will be invoked after loading has finished. May be null.
	 * @param <T> Class of config
	 */
	private <T> void setJsonConfigLoader(Class<T> type, Consumer<T> onLoadHandler) {
		assetManager.setLoader(type, new JsonConfigLoader<>(assetManager.getFileHandleResolver(), type, onLoadHandler));
	}

	private void loadUiAssets() {
		assetManager.load("ui/uiskin.atlas", TextureAtlas.class);
		assetManager.load("ui/uiskin.json", Skin.class);
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
