package com.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.*;

//You will be implementing a part of a function and a whole function in this document. Please follow the directions for the
//suggested order of completion that should make testing easier.
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
    // Logical and graphical representations of board
    private final Square[][] board;
    private final GameWindow g;

    //contains true if it's white's turn.
    private boolean whiteTurn;

    //if the player is currently dragging a piece this variable contains it.
    Piece currPiece;
    private Square fromMoveSquare;

    //used to keep track of the x/y coordinates of the mouse.
    private int currX;
    private int currY;

    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        //TO BE IMPLEMENTED FIRST
        //populate the board with squares here. Note that the board is composed of 64 squares alternating from
        //white to black.
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boolean isWhiteSquare = (row + col) % 2 == 0;
                board[row][col] = new Square(this, isWhiteSquare, row, col);
                this.add(board[row][col]);
            }
        }

        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;
    }

    //set up the board such that the black pieces are on one side and the white pieces are on the other.
    //since we only have one kind of piece for now you need only set the same number of pieces on either side.
    //it's up to you how you wish to arrange your pieces.
    void initializePieces() {
        

        // Amazons (one black, one white)
        board[7][3].put(new Piece(true, "src/main/java/com/example/Pictures/amazon.png"));
        board[0][3].put(new Piece(false, "Pictures/amazon.png"));
    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[x][y];
                if (sq == fromMoveSquare)
                    sq.setBorder(BorderFactory.createLineBorder(Color.blue));
                sq.paintComponent(g);
            }
        }

        if (currPiece != null) {
            final Image img = currPiece.getImage();
            g.drawImage(img, currX - 24, currY - 24, 48, 48, null);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied()) {
            currPiece = sq.getOccupyingPiece();
            fromMoveSquare = sq;
            if (currPiece.getColor() != whiteTurn)
                return;
            sq.setDisplay(false);
        }
        repaint();
    }

    //should move the piece to the desired location only if this is a legal move.
    //use the pieces "legal move" function to determine if this move is legal, then complete it by
    //moving the new piece to its new board location.
    @Override
    public void mouseReleased(MouseEvent e) {
        if (currPiece == null) return;

        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
        ArrayList<Square> legal = currPiece.getLegalMoves(this, fromMoveSquare);

        if (endSquare != null && legal.contains(endSquare)) {
            // Move piece
            endSquare.put(currPiece);
            fromMoveSquare.put(null);
            whiteTurn = !whiteTurn;
        } else {
            fromMoveSquare.setDisplay(true);
        }

        currPiece = null;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();
        repaint();
    }

    @Override public void mouseMoved(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}