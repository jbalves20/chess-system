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
public class Board {
    
    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
        if(rows < 1 || columns < 1){
            throw new BoardException("Error creating board: there must be at leat 1 row and 1 column at the board");
        }
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
    
    public Piece piece(int row, int column){
        if(!positionExists(row, column)){
            throw new BoardException("There is no such position on the board");
        }
        return pieces[row][column];
    }
    
    public Piece piece(Position position){
        if(!positionExists(position)){
            throw new BoardException("There is no such position on the board");
        }
        return pieces[position.getRow()][position.getColumn()];
    }
    
    public void placePiece(Piece piece, Position position){
        if(isThereAPiece(position)){
            throw new BoardException("There is already a piece on position " + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }
    
    public Piece removePiece(Position position){
        if(!positionExists(position)){
            throw new BoardException("There is no such position on the board");
        }
        if (piece(position) == null){
            return null;
        }
        Piece aux = piece(position);
        aux.position = null;
        pieces[position.getRow()][position.getColumn()] = null;
        return aux;
    }
    
    public boolean positionExists (int row, int column){
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }
    
    public boolean positionExists (Position position){
        return positionExists(position.getRow(), position.getColumn());
    }
    
    public boolean isThereAPiece (Position position){
        if(!positionExists(position)){
            throw new BoardException("There is no such position on the board");
        }
        return piece(position) != null;
    }
}
