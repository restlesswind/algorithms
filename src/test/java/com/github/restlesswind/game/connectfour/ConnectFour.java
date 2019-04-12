package com.github.restlesswind.game.connectfour;

import com.github.restlesswind.algorithms.search.AlphaBeta;

public class ConnectFour {
	public final static int cols = 7;
	public final static int rows = 6;
	public final static int WHITE = 0;
	public final static int RED = 1;
	public final static int YELLOW = 2;
	private View view;
	
	private Player currentPlayer = Player.HUMAN;

	Board board;

	public ConnectFour() {
		board = new Board(currentPlayer);
		view = new View(this);
	}

	private void changePlayer() {
		currentPlayer = changePlayer(currentPlayer);
		if (currentPlayer == Player.AI) {
			Move move = getBestAIMove();
			System.out.println("AI will play: " + move);
			play(move.col, move.type);
		}
	}
	
	public static Player changePlayer(Player inPlayer) {
		if (inPlayer == Player.HUMAN) {
			return Player.AI;
		} else if (inPlayer == Player.AI) {
			return Player.HUMAN;
		}
		return null;
	}
	
	public void play(int col, MoveType type) {
		board.play(currentPlayer, new Move(col, type));
		view.redraw(board.getBoard());
		view.repaint();
		Player winner = board.getWinner();
		if (winner != null) {
			System.out.println("Game over! winner is " + winner);
			return;
		}
		changePlayer();
	}
	
	private Move getBestAIMove() {
		int depth = 5;
		AlphaBeta.runAlphaBeta(board, depth, -10, 10, true);
		Board nextBoard = (Board) board.getNextNode();
		if (nextBoard != null) {
			return nextBoard.getMove();
		}
		return null;
	}

	
	public static void main(String[] args) {
		ConnectFour game = new ConnectFour();
	}
}
