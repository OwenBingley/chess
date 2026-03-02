package com.example;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Piece {
    private final boolean color;
    private BufferedImage img;

    // Constructor for a piece. Loads the image file
    public Piece(boolean isWhite, String img_file) {
        this.color = isWhite;

        try {
            if (this.img == null) {
                // Load image from the project folder
                this.img = ImageIO.read(new File(System.getProperty("user.dir") + "/" + img_file));
            }
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    // Returns true for white, false for black
    public boolean getColor() {
        return color;
    }

    // Returns the image of this piece
    public Image getImage() {
        return img;
    }

    // Draw the piece onto a square
    public void draw(Graphics g, Square currentSquare) {
        int x = 0; // draw from top-left of the square
        int y = 0;
        g.drawImage(this.img, x, y, currentSquare.getWidth(), currentSquare.getHeight(), null);
    }

    // TO BE IMPLEMENTED!
    // Return a list of squares this piece controls
    // The Amazon controls like a Queen + Knight
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> control = new ArrayList<>();
        int r = start.getRow();
        int c = start.getCol();

        // Knight moves
        int[][] knightOffsets = {
            {-2, -1}, {-2, 1}, {2, -1}, {2, 1},
            {-1, -2}, {-1, 2}, {1, -2}, {1, 2}
        };
        for (int[] off : knightOffsets) {
            int rr = r + off[0];
            int cc = c + off[1];
            if (rr >= 0 && rr < 8 && cc >= 0 && cc < 8)
                control.add(board[rr][cc]);
        }

        // Queen-like sliding moves
        int[][] directions = {
            {1,0},{-1,0},{0,1},{0,-1},
            {1,1},{1,-1},{-1,1},{-1,-1}
        };
        for (int[] d : directions) {
            int rr = r + d[0];
            int cc = c + d[1];
            while (rr >= 0 && rr < 8 && cc >= 0 && cc < 8) {
                control.add(board[rr][cc]);
                if (board[rr][cc].isOccupied()) break; // blocked beyond
                rr += d[0];
                cc += d[1];
            }
        }

        return control;
    }

    
    // Returns all legal moves for the Amazon
    // Rules: Moves like Knight or Queen, cannot move off the board, cannot capture own pieces
    public ArrayList<Square> getLegalMoves(Board b, Square start){
        ArrayList<Square> moves = new ArrayList<>();
        Square[][] board = b.getSquareArray();
        int r = start.getRow();
        int c = start.getCol();

        // Knight 
        int[][] knightOffsets = {
            {-2, -1}, {-2, 1}, {2, -1}, {2, 1},
            {-1, -2}, {-1, 2}, {1, -2}, {1, 2}
        };
        for (int[] off : knightOffsets) {
            int rr = r + off[0];
            int cc = c + off[1];
            if (rr >= 0 && rr < 8 && cc >= 0 && cc < 8) {
                Square sq = board[rr][cc];
                if (!sq.isOccupied() || sq.getOccupyingPiece().getColor() != color) {
                    moves.add(sq);
                }
            }
        }

        // Queen
        int[][] directions = {
            {1,0},{-1,0},{0,1},{0,-1},
            {1,1},{1,-1},{-1,1},{-1,-1}
        };
        for (int[] d : directions) {
            int rr = r + d[0];
            int cc = c + d[1];
            while (rr >= 0 && rr < 8 && cc >= 0 && cc < 8) {
                Square sq = board[rr][cc];
                if (!sq.isOccupied()) {
                    moves.add(sq);
                } else {
                    if (sq.getOccupyingPiece().getColor() != color)
                        moves.add(sq); // capture
                    break; // blocked
                }
                rr += d[0];
                cc += d[1];
            }
        }

        return moves;
    }
}