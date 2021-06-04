package com.gordonfromblumberg.games.html.common.factory;

import com.gordonfromblumberg.games.core.common.factory.AbstractFactory;

public class HtmlFactory extends AbstractFactory {
    private HtmlFactory() {
    }

    public static void init() {
        AbstractFactory.instance = new HtmlFactory();
    }
}
