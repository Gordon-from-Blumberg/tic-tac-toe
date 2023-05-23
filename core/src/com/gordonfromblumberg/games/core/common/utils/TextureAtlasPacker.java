package com.gordonfromblumberg.games.core.common.utils;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TextureAtlasPacker {
    public static void main(String[] args) {
        TexturePacker.process("core/resources/image", "android/assets/image", "texture_pack");
    }
}
