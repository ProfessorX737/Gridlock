import java.util.Collection;

/*
 * This class is used as the super class for nodes in the TreeGraph
 */
public interface TreeNode<N> {
	/**
	 * @return the weight from this node to @param to node
	 */
	int getWeightTo(N to);

	/**
	 * @return a set of all nodes connected to this node
	 */
	Collection<N> getConnections();
}
