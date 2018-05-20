import java.util.List;

public interface ShortestPathSearch<N> {
    /**
     * contains the algorithm to find the shortest path
     *
     * @param start, the node to start the search from
     * @return the last node of the shortest path
     * @post the number of nodes expanded counter is set to zero
     * before the search begins
     */
    List<N> shortestPath(N start, N goal);

    /**
     * @return the number of nodes expanded during the search
     * returns 0 if the search has not been done yet
     */
    int getNumExpanded();

    int getFinalCost();
}
