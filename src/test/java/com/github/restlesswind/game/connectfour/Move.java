package com.github.restlesswind.game.connectfour;

public class Move {
	int col = 0;
	MoveType type = MoveType.ADD;
	
	Move (int col, MoveType type) {
		this.col = col;
		this.type = type;
	}

	@Override
	public String toString() {
		return "Move [col=" + col + ", type=" + type + "]";
	}
}
