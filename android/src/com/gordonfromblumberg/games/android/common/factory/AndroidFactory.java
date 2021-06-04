package com.gordonfromblumberg.games.android.common.factory;

import com.gordonfromblumberg.games.core.common.factory.AbstractFactory;

public class AndroidFactory extends AbstractFactory {
    private AndroidFactory() {
    }

    public static void init() {
        AbstractFactory.instance = new AndroidFactory();
    }
}
