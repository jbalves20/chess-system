/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

/**
 *
 * @author João Alves
 */
public class Pawn extends ChessPiece {
    
    private ChessMatch chessMatch;
    
    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
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
            // Special move: white En passant
            if (position.getRow() == 3){
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(left) && isThereOpponentPiece(left) &&
                        getBoard().piece(left) == chessMatch.getEnPassantVulnerable()){
                    matrix[left.getRow() - 1][left.getColumn()] = true;
                }
                Position right = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExists(right) && isThereOpponentPiece(right) &&
                        getBoard().piece(right) == chessMatch.getEnPassantVulnerable()){
                    matrix[right.getRow() - 1][right.getColumn()] = true;
                }
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
            
            // Special move: black En passant
            if (position.getRow() == 4){
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(left) && isThereOpponentPiece(left) &&
                        getBoard().piece(left) == chessMatch.getEnPassantVulnerable()){
                    matrix[left.getRow() + 1][left.getColumn()] = true;
                }
                Position right = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExists(right) && isThereOpponentPiece(right) &&
                        getBoard().piece(right) == chessMatch.getEnPassantVulnerable()){
                    matrix[right.getRow() + 1][right.getColumn()] = true;
                }
            }
        }
        
        return matrix;
    }
}
