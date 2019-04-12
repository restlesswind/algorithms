package com.github.restlesswind.algorithms.search;

import java.util.List;
/**
 * An abstract node class to be consumed by the Alpha-Beta and the MiniMax implementation.
 * 
 * @author Huang, Jian
 * @email restlesswind@gmail.com
 *
 */
public abstract class Node {
	private int value = Integer.MIN_VALUE;
	private Node nextNode = null;
	
	public int getValue() {
		return value;
	}
	protected void setValue(int value) {
		this.value = value;
	}
	public Node getNextNode() {
		return nextNode;
	}
	protected void setNextNode(Node nextNode) {
		this.nextNode = nextNode;
	}
	
	/**
	 * Get all the possible children (next moves) of the current node.
	 * 
	 * @return a list of children nodes
	 */
	abstract public List<Node> getChildren();
	
	/**
	 * Whether the current node is a terminal node.
	 * 
	 * @return true if the current node is terminal, false otherwise
	 */
	abstract public boolean isTerminal();
	
	/**
	 * Get the heuristic value of the current node.
	 * 
	 * @return the heuristic value
	 */
	abstract public int getHeuristicValue();
}
