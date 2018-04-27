import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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

	/**
	 * since this is a directed graph, from is connected to to
	 * but not the other way around
	 */
	@Override
	public void insert(N to, N from, int weight) {
		if (!connections.containsKey(to)) {
			connections.put(to, new HashSet<N>());
		}
		if (!connections.containsKey(from)) {
			connections.put(from, new HashSet<N>());
		}
		connections.get(from).add(to);
		edges.put(new Edge(to, from), weight);
	}

	/**
	 * Delete node from connections map
	 * O(n^2) time
	 */
	@Override
	public void delete(N node) {
		Set<N> allConnection = connections.keySet();
		//Deleting all directed towards node
		for (N connection : allConnection) {
			if (connections.get(connection).contains(node)) {
				connections.get(connection).remove(node);
				edges.remove(new Edge(connection, node));
			}
		}
		//Deleting all directed from a
		for (N connection : connections.get(node)) {
			edges.remove(new Edge(node, connection));
		}
		connections.remove(node);
	}

	/**
	 * Checks whether two nodes are connected
	 */
	@Override
	public boolean isConnected(N to, N from) {
		return connections.get(from).contains(to);
	}

	@Override
	public int getWeight(N to, N from) {
		return edges.get(new Edge(to, from));
	}

	@Override
	public Collection<N> getConnections(N node) {
		return connections.get(node);
	}
	
	/**
	 * edge which represents the edge in the directed graph 
	 *
	 */
	private final class Edge {
		N to;
		N from;
		
		public Edge(N to, N from) {
			this.to = to;
			this.from = from;
		}
		
		@Override
		public boolean equals(final Object o) {
			if (this == o) return true;
			if (o == null) return false;
			if (getClass() != o.getClass()) return false;
			Edge other = (Edge) o;
			return (this.to == other.to && this.from == other.from);
		}
		
		@Override
		public int hashCode() {
			int result = 1;
		    result = 31 * result + to.hashCode();
		    result = 31 * result + from.hashCode();
		    return result;
		}
	}

}
