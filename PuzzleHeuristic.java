public class PuzzleHeuristic implements Heuristic<PuzzleState> {

    @Override
    public int getH(PuzzleState node) {
        if (node.isSolved()) return 0;
        int[][] board = node.getBoard();
        int exitCol = node.getExitCol();
        Vehicle red = node.getMainVehicle();
        int h = 1;
        for (int i = red.getCol() + red.getLength(); i <= exitCol; i++) {
            if (board[red.getRow()][i] != -1) {
                h++;
            }
        }
        return h;
    }

}
