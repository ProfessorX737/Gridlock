import java.util.Collection;

public interface Graph<N> {
    /**
     * @param a, an end point object
     * @param b, another end point object
     * @return the weight between the two
     */
    int getWeight(N a, N b);

    /**
     * @param node the node to get the child nodes from
     * @return a collection of objects that are connected to node
     */
    Collection<N> getConnections(N node);

}
