public class BorderedPuzzleController {
    BorderedPuzzleView borderedPuzzleView;

    public BorderedPuzzleController(BorderedPuzzleView borderedPuzzleView) {
        this.borderedPuzzleView = borderedPuzzleView;
    }

    void resize(int newCellSize){
        borderedPuzzleView.updateSize(newCellSize);
    }
}
