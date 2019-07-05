/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

/**
 *
 * @author João Alves
 */
public class ChessMatch {
    
    Board board;
    
    public ChessMatch(){
        board = new Board(8, 8);
        initialSetup();
    }
    
    public ChessPiece[][] getPieces(){
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for(int i=0; i<board.getRows(); i++){
            for(int j=0; j<board.getColumns(); j++){
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }
    
    private void initialSetup(){
        for(int i=1; i<=board.getColumns(); i++){
            if(i == 5){
                setupPiece('q', i);
                setupPiece('q', i);
            }
            else if(i == 4){
                setupPiece('k', i);
                setupPiece('k', i);
            }
            else if(i == 1 || i == 8){
                setupPiece('r', i);
                setupPiece('r', i);
            }
            else if(i == 2 || i == 7){
                setupPiece('n', i);
                setupPiece('n', i);
            }
            else if(i == 3 || i == 6){
                setupPiece('b', i);
                setupPiece('b', i);
            }
            
            setupPiece('p', i);
            setupPiece('p', i);
        }
    }

    
    private void setupPiece (char type, int column){
        switch (type){
            case 'r':
                board.placePiece(new Rook(board, Color.BLACK), new Position(1, column));
                board.placePiece(new Rook(board, Color.WHITE), new Position(8, column));
                break;
            case 'n':
                board.placePiece(new Knight(board, Color.BLACK), new Position(1, column));
                board.placePiece(new Knight(board, Color.WHITE), new Position(8, column));
                break;
            case 'b':
                board.placePiece(new Bishop(board, Color.BLACK), new Position(1, column));
                board.placePiece(new Bishop(board, Color.WHITE), new Position(8, column));
                break;
            case 'k':
                board.placePiece(new King(board, Color.BLACK), new Position(1, column));
                board.placePiece(new King(board, Color.WHITE), new Position(8, column));
                break;
            case 'q':
                board.placePiece(new Queen(board, Color.BLACK), new Position(1, column));
                board.placePiece(new Queen(board, Color.WHITE), new Position(8, column));
                break;
            case 'p':
                board.placePiece(new Pawn(board, Color.BLACK), new Position(2, column));
                board.placePiece(new Pawn(board, Color.WHITE), new Position(7, column));
                break;
        }
    }
}
