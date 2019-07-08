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
public class Knight extends ChessPiece {
    
    public Knight(Board board, Color color) {
        super(board, color);
    }
    
    @Override
    public String toString(){
        return "N";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];
        
        Position p = new Position(0, 0);
        
        // 2 North, 1 West
        p.setValues(position.getRow() - 2, position.getColumn() - 1);
        if(getBoard().positionExists(p) && (!getBoard().isThereAPiece(p) || isThereOpponentPiece(p))){
            matrix[p.getRow()][p.getColumn()] = true;
        }
        
        // 2 North, 1 East
        p.setValues(position.getRow() - 2, position.getColumn() + 1);
        if(getBoard().positionExists(p) && (!getBoard().isThereAPiece(p) || isThereOpponentPiece(p))){
            matrix[p.getRow()][p.getColumn()] = true;
        }
        
        // 2 East, 1 North
        p.setValues(position.getRow() - 1, position.getColumn() + 2);
        if(getBoard().positionExists(p) && (!getBoard().isThereAPiece(p) || isThereOpponentPiece(p))){
            matrix[p.getRow()][p.getColumn()] = true;
        }
        
        // 2 East, 1 South
        p.setValues(position.getRow() + 1, position.getColumn() + 2);
        if(getBoard().positionExists(p) && (!getBoard().isThereAPiece(p) || isThereOpponentPiece(p))){
            matrix[p.getRow()][p.getColumn()] = true;
        }
        
        // 2 South, 1 East
        p.setValues(position.getRow() + 2, position.getColumn() + 1);
        if(getBoard().positionExists(p) && (!getBoard().isThereAPiece(p) || isThereOpponentPiece(p))){
            matrix[p.getRow()][p.getColumn()] = true;
        }
        
        // 2 South, 1 West
        p.setValues(position.getRow() + 2, position.getColumn() - 1);
        if(getBoard().positionExists(p) && (!getBoard().isThereAPiece(p) || isThereOpponentPiece(p))){
            matrix[p.getRow()][p.getColumn()] = true;
        }
        
        // 2 West, 1 South
        p.setValues(position.getRow() + 1, position.getColumn() - 2);
        if(getBoard().positionExists(p) && (!getBoard().isThereAPiece(p) || isThereOpponentPiece(p))){
            matrix[p.getRow()][p.getColumn()] = true;
        }
        
        // 2 West, 1 North
        p.setValues(position.getRow() - 1, position.getColumn() - 2);
        if(getBoard().positionExists(p) && (!getBoard().isThereAPiece(p) || isThereOpponentPiece(p))){
            matrix[p.getRow()][p.getColumn()] = true;
        }
        
        return matrix;
    }
}
