// owen bingley
// amazon
// The Amazon is a chess piece that combines the moves of a Queen and a Knight.
// this class defines the Amazon piece, including its movement and control patterns on the chessboard. It extends the Piece class and implements the necessary methods to determine the squares it controls and the legal moves it can make based on the rules of chess.
package com.example;

import java.awt.Graphics;
import java.util.ArrayList;
// pre condition: board and square classes exist, piece class exists with getColor() method, and isWhite boolean field
// post condition: returns list of squares this piece controls and legal moves it can make, and string representation of the piece
// Amazon = Queen + Knight combined
public class Amazon extends Piece {

// pre condition: isWhite is true for white pieces, false for black pieces, img_file is the filename of the piece's image
   // post condition: creates an Amazon piece with the specified color and image
// Constructor passes color and image to parent Piece class
    public Amazon(boolean isWhite, String img_file) {
        super(isWhite, img_file);
    }

 // pre condition: board is an 8x8 array of Square objects, start is the Square where this piece is located
 // post condition: returns an ArrayList of Squares that this piece controls (can move to or attack), ignoring friendly/enemy rules
    // Returns all squares this piece CONTROLS (ignores friendly/enemy rules)
    @Override
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {

        ArrayList<Square> control = new ArrayList<>();

        // Get starting row and column
        int r = start.getRow();
        int c = start.getCol();

        // All 8 possible knight moves
        int[][] knight = {
            {-2,-1},{-2,1},{-1,-2},{-1,2},
            {1,-2},{1,2},{2,-1},{2,1}
        };

        // Loop through knight moves
        for (int[] k : knight) {
            int rr = r + k[0];
            int cc = c + k[1];

            // Make sure move stays on board
            if (rr >= 0 && rr < 8 && cc >= 0 && cc < 8) {
                control.add(board[rr][cc]); // add square to controlled list
            }
        }

        // Directions for queen movement (rook + bishop)
        int[][] dirs = {
            {1,0},{-1,0},{0,1},{0,-1}, // vertical + horizontal
            {1,1},{1,-1},{-1,1},{-1,-1} // diagonals
        };

        // Loop through each direction
        for (int[] d : dirs) {
            int rr = r + d[0];
            int cc = c + d[1];

            // Keep moving in that direction until edge of board
            while (rr >= 0 && rr < 8 && cc >= 0 && cc < 8) {
                control.add(board[rr][cc]);

                // Stop if a piece blocks further movement
                if (board[rr][cc].isOccupied()) break;

                rr += d[0];
                cc += d[1];
            }
        }

        return control;
    }
// pre condition: board is an 8x8 array of Square objects, start is the Square where this piece is located
// post condition: returns an ArrayList of Squares that this piece can legally move to (can't capture own pieces, but can capture enemy pieces)
    // Returns LEGAL moves (can't capture own pieces)
    @Override
    public ArrayList<Square> getLegalMoves(Board b, Square start) {

        ArrayList<Square> moves = new ArrayList<>();
        Square[][] board = b.getSquareArray();

        int r = start.getRow();
        int c = start.getCol();

        // Knight movement
        int[][] knight = {
            {-2,-1},{-2,1},{-1,-2},{-1,2},
            {1,-2},{1,2},{2,-1},{2,1}
        };

        for (int[] k : knight) {
            int rr = r + k[0];
            int cc = c + k[1];

            if (rr >= 0 && rr < 8 && cc >= 0 && cc < 8) {
                Square sq = board[rr][cc];

                // Add if empty OR enemy piece
                if (!sq.isOccupied() ||
                    sq.getOccupyingPiece().getColor() != this.getColor()) {
                    moves.add(sq);
                }
            }
        }

        // Queen-like movement directions
        int[][] dirs = {
            {1,0},{-1,0},{0,1},{0,-1},
            {1,1},{1,-1},{-1,1},{-1,-1}
        };

        for (int[] d : dirs) {
            int rr = r + d[0];
            int cc = c + d[1];

            while (rr >= 0 && rr < 8 && cc >= 0 && cc < 8) {

                Square sq = board[rr][cc];

                // If square is empty → valid move
                if (!sq.isOccupied()) {
                    moves.add(sq);
                } else {
                    // If enemy piece → can capture
                    if (sq.getOccupyingPiece().getColor() != this.getColor()) {
                        moves.add(sq);
                    }
                    break; // stop after hitting any piece
                }

                rr += d[0];
                cc += d[1];
            }
        }

        return moves;
    }
// pre condition: none
// post condition: returns a string representation of the piece, indicating its color and type (Amazon
    // String representation of the piece
    @Override
    public String toString() {
        return this.getColor() ? "White Amazon" : "Black Amazon";
    }
}