package com.example;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Piece {

    private boolean color;
    private BufferedImage img;

    public Piece(boolean color, String img_file) {
        this.color = color;

        try {
            if (this.img == null) {
                String path = System.getProperty("user.dir") + "/" + img_file;
                this.img = ImageIO.read(new File(path));
            }
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public boolean getColor() {
        return color;
    }

    public Image getImage() {
        return img;
    }

    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        g.drawImage(this.img, x, y, null);
    }

    // override in subclasses
    public ArrayList<Square> getLegalMoves(Board b, Square currentSquare) {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return color ? "white" : "black";
    }

    // override in subclasses
    public ArrayList<Square> getControlledSquares(Square[][] board, Square currentSquare) {
        return new ArrayList<>();
    }
}