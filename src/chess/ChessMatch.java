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
    
    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }
    
    private void initialSetup(){
        Color black = Color.BLACK;
        Color white = Color.WHITE;
        
        for(int i = 0; i < board.getColumns(); i++){
            switch(i){
                    case 0:
                        placeNewPiece('a', 8, new Rook(board, black));
                        placeNewPiece('a', 7, new Pawn(board, black));
                        placeNewPiece('a', 2, new Pawn(board, white));
                        placeNewPiece('a', 1, new Rook(board, white));
                        break;
                    case 1:
                        placeNewPiece('b', 8, new Knight(board, black));
                        placeNewPiece('b', 7, new Pawn(board, black));
                        placeNewPiece('b', 2, new Pawn(board, white));
                        placeNewPiece('b', 1, new Knight(board, white));
                        break;
                    case 2:
                        placeNewPiece('c', 8, new Bishop(board, black));
                        placeNewPiece('c', 7, new Pawn(board, black));
                        placeNewPiece('c', 2, new Pawn(board, white));
                        placeNewPiece('c', 1, new Bishop(board, white));
                        break;
                    case 3:
                        placeNewPiece('d', 8, new King(board, black));
                        placeNewPiece('d', 7, new Pawn(board, black));
                        placeNewPiece('d', 2, new Pawn(board, white));
                        placeNewPiece('d', 1, new King(board, white));
                        break;
                    case 4:
                        placeNewPiece('e', 8, new Queen(board, black));
                        placeNewPiece('e', 7, new Pawn(board, black));
                        placeNewPiece('e', 2, new Pawn(board, white));
                        placeNewPiece('e', 1, new Queen(board, white));
                        break;
                    case 5:
                        placeNewPiece('f', 8, new Bishop(board, black));
                        placeNewPiece('f', 7, new Pawn(board, black));
                        placeNewPiece('f', 2, new Pawn(board, white));
                        placeNewPiece('f', 1, new Bishop(board, white));
                        break;
                    case 6:
                        placeNewPiece('g', 8, new Knight(board, black));
                        placeNewPiece('g', 7, new Pawn(board, black));
                        placeNewPiece('g', 2, new Pawn(board, white));
                        placeNewPiece('g', 1, new Knight(board, white));
                        break;
                    case 7:
                        placeNewPiece('h', 8, new Rook(board, black));
                        placeNewPiece('h', 7, new Pawn(board, black));
                        placeNewPiece('h', 2, new Pawn(board, white));
                        placeNewPiece('h', 1, new Rook(board, white));
                        break;
            }
        }
        //BoardException test:
        //board.placePiece(new Rook(board, Color.BLACK), new Position(0, 8));
    }

//    private void setupPiece (char type, int column){
//        switch (type){
//            case 'r':
//                board.placePiece(new Rook(board, Color.BLACK), new Position(0, column));
//                board.placePiece(new Rook(board, Color.WHITE), new Position(7, column));
//                break;
//            case 'n':
//                board.placePiece(new Knight(board, Color.BLACK), new Position(0, column));
//                board.placePiece(new Knight(board, Color.WHITE), new Position(7, column));
//                break;
//            case 'b':
//                board.placePiece(new Bishop(board, Color.BLACK), new Position(0, column));
//                board.placePiece(new Bishop(board, Color.WHITE), new Position(7, column));
//                break;
//            case 'k':
//                board.placePiece(new King(board, Color.BLACK), new Position(0, column));
//                board.placePiece(new King(board, Color.WHITE), new Position(7, column));
//                break;
//            case 'q':
//                board.placePiece(new Queen(board, Color.BLACK), new Position(0, column));
//                board.placePiece(new Queen(board, Color.WHITE), new Position(7, column));
//                break;
//            case 'p':
//                board.placePiece(new Pawn(board, Color.BLACK), new Position(1, column));
//                board.placePiece(new Pawn(board, Color.WHITE), new Position(6, column));
//                break;
//        }
//    }
}