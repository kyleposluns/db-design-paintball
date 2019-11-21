package com.kyleposluns.paintball.game;

/**
 * Visitor function object for all implementations of state.
 */
public interface StateVisitor {

  /**
   * Perform a computation on a PregameState object.
   * @param state The PregameState object.
   * @param <R> The type of the result of the computation.
   * @return The result of the computation.
   */
  <R> R visitPregameState(PregameState state);

  /**
   * Perform a computation on a GameLogicState object.
   * @param state The PregameState object.
   * @param <R> The type of the result of the computation.
   * @return The result of the computation.
   */
  <R> R visitGameLogicState(GameLogicState state);

  /**
   * Perform a computation on a PostgameState object.
   * @param state The PregameState object.
   * @param <R> The type of the result of the computation.
   * @return The result of the computation.
   */
  <R> R visitPostGameState(PostgameState state);

}
