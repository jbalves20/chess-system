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
public abstract class Piece {
    
    protected Position position;
    private Board board;

    public Piece(Board board) {
        this.board = board;
        position = null;
    }
    
    protected Board getBoard() {
        return board;
    }
    
    public abstract boolean[][] possibleMoves();
    
    // Hook method: this method depends on an implementation of the possibleMoves() above in a class that implements Piece.
    public boolean isMovePossible(Position position){
        return possibleMoves()[position.getRow()][position.getColumn()];
    }
    
    // Another Hook method
    public boolean isThereAnyPossibleMove(){
        boolean[][] matrix = possibleMoves();
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                if(matrix[i][j]){
                    return true;
                }
            }
        }
        return false;
    }
}
