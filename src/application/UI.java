/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author João Alves
 */
public class UI {
    
    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    // My addition:
    // http://www.lihaoyi.com/post/BuildyourownCommandLinewithANSIescapecodes.html
    private static final String ANSI_BRIGHT_BLACK = "\u001B[30;1m";

    private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    private static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    private static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    private static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    // My addition:
    // http://www.lihaoyi.com/post/BuildyourownCommandLinewithANSIescapecodes.html
    private static final String ANSI_GRAY_BACKGROUND = "\u001b[48;5;243m";
    
    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public static void clearScreen() {
            System.out.print("\033[H\033[2J");
            System.out.flush();
    }
    
    public static ChessPosition readChessPosition(Scanner sc){
        try{
            String s = sc.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));

            return new ChessPosition(column, row);
        }
        catch (RuntimeException e){
            throw new InputMismatchException("Error reading ChessPosition. Values should be within the range of a1 to h8");
        }
    }
    
    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured){
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(captured);
        System.out.println();
        System.out.print("Turn: " + chessMatch.getTurn());
        if(!chessMatch.isCheckMate()){
            System.out.println(" (" + chessMatch.getCurrentPlayer() + " turn)");
            
            if(chessMatch.isCheck()){
                System.out.println("\nCHECK!");
            }
            else{
                System.out.println("\n");
            }
        }
        else{
            System.out.println("\n\nCHECKMATE!!!");
            System.out.println("Winner: " + chessMatch.getCurrentPlayer());
            System.out.println("Congratulations!");
        }
    }
    
    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves){
        System.out.println();
        for (int i=0; i<pieces.length; i++){
            System.out.print((8 - i) + "    ");
            for (int j=0; j<pieces[i].length; j++){
                printPiece(pieces [i][j], possibleMoves[i][j]);
            }
            System.out.println("");
        }
        System.out.println("\n     a  b  c  d  e  f  g  h");
    }
    
    public static void printBoard(ChessPiece[][] pieces){
        System.out.println();
        for (int i=0; i<pieces.length; i++){
            System.out.print((8 - i) + "    ");
            for (int j=0; j<pieces[i].length; j++){
                printPiece(pieces [i][j], false);
            }
            System.out.println("");
        }
        System.out.println("\n     a  b  c  d  e  f  g  h");
    }
    
    private static void printPiece(ChessPiece piece, boolean background){
        if(background){
            System.out.print(ANSI_GRAY_BACKGROUND);
        }
        if (piece == null){
            System.out.print("-" + ANSI_RESET);
        }
        else{
            if(piece.getColor() == Color.WHITE){
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else{
                System.out.print(ANSI_BRIGHT_BLACK + piece + ANSI_RESET);
            }
        }
        System.out.print("  ");
    }
    
    private static void printCapturedPieces (List<ChessPiece> captured){
        List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
        List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
        
        System.out.println("Captured pieces:");
        System.out.println("White: " + ANSI_WHITE + Arrays.toString(white.toArray()) + ANSI_RESET);
        System.out.println("Black: " + ANSI_BRIGHT_BLACK + Arrays.toString(black.toArray()) + ANSI_RESET);
    }
}
