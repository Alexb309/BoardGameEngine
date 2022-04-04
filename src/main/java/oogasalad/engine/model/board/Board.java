package oogasalad.engine.model.board;

import java.util.Iterator;
import java.util.Optional;
import javafx.util.Pair;
import oogasalad.engine.model.Observable;
import oogasalad.engine.model.OutOfBoardException;
import oogasalad.engine.model.Piece;
import oogasalad.engine.model.Utilities;

public class Board extends Observable<Piece[][]> implements Iterable<Pair<Position, Piece>> {

  private int myRows;
  private int myColumns;
  private Piece[][] pieceLocations;

  public Board(int rows, int columns) {
    myRows = rows;
    myColumns = columns;
    pieceLocations = new Piece[rows][columns];
  }

  public Piece[][] getMyBoard(){
    return pieceLocations;
  }

  /**
   * returns true if there is a piece at location Board[row][column]. else, false
   * @param row
   * @param column
   * @return
   */
  private boolean isPieceAtLocation(int row, int column){
    return pieceLocations[row][column] != null;

  }
  public void placeNewPiece(int row, int column, int type, int player) throws OutOfBoardException {
    Piece piece = new Piece(type, player, row, column);
    place(row, column, piece);
  }

  private void place(int i, int j, Piece piece) throws OutOfBoardException {
    if(i <= myRows && j <= myColumns){
      if (piece != null) {
        piece.movePiece(i, j);
      }
      pieceLocations[i][j] = piece;
    }
    else{
      throw new OutOfBoardException("Piece out of Board");
    }
  }

  public void remove(int i, int j){
    pieceLocations[i][j] =null;
  }

  public Optional<Piece> getPiece(int i, int j) {
    //return Optional.of(myBoard[i][j]);
    Optional<Piece> piece;
    if (pieceLocations[i][j] == null) {
      piece = Optional.empty();
    }
    else {
      piece = Optional.of(pieceLocations[i][j]);
    }
    return piece;
  }


  /**
   * If piece exists at (i1, j1), moves that piece
   * to (i2, j2)
   * @param i1
   * @param j1
   * @param i2
   * @param j2
   * @throws OutOfBoardException
   */
  public void move(int i1, int j1, int i2, int j2) throws OutOfBoardException {
    if (isPieceAtLocation(i1,j1)){
      Piece piece = pieceLocations[i1][j1];
      place(i2, j2, piece);
      pieceLocations[i1][j1] = null;
    }
  }

  public Boolean isValid(Position position){
    return isValidX(position.getI()) && isValidY(position.getJ());
  }

  private boolean isValidY(int j) {
    return Utilities.isPositive(j) && (j <= myRows);
  }

  private boolean isValidX(int i) {
    return Utilities.isPositive(i) && (i <= myColumns);
  }

  public Board deepCopy() throws OutOfBoardException {
    Board board = new Board(myRows, myColumns);
    for (Pair<Position, Piece> pair: this) {
      Piece copyPiece;
      if (pair.getValue() != null) {
        Piece piece = pair.getValue();
        board.placeNewPiece(piece.getI(), piece.getJ(), piece.getType(), piece.getOwner());
      }
    }
    return board;
  }

  // Let's discuss, I think we should use the Java Streams class to create a Stream over the board declaratively, because:
// 1. We can use built in functionality for streams
// 2. Very easy to make code parallel/concurrent
// 3. Open-Closed -> we won't have to change implemenation if we decide to change how to represent Board because it will still be a Stream
  @Override
  public Iterator<Pair<Position, Piece>> iterator() {
    return new BoardIterator(pieceLocations);
  }
}
