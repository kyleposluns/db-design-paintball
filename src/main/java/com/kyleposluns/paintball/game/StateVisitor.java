package com.kyleposluns.paintball.game;

/**
 * Visitor function object for all implementations of state.
 * @param <R>   The type of the result of the computation.
 */
public interface StateVisitor<R> {

  /**
   * Perform a computation on a PregameState object.
   *
   * @param state The PregameState object.
   * @return The result of the computation.
   */
  R visitPregameState(PregameState state);

  /**
   * Perform a computation on a GameLogicState object.
   *
   * @param state The PregameState object.
   * @return The result of the computation.
   */
  R visitGameLogicState(GameLogicState state);

  /**
   * Perform a computation on a PostgameState object.
   *
   * @param state The PregameState object.
   * @return The result of the computation.
   */
  R visitPostGameState(PostgameState state);

}
