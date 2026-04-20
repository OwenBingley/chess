package com.example;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

// Base class for all chess pieces
public class Piece {

    private boolean color; // true = white, false = black
    private BufferedImage img; // image used to draw the piece

    // Constructor loads image and sets color
    public Piece(boolean color, String img_file) {
        this.color = color;

        try {
            // Only load image if not already loaded
            if (this.img == null) {
                String path = img_file;
                this.img = ImageIO.read(new File(path));
            }
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    // Returns piece color
    public boolean getColor() {
        return color;
    }

    // Returns image of piece
    public Image getImage() {
        return img;
    }

    // Draws the piece on the board
    public void draw(Graphics g, Square currentSquare) {
       g.drawImage(this.img, 0, 0, currentSquare.getWidth(), currentSquare.getHeight(), null);
    }

    // Default legal moves (overridden in subclasses)
    public ArrayList<Square> getLegalMoves(Board b, Square currentSquare) {
        return new ArrayList<>();
    }

    // String version of piece (just color by default)
    @Override
    public String toString() {
        return color ? "white" : "black";
    }

    // Default controlled squares (overridden in subclasses)
    public ArrayList<Square> getControlledSquares(Square[][] board, Square currentSquare) {
        return new ArrayList<>();
    }
}