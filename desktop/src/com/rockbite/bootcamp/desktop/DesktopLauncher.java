package com.rockbite.bootcamp.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.rockbite.bootcamp.FlappyBird;

public class DesktopLauncher {
    //Atlas rebuilding flag
    private static boolean rebuildAtlas = false;

    public static void main(String[] arg) {

        if (rebuildAtlas) {
            TexturePacker.Settings settings = new TexturePacker.Settings();
            settings.maxWidth = 2048;
            settings.maxHeight = 2048;
            settings.duplicatePadding = false;
//            settings.debug = true;
            //TexturePacker.process(settings, source folder of images, output folder, atlas file)
            TexturePacker.process(settings, "pictures",
                    "images",
                    "flappyBirdAtlas");
        }

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "flappyBird";
        config.width = 1200;
        config.height = 800;
        new LwjglApplication(new FlappyBird(), config);
    }
}
