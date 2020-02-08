package com.gordonfromblumberg.game_template.android.factory;

import com.gordonfromblumberg.game_template.factory.AbstractFactory;

public class AndroidFactory extends AbstractFactory {
    public AndroidFactory() {
        AbstractFactory.instance = this;
    }
}
