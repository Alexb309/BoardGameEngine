package oogasalad.engine.model.conditions.board_conditions;

import javafx.util.Pair;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.Piece;
import oogasalad.engine.model.board.Position;

/**
 * Condition that evaluates to true when the entire board is full of pieces
 * @author Robert Cranston
 */
public class BoardFull implements BoardCondition{

  /**
   * Checks every board cell and returns true if every cell has a piece
   * @param board current board state
   * @return true if the board has no empty spaces
   */
  @Override
  public boolean isTrue(Board board){
    for(Pair<Position, Piece> pair : board){
      if(pair.getValue() == null){
        return false;
      }
    }
    return true;
  }
}