

//Ryan Kim
//Piece name: King
//The king moves one square in every direction.
package com.example;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
//you will need to implement two functions in this file.
public class King extends Piece{
public King(boolean isWhite, String img_file) {
super(isWhite, img_file);
}
//precondition: g and currentSquare must be on-null valid objects.
//postcondition: the image stored in the img property of this object is drawn
//to the screen.

// TO BE IMPLEMENTED!
//return a list of every square that is "controlled" by this piece. A square is
//controlled
//if the piece capture into it legally.
//precondition: board is set up properly, start is within range of the board,
//board is 8x8
//postcondition: return all possible moves the piece can move to, ignoring if
//the square is occupied or not.
public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
ArrayList<Square> controlledSquare = new ArrayList<Square>();
if (start.getCol()<7) {
Square right = board[start.getRow()][start.getCol() + 1];
controlledSquare.add(right);
}
if (start.getCol()>0) {
Square left = board[start.getRow()][start.getCol() - 1];
controlledSquare.add(left);
}
if (start.getRow()>0) {
Square up = board[start.getRow() - 1][start.getCol()];
controlledSquare.add(up);
}
if (start.getRow()<7) {
Square down = board[start.getRow() + 1][start.getCol()];
controlledSquare.add(down);
}
if (start.getCol()<7 && start.getRow()>0) {
Square upRight = board[start.getRow() - 1][start.getCol() + 1];
controlledSquare.add(upRight);
}
if (start.getCol()>0 && start.getRow()>0) {
Square upLeft = board[start.getRow() - 1][start.getCol() - 1];
controlledSquare.add(upLeft);
}
if (start.getCol()<7 && start.getRow()<7) {
Square downRight = board[start.getRow() - 1][start.getCol() - 1];
controlledSquare.add(downRight);
}
if (start.getCol()>0 && start.getRow()<7) {
Square downLeft = board[start.getRow() - 1][start.getCol() - 1];
controlledSquare.add(downLeft);
}
return controlledSquare;
}
//TO BE IMPLEMENTED!
//implement the move function here
//it's up to you how the piece moves, but at the very least the rules should be
//logical and it should never move off the board!
//returns an arraylist of squares which are legal to move to
//please note that your piece must have some sort of logic. Just being able to
//move to every square on the board is not
//going to score any points.
//The king moves one square in every direction.
//precondition: board is set up properly, start is within range of the board,
//board is 8x8
//postcondition: return all possible legal moves the piece can move to,
//checking if the square is occupied or not
public ArrayList<Square> getLegalMoves(Board b, Square start){
ArrayList<Square> moves = new ArrayList<Square>();
if(start.getCol()<7){
Square right = b.getSquareArray()[start.getRow()][start.getCol()+1];
if(right.isOccupied()==false){
moves.add(right);
}
else if(right.getOccupyingPiece().getColor()!=getColor()){
moves.add(right);
}
}
if(start.getCol()>0){
Square left = b.getSquareArray()[start.getRow()][start.getCol()-1];
if(left.isOccupied()==false){
moves.add(left);
}
else if(left.getOccupyingPiece().getColor()!=getColor()){
moves.add(left);
}
}
if(start.getRow()<7){
Square down = b.getSquareArray()[start.getRow()+1][start.getCol()];
if(down.isOccupied()==false){
moves.add(down);
}
else if(down.getOccupyingPiece().getColor()!=getColor()){
moves.add(down);
}
}
if(start.getRow()>0){
Square up = b.getSquareArray()[start.getRow()-1][start.getCol()];
if(up.isOccupied()==false){
moves.add(up);
}
else if(up.getOccupyingPiece().getColor()!=getColor()){
moves.add(up);
}
}
if(start.getCol()<7 && start.getRow()>0){
Square upRight = b.getSquareArray()[start.getRow()-1][start.getCol()+1];
if(upRight.isOccupied()==false){
moves.add(upRight);
}
else if(upRight.getOccupyingPiece().getColor()!=getColor()){
moves.add(upRight);
}
}
if(start.getCol()>0 && start.getRow()>0){
Square upLeft = b.getSquareArray()[start.getRow()-1][start.getCol()-1];
if(upLeft.isOccupied()==false){
moves.add(upLeft);
}
else if(upLeft.getOccupyingPiece().getColor()!=getColor()){
moves.add(upLeft);
}
}
if(start.getCol()>0 && start.getRow()<7){
Square downLeft = b.getSquareArray()[start.getRow()+1][start.getCol()-1];
if(downLeft.isOccupied()==false){
moves.add(downLeft);
}
else if(downLeft.getOccupyingPiece().getColor()!=getColor()){
moves.add(downLeft);
}
}
if(start.getCol()<7 && start.getRow()<7){
Square downRight = b.getSquareArray()[start.getRow()+1][start.getCol()+1];
if(downRight.isOccupied()==false){
moves.add(downRight);
}
else if(downRight.getOccupyingPiece().getColor()!=getColor()){
moves.add(downRight);
}
}
return moves;
}
public String toString(){
return "A " + super.toString() + " King";
}
}
