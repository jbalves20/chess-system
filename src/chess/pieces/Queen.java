/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

/**
 *
 * @author João Alves
 */
public class Queen extends ChessPiece {
    
    public Queen(Board board, Color color) {
        super(board, color);
    }
    
    @Override
    public String toString(){
        return "Q";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];
        return matrix;
    }
}
