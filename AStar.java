import java.util.*;

/**
 * abstract class for the A* algorithm made generic enough so that it
 * can be implemented to work for any problem requiring A* search by
 * implementing the abstract methods.
 *
 * @param <N> any object to use as the node for the search
 */
public class AStar<N> implements ShortestPathSearch<N> {
    private final Heuristic<N> h;
    private final Graph<N> graph;
    private int n;
    private int finalCost;

    public AStar(Graph<N> graph, Heuristic<N> h) {
        this.h = h;
        this.graph = graph;
        this.n = 0;
        this.finalCost = 0;
    }

    /**
     * The A* algorithm to find the shortest path
     *
     * @param start, the node to begin the search
     */
    @Override
    public List<N> shortestPath(N start, N goal) {
        this.n = 0;
        this.finalCost = 0;
        Set<N> closed = new HashSet<>();
        PriorityQueue<NodeAndCost> queue = new PriorityQueue<>();
        Map<N, N> cameFrom = new HashMap<>();
        queue.add(new NodeAndCost(start, this.h.getH(start)));
        Map<N, Integer> gScore = new HashMap<>();
        gScore.put(start, 0);

        while (!queue.isEmpty()) {
            NodeAndCost nac = queue.poll();
            n++;
            N curr = nac.getNode();
            if (curr.equals(goal)) {
                this.finalCost = gScore.get(curr);
                return this.reconstructPath(cameFrom, curr);
            }
            closed.add(curr);
            for (N childNode : this.graph.getConnections(curr)) {
                if (closed.contains(childNode)) continue;
                int tempGScore = gScore.get(curr) + graph.getWeight(curr, childNode);
                if (gScore.containsKey(childNode)) {
                    if (tempGScore >= gScore.get(childNode)) continue;
                }
                cameFrom.put(childNode, curr);
                gScore.put(childNode, tempGScore);
                int f = gScore.get(curr) + h.getH(childNode);
                queue.add(new NodeAndCost(childNode, f));
            }
        }
        return null;
    }

    /**
     * Reconstruct path using the cameFrom map
     *
     * @param cameFrom
     * @param curr
     * @return
     */
    public List<N> reconstructPath(Map<N, N> cameFrom, N curr) {
        List<N> totalPath = new ArrayList<>();
        totalPath.add(curr);
        while (cameFrom.containsKey(curr)) {
            curr = cameFrom.get(curr);
            totalPath.add(0, curr);
        }
        return totalPath;
    }

    @Override
    public int getFinalCost() {
        return this.finalCost;
    }

    @Override
    public int getNumExpanded() {
        return n;
    }

    /**
     * Class to be used by the priority queue in shortestPath() function
     * implements Comparable interface so the queue knows how to sort
     * instances of this class
     */
    private class NodeAndCost implements Comparable<NodeAndCost> {
        private N node;
        private int cost;

        public NodeAndCost(N node, int cost) {
            this.node = node;
            this.cost = cost;
        }

        public N getNode() {
            return this.node;
        }

        @Override
        public int compareTo(NodeAndCost ob) {
            return Integer.compare(this.cost, ob.cost);
        }
    }
}
