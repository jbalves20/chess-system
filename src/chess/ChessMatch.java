/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author João Alves
 */
public class ChessMatch {
    
    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;
    
    private final List<Piece> piecesOnTheBoard = new ArrayList<>();
    private final List<Piece> capturedPieces = new ArrayList<>();
    
    public ChessMatch(){
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }
    
    public int getTurn(){
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }
    
    public boolean isCheck(){
        return check;
    }

    public boolean isCheckMate() {
        return checkMate;
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece getPromoted() {
        return promoted;
    }
    
    public ChessPiece[][] getPieces(){
        ChessPiece[][] matrix = new ChessPiece[board.getRows()][board.getColumns()];
        for(int i=0; i<board.getRows(); i++){
            for(int j=0; j<board.getColumns(); j++){
                matrix[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return matrix;
    }
    
    public boolean [][] possibleMoves(ChessPosition sourcePosition){
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        
        return board.piece(position).possibleMoves();
    }
    
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        
        //validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        
        if(testCheck(currentPlayer)){
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can not put yourself in check.");
        }
        
        ChessPiece movedPiece = (ChessPiece)board.piece(target);
        
        // Special move: Promotion
        promoted = null;
        if (movedPiece instanceof Pawn){
            if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || 
                    (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)){
                promoted = (ChessPiece)board.piece(target);
                promoted = replacePromotedPiece("Q");
            }
        }
        
        check = testCheck(opponent(currentPlayer));
        
        if(testCheckMate(opponent(currentPlayer))){
            checkMate = true;
        }
        else{
            nextTurn();
        }
        
        // Special move: En passant
        if(movedPiece instanceof Pawn && 
                (target.getRow() == source.getRow() - 2 ||
                target.getRow() == source.getRow() + 2)){
            enPassantVulnerable = movedPiece;
        }
        else{
            enPassantVulnerable = null;
        }
        
        return (ChessPiece) capturedPiece;
    }
    
    public void validateSourcePosition(Position position){
        if (!board.isThereAPiece(position)){
            throw new ChessException("There is no piece at source position.");
        }
        if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()){
            throw new ChessException("This piece belongs to the opponent. Select an available piece.");
        }
        if (!board.piece(position).isThereAnyPossibleMove()){
            throw new ChessException("There is no possible move for the selected piece.");
        }
    }
    
    public void validateTargetPosition(Position source, Position target){
        if (!board.piece(source).isMovePossible(target)){
            throw new ChessException("The selected piece can not be moved to the target position.");
        }
    }
    
    public ChessPiece replacePromotedPiece(String type){
        if(promoted == null){
            throw new IllegalStateException("There is no piece to be promoted.");
        }
        if(!type.equals("R") && !type.equals("N") && !type.equals("B") && !type.equals("Q")){
            throw new InvalidParameterException("Invalid type for promotion");
        }
        
        Position pos = promoted.getChessPosition().toPosition();
        Piece p = board.removePiece(pos);
        piecesOnTheBoard.remove(p);
        
        ChessPiece newPiece = newPiece(type, promoted.getColor());
        board.placePiece(newPiece, pos);
        piecesOnTheBoard.add(newPiece);
        
        return newPiece;
    }
    
    private ChessPiece newPiece(String type, Color color){
        if (type.equals("R")) return new Rook(board, color);
        if (type.equals("N")) return new Knight(board, color);
        if (type.equals("B")) return new Bishop(board, color);
        return new Queen(board, color);
    }
    
    private Piece makeMove(Position source, Position target){
        ChessPiece p = (ChessPiece) board.removePiece(source);
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);
        
        if(capturedPiece != null){
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        
        // Special move: kingside Castling
        if (p instanceof King && target.getColumn() == source.getColumn() + 2){
            Position rookSource = new Position(source.getRow(), source.getColumn() + 3);
            Position rookTarget = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece)board.removePiece(rookSource);
            board.placePiece(rook, rookTarget);
            rook.increaseMoveCount();
        }
        // Special move: queenside Castling
        if (p instanceof King && target.getColumn() == source.getColumn() - 2){
            Position rookSource = new Position(source.getRow(), source.getColumn() - 4);
            Position rookTarget = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece)board.removePiece(rookSource);
            board.placePiece(rook, rookTarget);
            rook.increaseMoveCount();
        }
        
