package me.winter.gdx.animation.scml;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import me.winter.gdx.animation.scml.SCMLLoader.Parameters;

/**
 * Loads a SCML file (Spriter format) into LibGDX's AssetManager
 * <p>
 * Created on 2017-01-16.
 *
 * @author Alexander Winter
 */
public class SCMLLoader extends SynchronousAssetLoader<SCMLProject, Parameters> {
    private final SCMLReader reader;

    public SCMLLoader(FileHandleResolver resolver) {
        this(resolver, new SCMLReader());
    }

    public SCMLLoader(FileHandleResolver resolver, SCMLReader reader) {
        super(resolver);
        this.reader = reader;
    }

    @Override
    public SCMLProject load(AssetManager assetManager, String fileName, FileHandle file, Parameters params) {
        reader.setAtlas(assetManager.get(params.textureAtlasName));
        return reader.load(file.read());
    }

    public SCMLProject load(TextureAtlas textureAtlas, FileHandle scmlFile) {
        reader.setAtlas(textureAtlas);
        return reader.load(scmlFile.read());
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, Parameters params) {
        AssetDescriptor<TextureAtlas> descriptor = new AssetDescriptor<>(params.textureAtlasName, TextureAtlas.class);
        Array<AssetDescriptor> array = new Array<>();
        array.add(descriptor);
        return array;
    }

    public static class Parameters extends AssetLoaderParameters<SCMLProject> {
        public Parameters(String textureAtlasName) {
            this.textureAtlasName = textureAtlasName;
        }

        public String textureAtlasName;
    }
}
