import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PuzzleSolver {
    public static List<int[][]> solve(PuzzleGame game) {
        Heuristic<PuzzleState> h = new PuzzleHeuristic();
        Graph<PuzzleState> stateGraph = new TreeGraph<>();
        ShortestPathSearch<PuzzleState> search = new AStar<>(stateGraph, h);
        PuzzleGame goal = new PuzzleGame(game.getNumRows(), game.getNumCols(), game.getExitRow(), game.getExitCol());
        goal.addVehicle(false, 2, game.getExitRow(), game.getExitCol(), true);
        List<PuzzleState> states = search.shortestPath(new PuzzleState(game), new PuzzleState(goal));
        if (states == null) return null;
        List<int[][]> path = new ArrayList<>();
        for (PuzzleState state : states) {
            path.add(state.getBoard());
        }
        return path;
    }
}
