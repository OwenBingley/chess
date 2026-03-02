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

    /**
     * PRE-condition: isWhite is true for white, false for black.
     *      img_file is a valid path to an image file.
     * POST-condition: Initializes a Piece with its color and loads its image into memory.
     */
    public Piece(boolean isWhite, String img_file) {
        this.color = isWhite;

        try {
            if (this.img == null) {
                this.img = ImageIO.read(new File(System.getProperty("user.dir") + "/" + img_file));
            }
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    /**
     * PRE-condition: none
     * POST-condition: returns true if this piece is white, false if black.
     */
    public boolean getColor() {
        return color;
    }

    /**
     * PRE-condition: image was loaded correctly in constructor.
     * POST-condition: returns the Image object for this piece.
     */
    public Image getImage() {
        return img;
    }

    /**
     * PRE-condition: g is a valid Graphics object; currentSquare is not null.
     * POST-condition: draws this piece's image scaled to fit the given square.
     */
    public void draw(Graphics g, Square currentSquare) {
        int x = 0;
        int y = 0;
        g.drawImage(this.img, x, y, currentSquare.getWidth(), currentSquare.getHeight(), null);
    }

    /**
     * PRE-condition: board is an 8x8 array of Squares, start is a valid Square containing this piece.
     * POST-condition: returns all squares this piece attacks (Amazon = Queen + Knight control).
     */
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

        // Queen sliding moves
        int[][] directions = {
            {1,0},{-1,0},{0,1},{0,-1},
            {1,1},{1,-1},{-1,1},{-1,-1}
        };
        for (int[] d : directions) {
            int rr = r + d[0];
            int cc = c + d[1];
            while (rr >= 0 && rr < 8 && cc >= 0 && cc < 8) {
                control.add(board[rr][cc]);
                if (board[rr][cc].isOccupied()) break;
                rr += d[0];
                cc += d[1];
            }
        }

        return control;
    }

    /**
     * PRE-condition: b is a valid Board; start is the square this piece is on.
     * POST-condition: returns all legal moves (Amazon: Queen + Knight) that stay on board
     *       and do NOT land on a friendly piece.
     */
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

        // Queen moves
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
                        moves.add(sq);
                    break;
                }
                rr += d[0];
                cc += d[1];
            }
        }

        return moves;
    }
}