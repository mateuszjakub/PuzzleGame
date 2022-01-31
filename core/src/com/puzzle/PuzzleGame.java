package com.puzzle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class PuzzleGame extends ApplicationAdapter {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 650;

	private static final int pieceWidth = 150;
	private static final int pieceHeight = 150;

	private SpriteBatch batch;
	private Texture puzzleOutlineImg;
	private Texture puzzleImg;
	private int originPositionX;
	private int originPositionY;
	private List<PuzzlePiece> pieceList;
	private List<PuzzlePiece> settledPieceList;
	private PuzzlePiece selectedPiece;
	private GridPoint2 lastMousePosition = new GridPoint2();

	@Override
	public void create () {
		batch = new SpriteBatch();
		puzzleOutlineImg = new Texture("puzzle_outline.jpg");
		puzzleImg = new Texture("mountain.jpg");
		pieceList = new LinkedList<>();
		settledPieceList = new LinkedList<>();
		originPositionX = WINDOW_WIDTH/2- puzzleOutlineImg.getWidth()/2;
		originPositionY = WINDOW_HEIGHT/2- puzzleOutlineImg.getHeight()/2;
		preparePuzzlePiece();
	}

	@Override
	public void render () {
		handleMouse();
		ScreenUtils.clear(1, 1, 1, 1);

		batch.begin();

		batch.draw(puzzleOutlineImg, originPositionX, originPositionY);
		settledPieceList.forEach(x -> x.draw(batch));
		pieceList.forEach(x -> x.draw(batch));
		if (selectedPiece != null) {
			selectedPiece.draw(batch);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		puzzleOutlineImg.dispose();
		puzzleImg.dispose();
	}

	private void handleMouse() {
		GridPoint2 mousePos = getAndMapMousePos();
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			ListIterator<PuzzlePiece> listIterator = pieceList.listIterator(pieceList.size());
			while (listIterator.hasPrevious()) {
				PuzzlePiece puzzlePiece = listIterator.previous();
			if (puzzlePiece.isMouseIn(mousePos)) {
				selectedPiece = puzzlePiece;
				listIterator.remove();
				break;
			}
			}
						lastMousePosition.set(mousePos);

		}
		if (Gdx.input.isButtonPressed((Input.Buttons.LEFT))&&selectedPiece!=null){
			selectedPiece.movePiece(mousePos, lastMousePosition);
			lastMousePosition.set(mousePos);
		}

		else if (selectedPiece != null) {
			if (selectedPiece.isInRegion(mousePos)) {
				selectedPiece.setCorrectPosition();
				settledPieceList.add(selectedPiece);
			}
			else {
				pieceList.add(selectedPiece);
			}
			selectedPiece = null;
		}
	}

	private GridPoint2 getAndMapMousePos() {
		return new GridPoint2(Gdx.input.getX(), WINDOW_HEIGHT-Gdx.input.getY());
	}

	private void preparePuzzlePiece() {
		for (int col=0; col < puzzleImg.getWidth()/pieceWidth; col++) {
			for(int row=0; row <puzzleImg.getHeight()/pieceHeight; row++) {
				TextureRegion puzzlePieceImg = new TextureRegion();
				puzzlePieceImg.setTexture(puzzleImg);
				puzzlePieceImg.setRegion(col * pieceWidth, row*pieceHeight, pieceWidth, pieceHeight);
				GridPoint2 piecePosition = randomPosition();
				GridPoint2 correctPosition = new GridPoint2
						((WINDOW_WIDTH- puzzleImg.getWidth())/2+col*pieceWidth,
						(WINDOW_HEIGHT- puzzleImg.getHeight())/2+(2-row)*pieceHeight);
				PuzzlePiece piece = new PuzzlePiece(puzzlePieceImg, piecePosition, correctPosition);
				pieceList.add(piece);
			}
		}
	}

	private GridPoint2 randomPosition () {
		return new GridPoint2(randomInt(WINDOW_WIDTH,pieceWidth), randomInt(WINDOW_HEIGHT,pieceHeight));
	}
	private int randomInt (int maxValue, int pieceSize) {
		return (int) (Math.random()*(maxValue-pieceSize));
	}


}
