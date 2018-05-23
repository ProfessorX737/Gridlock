public interface Heuristic<N> {
	/**
	 * @param node, the node from which to calculate the heuristic 
	 * @return the heuristic value of the node
	 */
	int getH(N node);
}
