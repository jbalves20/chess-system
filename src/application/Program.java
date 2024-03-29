/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author João Alves
 */
public class Program {
    public static void main (String[] args){
        
        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();
        
        while(!chessMatch.isCheckMate()){
            try{
                UI.clearScreen();
                UI.printMatch(chessMatch, captured);
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(sc);

                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);
                
                System.out.println("\n");
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(sc);

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
                
                if (capturedPiece != null){
                    captured.add(capturedPiece);
                }
                
                if (chessMatch.getPromoted() != null){
                    boolean aux = false;
                    while(!aux){
                        try{
                            System.out.println("\nEnter piece for Promotion (R/N/B/Q)");
                            String type = sc.nextLine().toUpperCase();
                            chessMatch.replacePromotedPiece(type);
                            aux = true;
                        }
                        catch(RuntimeException e){
                            System.out.print("\n" + e.getMessage() + ". ");
                        }
                    }
                }
            }
            catch (ChessException e){
                System.out.println("\n" + e.getMessage());
                sc.nextLine();
            }
            catch (InputMismatchException e){
                System.out.println("\n" + e.getMessage());
                sc.nextLine();
            }
        }
        UI.clearScreen();
        UI.printMatch(chessMatch, captured);
    }
}
