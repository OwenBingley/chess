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
    public static final String PICTURE_PATH = "/src/main/java/com/example/Pictures/";
    public static final String RESOURCES_AMAZON_PNG = PICTURE_PATH + "amazon.png";
    public static final String RESOURCES_BAMAZON_PNG = PICTURE_PATH + "blackAmazon.png";
    private static final String RESOURCES_WBISHOP_PNG = PICTURE_PATH + "wbishop.png";
    private static final String RESOURCES_BBISHOP_PNG = PICTURE_PATH + "bbishop.png";
    private static final String RESOURCES_WKNIGHT_PNG = PICTURE_PATH + "wknight.png";
    private static final String RESOURCES_BKNIGHT_PNG = PICTURE_PATH + "bknight.png";
    private static final String RESOURCES_WROOK_PNG = PICTURE_PATH + "wrook.png";
    private static final String RESOURCES_BROOK_PNG = PICTURE_PATH + "brook.png";
    private static final String RESOURCES_WKING_PNG = PICTURE_PATH + "wking.png";
    private static final String RESOURCES_BKING_PNG = PICTURE_PATH + "bking.png";
    private static final String RESOURCES_BQUEEN_PNG = PICTURE_PATH + "bqueen.png";
    private static final String RESOURCES_WQUEEN_PNG = PICTURE_PATH + "wqueen.png";
    private static final String RESOURCES_WPAWN_PNG = PICTURE_PATH + "wpawn.png";
    private static final String RESOURCES_BPAWN_PNG = PICTURE_PATH + "bpawn.png";


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
        

        // Amazons 
        board[7][3].put(new Piece(true,   RESOURCES_AMAZON_PNG));
        board[0][3].put(new Piece(false, RESOURCES_BAMAZON_PNG ));
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
            for(Square s: currPiece.getLegalMoves(this, sq)){
                s.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.RED));
            }
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
        for(Square [] row: board){
            for(Square s: row){
                s.setBorder(null);
            }
        }
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