/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgame;

/**
 *
 * @author João Alves
 */
public class Piece {
    
    protected Position position;
    private Board board;

    public Piece() {
    }

    public Piece(Board board) {
        this.board = board;
        position = null;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    protected Board getBoard() {
        return board;
    }
    
    public Piece[][] possibleMoves(){
        return null;
    }
    
    public boolean isMovePossible(Position position){
        return false;
    }
    
    public boolean isThereAnyPossibleMove(){
        return false;
    }
    
    
}
