package com.github.restlesswind.algorithms.search;
/**
 * An implementation of MiniMax algorithm 
 * 
 * @author Huang, Jian
 * @email restlesswind@gmail.com
 *
 */
public class MiniMax {
	public static void runMiniMax(Node node, int depth, boolean isMaximizing) {
		if (depth == 0 || node.isTerminal()) {
			node.setValue(node.getHeuristicValue());
			node.setNextNode(null);
			return;
		}
		if (isMaximizing) {
			node.setValue(Integer.MIN_VALUE);
			for (Node child: node.getChildren()) {
				runMiniMax(child, depth - 1, !isMaximizing);
				if (child.getValue() > node.getValue()) {
					node.setValue(child.getValue());
					node.setNextNode(child);
				}
			} 
		} else {
			node.setValue(Integer.MAX_VALUE);
			for (Node child: node.getChildren()) {
				runMiniMax(child, depth - 1, !isMaximizing);
				if (child.getValue() < node.getValue()) {
					node.setValue(child.getValue());
					node.setNextNode(child);
				}
			} 
		}
	}
}
