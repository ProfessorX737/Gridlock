import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * a directed weighted graph implementation 
 *
 */
public class DirectedWeightedGraph<N> implements Graph<N> {
	private final Map<N, Set<N>> connections;
	private final Map<Edge, Integer> edges;
	
	public DirectedWeightedGraph() {
		connections = new HashMap<N, Set<N>>();
		edges = new HashMap<Edge, Integer>();
	}

	@Override
	public void insert(N a, N b, int weight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(N a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isConnected(N a, N b) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getWeight(N a, N b) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<N> getConnections(N node) {
		// TODO Auto-generated method stub
		return null;
	}

}
