import java.util.List;

public class TreeGraph<N extends TreeNode<N>> implements Graph<N> {
	
	public TreeGraph() {}

	@Override
	public int getWeight(N a, N b) {
		return a.getWeightTo(b);
	}

	@Override
	public List<N> getConnections(N node) {
		return (List<N>) node.getConnections();
	}

}
