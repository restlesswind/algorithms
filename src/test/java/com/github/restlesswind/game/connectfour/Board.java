package com.github.restlesswind.game.connectfour;

import java.util.ArrayList;
import java.util.List;

import com.github.restlesswind.algorithms.search.Node;

public class Board extends Node {
	public static final int COLS = 7;
	public static final int ROWS = 6;
	private Player[][] board;
	private Player currentPlayer;
	private Move move = null;
	
	
	public Board(Board inBoard) {
		board = new Player[ROWS][COLS];
		for (int y = 0; y < ROWS; y++) {
			for (int x =0; x < COLS; x++) {
				board[y][x] = inBoard.getBoard()[y][x];
			}
		}
		this.currentPlayer = inBoard.currentPlayer;
	}
	
	public Board(Player player) {
		board = new Player[ROWS][COLS];
		for (int y = 0; y < ROWS; y++) {
			for (int x =0; x < COLS; x++) {
				board[y][x] = Player.EMPTY;
			}
		}
		this.currentPlayer = player;
	}
	
	
	public Player[][] getBoard() {
		return board;
	}
	

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Move getMove() {
		return move;
	}

	private static boolean isPositionValid(int row, int col) {
		return (row >= 0 && row < ROWS && col >= 0 && col < COLS);
	}
	
	private boolean playRemove(int col) {
		if (board[ROWS - 1][col] != Player.EMPTY) {
			for (int row = ROWS - 1; row >= 1; row--) {
				board[row][col] = board[row - 1][col];
				board[row - 1][col] = Player.EMPTY;
			}
			return true;
		}
		return false;
	}

	private boolean playAdd(Player color, int col) {
		for (int row = ROWS - 1; row >= 0; row--) {
			if (board[row][col] == Player.EMPTY) {
				board[row][col] = color;
				return true;
			}
		}
		return false;
	}
	
	public boolean play(Player player, Move move) {
		System.out.println("player " + player + " played " + move);
		this.move = move;
		this.currentPlayer = ConnectFour.changePlayer(player);
		switch (move.type) {
		case ADD:
			return playAdd(player, move.col);
		case REMOVE:
			return playRemove(move.col);
		}
		return false;
	}
	
	private int getMaxValue(Player color) { 
		int maxValue = 0;
		int value = 0;
		for (int row = 0; row < ROWS; row++) {
			for (int col =0; col < COLS; col++) {
				for (Direction direction: Direction.values()) {
					value = getValue(row, col, color, direction);
					if (value > maxValue) {
						maxValue = value;
					}
				}
			}
		}
		return maxValue;
	}

	public Player getWinner() {
		if (getMaxValue(Player.HUMAN) >= 4) {
			return Player.HUMAN;
		}
		
		if (getMaxValue(Player.AI) >= 4) {
			return Player.AI;
		}
		return null;
	}
	
	
	private int getValue(int row, int col, Player color, Direction direction) {
		if (color != board[row][col]) {
			return 0;
		}
		int value = 1;
		int nextRow;
		int nextCol;
		switch (direction) {
		case UR:
			nextRow = row -1;
			nextCol = col + 1;
			while (isPositionValid(nextRow, nextCol) && board[nextRow][nextCol] == color) {
				value++;
				nextRow = nextRow - 1;
				nextCol = nextCol + 1;
			}
			
			if (value != 0 && value < 4) {
				// check if the value has the potential to grow, if not, set to 0
				if (!((isPositionValid(row + 1, col - 1) && board[row + 1][col - 1] == Player.EMPTY)
						|| (isPositionValid(nextRow, nextCol) && board[nextRow][nextCol] == Player.EMPTY))) {
					value = 0;
				}
			}
			
			break;
		case R:
			nextRow = row;
			nextCol = col + 1;
			while (isPositionValid(nextRow, nextCol) && board[nextRow][nextCol] == color) {
				value++;
				nextCol = nextCol + 1;
			}
			if (value != 0 && value < 4) {
				// check if the value has the potential to grow, if not, set to 0
				if (!((isPositionValid(row, col - 1) && board[row][col - 1] == Player.EMPTY)
						|| (isPositionValid(nextRow, nextCol) && board[nextRow][nextCol] == Player.EMPTY))) {
					value = 0;
				}
			}
			
			break;
		case DR:
			nextRow = row + 1;
			nextCol = col + 1;
			while (isPositionValid(nextRow, nextCol) && board[nextRow][nextCol] == color) {
				value++;
				nextRow = nextRow + 1;
				nextCol = nextCol + 1;
			}
			if (value != 0 && value < 4) {
				// check if the value has the potential to grow, if not, set to 0
				if (!((isPositionValid(row - 1, col - 1) && board[row - 1][col - 1] == Player.EMPTY)
						|| (isPositionValid(nextRow, nextCol) && board[nextRow][nextCol] == Player.EMPTY))) {
					value = 0;
				}
			}
			break;
		case D:
			nextRow = row + 1;
			nextCol = col;
			while (isPositionValid(nextRow, nextCol) && board[nextRow][nextCol] == color) {
				value++;
				nextRow = nextRow + 1;
			}
			if (value != 0 && value < 4) {
				// check if the value has the potential to grow, if not, set to 0
				if (!((isPositionValid(row - 1, col) && board[row - 1][col] == Player.EMPTY)
						|| (isPositionValid(nextRow, nextCol) && board[nextRow][nextCol] == Player.EMPTY))) {
					value = 0;
				}
			}
			break;	
		}
		return value;
	}

	@Override
	public List<Node> getChildren() {
		List<Node> list = new ArrayList<>();
		for (int i = 0; i < COLS; i++) {
			Board child = new Board(this);
			Move move = new Move(i, MoveType.ADD);
			if (child.play(currentPlayer, move)) {
				list.add(child);
			}
		}
		return list;
	}

	@Override
	public boolean isTerminal() {
		return getWinner() != null;
	}

	@Override
	public int getHeuristicValue() {
		int selfValue = getMaxValue(Player.AI);
		int opponentValue =  getMaxValue(Player.HUMAN);
		if (selfValue >= 2) {
			selfValue = selfValue * 2;
		}
		
		if (opponentValue >= 4) {
			opponentValue = opponentValue * 2;
		}
		
		return  selfValue  - opponentValue;
	}

}
