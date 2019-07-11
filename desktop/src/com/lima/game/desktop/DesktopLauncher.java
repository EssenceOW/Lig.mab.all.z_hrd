package com.lima.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lima.game.blzhrd;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = blzhrd.WIDTH;
		config.height = blzhrd.HEIGHT;
		config.title = blzhrd.TITLE;
		new LwjglApplication(new blzhrd(), config);
	}
}
