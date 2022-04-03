package oogasalad.engine.model.action;

import oogasalad.engine.model.Game;
import oogasalad.engine.model.board.Board;

public interface Action {

  public void execute(Game game);

  public Board getNextState();
}