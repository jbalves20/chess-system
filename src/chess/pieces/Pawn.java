/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

/**
 *
 * @author João Alves
 */
public class Pawn extends ChessPiece {
    
    public Pawn(Board board, Color color) {
        super(board, color);
    }
    
    @Override
    public String toString(){
        return "P";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];
        
        Position p = new Position (0, 0);
        
        if(getColor() == Color.WHITE){
            p.setValues(position.getRow() - 1, position.getColumn());
            if(getBoard().positionExists(p) && !getBoard().isThereAPiece(p)){
                matrix[p.getRow()][p.getColumn()] = true;
                // second square on first move
                p.setRow(p.getRow() - 1);
                if(getBoard().positionExists(p) && !getBoard().isThereAPiece(p) && getMoveCount() == 0){
                    matrix[p.getRow()][p.getColumn()] = true;
                }
            }
            // diagonal captures: left
            p.setValues(position.getRow() - 1, position.getColumn() - 1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                matrix[p.getRow()][p.getColumn()] = true;
            }
            // diagonal captures: right
            p.setValues(position.getRow() - 1, position.getColumn() + 1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                matrix[p.getRow()][p.getColumn()] = true;
            }
        }
        else{
            p.setValues(position.getRow() + 1, position.getColumn());
            if(getBoard().positionExists(p) && !getBoard().isThereAPiece(p)){
                matrix[p.getRow()][p.getColumn()] = true;
                // second square on first move
                p.setRow(p.getRow() + 1);
                if(getBoard().positionExists(p) && !getBoard().isThereAPiece(p) && getMoveCount() == 0){
                    matrix[p.getRow()][p.getColumn()] = true;
                }
            }
            // diagonal captures: left
            p.setValues(position.getRow() + 1, position.getColumn() - 1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                matrix[p.getRow()][p.getColumn()] = true;
            }
            // diagonal captures: right
            p.setValues(position.getRow() + 1, position.getColumn() + 1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                matrix[p.getRow()][p.getColumn()] = true;
            }
        }
        
        return matrix;
    }
}
