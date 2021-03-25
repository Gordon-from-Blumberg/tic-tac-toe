package com.gordonfromblumberg.games.android.common;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.gordonfromblumberg.games.android.common.factory.AndroidFactory;
import com.gordonfromblumberg.games.core.common.Main;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		new AndroidFactory();
		initialize(Main.createInstance(), config);
	}
}
