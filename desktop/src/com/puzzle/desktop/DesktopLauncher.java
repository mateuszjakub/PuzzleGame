package com.puzzle.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.puzzle.PuzzleGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.width = PuzzleGame.WINDOW_WIDTH;
		config.height = PuzzleGame.WINDOW_HEIGHT;
		config.title = "PuzzleGame";
		new LwjglApplication(new PuzzleGame(), config);
	}
}
