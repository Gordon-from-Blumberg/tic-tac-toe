package com.gordonfromblumberg.games.android.common.factory;

import com.gordonfromblumberg.games.core.common.factory.AbstractFactory;

public class AndroidFactory extends AbstractFactory {
    public AndroidFactory() {
        AbstractFactory.instance = this;
    }
}
