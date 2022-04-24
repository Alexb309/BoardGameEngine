package oogasalad.engine.cheat_codes;

import java.util.Optional;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.Position;
import oogasalad.engine.model.board.PositionState;

public class RemoveRandomPlayer0Piece extends CheatCode{
  @Override
  public Board accept(Board board) {
    Optional<Position> pos = (board.getPositionStatesStream()
        .filter(e -> e.player() == 0)
        .findAny()
        .map(PositionState::position));
    if(pos.isPresent()){
      return board.removePiece(pos.get());
    }
    return board;
  }
}
