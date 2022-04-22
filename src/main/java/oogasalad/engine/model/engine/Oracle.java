package oogasalad.engine.model.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import oogasalad.engine.model.ai.AIOracle;
import oogasalad.engine.model.board.Board;
import oogasalad.engine.model.board.Position;
import oogasalad.engine.model.rule.terminal_conditions.EndRule;
import oogasalad.engine.model.rule.Move;

/**
 * This class controls game logic, such as generation of available moves, checking rules, etc
 *
 * @author Jake Heller
 */
public class Oracle implements AIOracle {

  private Collection<Move> myMoves;
  private Collection<EndRule> myEndRules;
  private Collection<Move> myPersistentRules;

  private int myNumPlayers;

  public Oracle(Collection<Move> moves, Collection<EndRule> endRules, Collection<Move> persistentRules, int numPlayers) {
    myMoves = moves;
    myEndRules = endRules;
    myPersistentRules = persistentRules;
    myNumPlayers = numPlayers;
  }
  /**
   *
   * @param board
   */
  public Board checkForWin(Board board) {
    for (Iterator<EndRule> it = myEndRules.iterator(); it.hasNext(); ) {
      EndRule endRule = it.next();
      if(endRule.isValid(board, new Position(0,0))){
        return board.setWinner(endRule.getWinner(board));
      }
    }
    return board;
  }

  /**
   * Returns valid moves for given position and board
   * If you want to use the game's current board, you can
   * use the gameGameStateBoard() function
   *
   * Note: this returns all available moves, not specific to
   * a player
   *
   * @param board
   * @param referencePoint
   * @return
   */
  public Stream<Move> getValidMovesForPosition(Board board, Position referencePoint) {
    return myMoves.stream().filter((move) -> move.isValid(board, referencePoint));
  }

  private Stream<Choice> getValidChoicesForPosition(Board board, Position referencePoint) {
    return getValidMovesForPosition(board, referencePoint).map(move -> new Choice(referencePoint, move));
  }

  public Stream<Choice> getValidChoices(Board board) {
    return board.getPositionStatesStream().flatMap(positionState -> getValidChoicesForPosition(board, positionState.position()));
  }
  /**
   * Outer map
   * @param board
   * @return two dimensional map, where outer map key is the 'reference point' for the move, while the
   * inner map key is the 'representative point' of the move, or the
   */
  public Map<Position, Stream<Move>> getAllValidMoves(Board board) {
    Map<Position, Stream<Move>> allMoves = new HashMap<>();
    board.getPositionStatesStream().forEach(posState ->
        allMoves.put(posState.position(), getValidMovesForPosition(board, posState.position())));
    return allMoves;
  }
  /**
   * Applies persistent rules to a board
   * Should be called after a player move gets executed and before
   * the end check
   *
   * @param board
   * @return
   */
  public Board applyRules(Board board) {
    Board finalBoard = board;
    for (Move rule: myPersistentRules) {
      board = rule.doMovement(board, new Position(0,0));
    }
    return board;
  }

  /**
   *
   * @param p1 relative point
   * @param p2 representative point
   * @return
   */
  public Optional<Move> getMoveSatisfying(Board board, Position p1, Position p2) {
    Optional<Move> choice = myMoves.stream().filter(move -> move.isValid(board, p1)).filter(move -> move.getRepresentativeCell(
        p1).equals(p2)).findFirst();
    return choice;
  }

  public List<Position> getRepresentativePoints(Stream<Move> moves, Position referencePoint) {
    List<Position> positions = new ArrayList<>();
    moves.forEach((move) -> positions.add(move.getRepresentativeCell(referencePoint)));

    return positions;
  }

  public Board incrementPlayer(Board board) {
    int nextPlayer = (board.getPlayer() + 1) % myNumPlayers;
    return board.setPlayer(nextPlayer);
  }

  @Override
  public Set<Choice> getChoices(Board board, int player) {
    return null;
  }

  @Override
  public Boolean isWinningState(Board board) {
    return null;
  }
}
