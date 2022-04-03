package oogasalad.engine.view;

import oogasalad.engine.controller.Controller;

public class ViewManager {

  public ViewManager() {
  }

  public GameView createGameView(BoardView board, Controller controller) {
    GameView view = new GameView(board, controller);
    return view;
  }

}