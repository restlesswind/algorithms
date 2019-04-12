package com.github.restlesswind.algorithms.search;
/**
 * An implementation of Alpha-Beta Pruning algorithm 
 * 
 * @author Huang, Jian
 * @email restlesswind@gmail.com
 *
 */
public class AlphaBeta {
	public static void runAlphaBeta(Node node, int depth, int alpha, int beta, boolean isMaximizing) {
		if (depth == 0 || node.isTerminal()) {
			node.setValue(node.getHeuristicValue());
			node.setNextNode(null);
			return;
		}
		if (isMaximizing) {
			node.setValue(Integer.MIN_VALUE);
			for (Node child: node.getChildren()) {
				runAlphaBeta(child, depth - 1, alpha, beta, !isMaximizing);
				if (child.getValue() > node.getValue()) {
					node.setValue(child.getValue());
					node.setNextNode(child);
				}
				alpha = Math.max(alpha, node.getValue());
				if (alpha >= beta) {
					break;
				}
			} 
		} else {
			node.setValue(Integer.MAX_VALUE);
			for (Node child: node.getChildren()) {
				runAlphaBeta(child, depth - 1, alpha, beta, !isMaximizing);
				if (child.getValue() < node.getValue()) {
					node.setValue(child.getValue());
					node.setNextNode(child);
				}
				beta = Math.min(beta, node.getValue());
				if (alpha >= beta) {
					break;
				}
			} 
		}
	}
}
