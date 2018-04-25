import java.util.Collection;

/**
 * Interface for the graph
 *
 */
public interface Graph<N> {
	/**
	 * insert node a and node b into graph with weight
	 * @param a
	 * @param b
	 * @param weight
	 */
	void insert(N a, N b, int weight);
	
	/**
	 * delete the node from the graph
	 * @param a
	 */
	void delete(N a);
	
	/**
	 * check whether the two nodes are connected
	 * @param a
	 * @param b
	 * @return
	 */
	boolean isConnected(N a, N b);
	
	/**
	 * return the weight between the connected nodes
	 * @post isConnected(a, b) == true
	 * @param a
	 * @param b
	 * @return
	 */
	int getWeight(N a, N b);
	
	/**
	 * return neighbors to the node
	 * @param node
	 * @return
	 */
	Collection<N> getConnections(N node);
}
