package com.puzzle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

public class PuzzlePiece {
    private GridPoint2 piecePosition;
    TextureRegion puzzlePiece;
    private GridPoint2 correctPosition;

    PuzzlePiece(TextureRegion puzzlePiece, GridPoint2 piecePosition, GridPoint2 correctPosition){
        this.puzzlePiece = puzzlePiece;
        this.piecePosition = piecePosition;
        this.correctPosition = correctPosition;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(puzzlePiece, piecePosition.x, piecePosition.y);
    }

    public boolean isMouseIn(GridPoint2 mousePos) {
        return (mousePos.x > piecePosition.x && mousePos.x < piecePosition.x + puzzlePiece.getRegionWidth()
                && mousePos.y > piecePosition.y && mousePos.y < piecePosition.y+ puzzlePiece.getRegionHeight() );
    }

    public void movePiece(GridPoint2 mousePos, GridPoint2 lastMousePosition) {
        piecePosition.x += mousePos.x - lastMousePosition.x;
        piecePosition.y += mousePos.y - lastMousePosition.y;
    }

    public boolean isInRegion(GridPoint2 mousePos){
        return (mousePos.x > correctPosition.x && mousePos.y > correctPosition.y
            && mousePos.x < correctPosition.x + puzzlePiece.getRegionWidth() && mousePos.y < correctPosition.y + puzzlePiece.getRegionHeight());
    }
    public void setCorrectPosition() {
        piecePosition.x = correctPosition.x;
        piecePosition.y = correctPosition.y;
    }

}
