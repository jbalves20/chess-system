/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

/**
 *
 * @author João Alves
 */
public enum Color {
    BLACK,
    WHITE;
    
    @Override
    public String toString(){
        return this == WHITE ? "White" : "Black";
    }
}