        // Special move: En passant
        if (p instanceof Pawn){
            if (source.getColumn() != target.getColumn() && capturedPiece == null){
                Position pawnPosition;
                if (p.getColor() == Color.WHITE){
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                }
                else{
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());
                }
                capturedPiece = board.removePiece(pawnPosition);
                capturedPieces.add(capturedPiece);
                piecesOnTheBoard.remove(capturedPiece);
            }
        }
        
        return capturedPiece;
    }
    
    private void undoMove(Position source, Position target, Piece capturedPiece){
        ChessPiece p = (ChessPiece) board.removePiece(target);
        p.decreaseMoveCount();
        board.placePiece(p, source);
        
        if(capturedPiece != null){
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
        
        // Special move: kingside Castling
        if (p instanceof King && target.getColumn() == source.getColumn() + 2){
            Position rookSource = new Position(source.getRow(), source.getColumn() + 3);
            Position rookTarget = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece)board.removePiece(rookTarget);
            board.placePiece(rook, rookSource);
            rook.decreaseMoveCount();
        }
        // Special move: queenside Castling
        if (p instanceof King && target.getColumn() == source.getColumn() - 2){
            Position rookSource = new Position(source.getRow(), source.getColumn() - 4);
            Position rookTarget = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece)board.removePiece(rookTarget);
            board.placePiece(rook, rookSource);
            rook.decreaseMoveCount();
        }
        
        // Special move: En passant
        if (p instanceof Pawn){
            if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable){
                ChessPiece pawn = (ChessPiece)board.removePiece(target);
                Position pawnPosition;
                if (p.getColor() == Color.WHITE){
                    pawnPosition = new Position(3, target.getColumn());
                }
                else{
                    pawnPosition = new Position(4, target.getColumn());
                }
                board.placePiece(pawn, pawnPosition);
            }
        }
    }
    
    private void nextTurn(){
        turn++;
        currentPlayer = currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE;
    }
    
    private Color opponent(Color color){
        return color.opposite();
    }
    
    private ChessPiece findKing(Color color){
        List<Piece> list = piecesOnTheBoard
                .stream().filter(x -> ((ChessPiece)x).getColor() == color)
                .collect(Collectors.toList());
        
        for (Piece p : list){
            if(p instanceof King){
                return (ChessPiece)p;
            }
        }
        throw new IllegalStateException("There is no " + color + " King on the board!");
    }
    
    private boolean testCheck (Color color){
        Position kingPosition = findKing(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard
                .stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color))
                .collect(Collectors.toList());
        
        for (Piece p : opponentPieces){
            boolean[][] matrix = p.possibleMoves();
            if(matrix[kingPosition.getRow()][kingPosition.getColumn()]){
                return true;
            }
        }
        return false;
    }
    
    private boolean testCheckMate (Color color){
        if(!testCheck(color)){
            return false;
        }
        
        List<Piece> opponentPieces = piecesOnTheBoard
                .stream().filter(x -> ((ChessPiece)x).getColor() == color)
                .collect(Collectors.toList());
        
        for (Piece p : opponentPieces){
            boolean[][] matrix = p.possibleMoves();
            for (int i = 0; i < matrix.length; i++){
                for (int j = 0; j < matrix[i].length; j++){
                    if(matrix[i][j]){
                        Position source = ((ChessPiece)p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece checkedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, checkedPiece);
                        if(!testCheck){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    
    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }
    
    private void initialSetup(){
        Color black = Color.BLACK;
        Color white = Color.WHITE;
        
//        // Checkmate test:
//        placeNewPiece('a', 8, new King (board, black));
//        placeNewPiece('b', 8, new Rook (board, black));
//        placeNewPiece('e', 1, new King (board, white));
//        placeNewPiece('d', 1, new Rook (board, white));
//        placeNewPiece('h', 7, new Rook (board, white));
        
        for(int i = 0; i < board.getColumns(); i++){
            switch(i){
                    case 0:
                        placeNewPiece('a', 8, new Rook(board, black));
                        placeNewPiece('a', 7, new Pawn(board, black, this));
                        placeNewPiece('a', 2, new Pawn(board, white, this));
                        placeNewPiece('a', 1, new Rook(board, white));
                        break;
                    case 1:
                        placeNewPiece('b', 8, new Knight(board, black));
                        placeNewPiece('b', 7, new Pawn(board, black, this));
                        placeNewPiece('b', 2, new Pawn(board, white, this));
                        placeNewPiece('b', 1, new Knight(board, white));
                        break;
                    case 2:
                        placeNewPiece('c', 8, new Bishop(board, black));
                        placeNewPiece('c', 7, new Pawn(board, black, this));
                        placeNewPiece('c', 2, new Pawn(board, white, this));
                        placeNewPiece('c', 1, new Bishop(board, white));
                        break;
                    case 3:
                        placeNewPiece('d', 8, new Queen(board, black));
                        placeNewPiece('d', 7, new Pawn(board, black, this));
                        placeNewPiece('d', 2, new Pawn(board, white, this));
                        placeNewPiece('d', 1, new Queen(board, white));
                        break;
                    case 4:
                        placeNewPiece('e', 8, new King(board, black, this));
                        placeNewPiece('e', 7, new Pawn(board, black, this));
                        placeNewPiece('e', 2, new Pawn(board, white, this));
                        placeNewPiece('e', 1, new King(board, white, this));
                        break;
                    case 5:
                        placeNewPiece('f', 8, new Bishop(board, black));
                        placeNewPiece('f', 7, new Pawn(board, black, this));
                        placeNewPiece('f', 2, new Pawn(board, white, this));
                        placeNewPiece('f', 1, new Bishop(board, white));
                        break;
                    case 6:
                        placeNewPiece('g', 8, new Knight(board, black));
                        placeNewPiece('g', 7, new Pawn(board, black, this));
                        placeNewPiece('g', 2, new Pawn(board, white, this));
                        placeNewPiece('g', 1, new Knight(board, white));
                        break;
                    case 7:
                        placeNewPiece('h', 8, new Rook(board, black));
                        placeNewPiece('h', 7, new Pawn(board, black, this));
                        placeNewPiece('h', 2, new Pawn(board, white, this));
                        placeNewPiece('h', 1, new Rook(board, white));
                        break;
            }
        }
        //BoardException test:
        //board.placePiece(new Rook(board, Color.BLACK), new Position(0, 8));
    }
}
