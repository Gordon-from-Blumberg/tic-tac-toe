package com.gordonfromblumberg.games.common.factory;

public class AndroidFactory extends AbstractFactory {
    public AndroidFactory() {
        AbstractFactory.instance = this;
    }
}
