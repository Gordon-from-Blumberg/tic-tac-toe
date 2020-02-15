package com.gordonfromblumberg.game_template.utils;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TextureAtlasPacker {
    public static void main(String[] args) {
        TexturePacker.process("../../core/resources/image", "image", "texture_pack");
    }
}
