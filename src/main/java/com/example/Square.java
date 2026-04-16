package com.example;


import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


import javax.swing.*;




//Please read the following class carefully! It represents a single chess board square and is what you'll be using
//to represent the chessboard.
@SuppressWarnings("serial")
public class Square extends JComponent {
    //a reference back to the board that stores this square.
    private Board b;
   
    //true for white, false for black.
    private final boolean color;
    //if there's a piece on the square this stores it. If there isn't this stores null.
    private Piece occupyingPiece;
   
    //if desired you can use this to retain the piece where it is but make it invisible to the user.
    //True means to display the piece. This property will be switched to false when we are dragging a piece around while choosing our next move.
    private boolean dispPiece;
   
    //the coordinates of the square.
    private int row;
    private int col;
   
   
    public Square(Board b, boolean isWhite, int row, int col) {
       
        this.b = b;
        this.color = isWhite;
        this.dispPiece = true;
        this.row = row;
        this.col = col;
       
       
        this.setBorder(BorderFactory.createEmptyBorder());
    }
   
    public boolean getColor() {
        return this.color;
    }
   
    public Piece getOccupyingPiece() {
        return occupyingPiece;
    }
   
    public boolean isOccupied() {
        return (this.occupyingPiece != null);
    }
   
    public int getRow() {
        return this.row;
    }
   
    public int getCol() {
        return this.col;
    }
   
    public void setDisplay(boolean v) {
        this.dispPiece = v;
    }
   
    public void put(Piece p) {
        this.occupyingPiece = p;
    }
   
    public Piece removePiece() {
        Piece p = this.occupyingPiece;
        this.occupyingPiece = null;
        return p;
    }


   
    /**
 * PRE-condition: g is a valid Graphics object; this Square has its color and size initialized.
 * POST-condition: draws this square as its correct color; if a piece is occupying this square
 *       and display is enabled, draws the piece on top of the square.
 */
@Override
public void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (this.color) {
        g.setColor(new Color(221, 192, 127)); // light square
    } else {
        g.setColor(new Color(101, 67, 33));   // dark square
    }

    // IMPORTANT FIX: use local coordinates (0,0)
    g.fillRect(0, 0, this.getWidth(), this.getHeight());

    if (occupyingPiece != null && dispPiece) {
        occupyingPiece.draw(g, this);
    }
}
}