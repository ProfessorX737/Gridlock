import java.awt.Color;
import java.util.List;

public class LevelEditorImplementation implements LevelEditor{
	PuzzleGame puzzle;
	
	public LevelEditorImplementation() {
	}

	@Override
	public PuzzleGame exportLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuzzleGame loadSavedLevel(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveLevel(String filename) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSolvable() {
		List<int[][]>puzzleSolved = PuzzleSolver.solve(this.puzzle);
		if (puzzleSolved == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean canAddVehicle(Vehicle v) {
		return false;
	}

	@Override
	public boolean canAddVehicle(boolean isVertical, int length, int row, int col) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addVehicle(Vehicle v) {
		puzzle.addVehicle(v);
		
	}

	@Override
	public void addVehicle(boolean isVertical, int length, int row, int col, Color color) {
		puzzle.addVehicle(isVertical, length, row, col, color);
		
	}

	@Override
	public void removeVehicleAtLocation(int row, int col) {
		
	}

	@Override
	public Vehicle generateVehicle(boolean isVertical, int length, int row, int col, Color color) {
		return new Vehicle(puzzle.getVehicleMapSize(), isVertical, length, row, col, color);
	}

	/**
	 * By choosing a different puzzle size will have to generate a new empty puzzle.
	 */
	@Override
	public void chooseBoardSize(int row, int col) {
		this.puzzle = new PuzzleGame(row, col);
	}

	/**
	 * Choose a different exit. 
	 * @pre exitRow < height && exitCol < width
	 * @post true
	 */
	@Override
	public void chooseExit(int exitRow, int exitCol) {
		this.puzzle.setExitCol(exitRow);
		this.puzzle.setExitRow(exitCol);
	}

}
