package com.gordonfromblumberg.games.core.common.utils;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.function.Consumer;

public class JsonConfigLoader<T> extends AsynchronousAssetLoader<T, JsonConfigLoader.JsonConfigParameter<T>> {

    private final Class<T> type;
    private final Consumer<T> onLoadHandler;

    public JsonConfigLoader(FileHandleResolver resolver, Class<T> type) {
        this(resolver, type, null);
    }

    public JsonConfigLoader(FileHandleResolver resolver, Class<T> type, Consumer<T> onLoadHandler) {
        super(resolver);

        this.type = type;
        this.onLoadHandler = onLoadHandler;
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, JsonConfigParameter parameter) {

    }

    @Override
    public T loadSync(AssetManager manager, String fileName, FileHandle file, JsonConfigParameter parameter) {
        final Json json = new Json();
        T result = json.fromJson(type, file);
        if (onLoadHandler != null) {
            onLoadHandler.accept(result);
        }
        return result;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, JsonConfigParameter parameter) {
        return null;
    }

    public static class JsonConfigParameter<T> extends AssetLoaderParameters<T> {

    }
}
