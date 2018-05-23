import java.awt.Color;
import java.util.List;

public class LevelEditorImplementation implements LevelEditor{
	PuzzleGame puzzle;
	
	/*
	 * Need to specify the initial size of the board.
	 */
	public LevelEditorImplementation(int row, int col) {
		this.puzzle = new PuzzleGame(row, col);
	}

	@Override
	public PuzzleGame exportLevel() {
		return puzzle;
	}

	@Override
	public PuzzleGame loadSavedLevel(String filename) {
		FileSystem f = new FileSystemImplementation();
		return f.loadPuzzleGame(filename);
	}

	@Override
	public void saveLevel(String filename) {
		FileSystem f = new FileSystemImplementation();
		f.savePuzzleGame(puzzle, filename);
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
	public boolean canAddVehicle(boolean isVertical, int length, int row, int col) {
		return puzzle.canAddVehicle(isVertical, length, row, col);
	}

	@Override
	public void addVehicle(boolean isVertical, int length, int row, int col, Color color) {
		puzzle.addVehicle(isVertical, length, row, col, color);
		
	}

	@Override
	public void removeVehicleAtLocation(int row, int col) {
		puzzle.removeVehicleAtLocation(row, col);
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

	@Override
	public int getVehicleIDAtLocation(int row, int col) {
		return puzzle.getVehicleIDAtLocation(row, col);
	}

	@Override
	public void changeVehiclePosition(int id, int row, int col) {
		puzzle.moveVehicle(id, row, col);
	}

	@Override
	public void changeVehicleIsVertical(int id, boolean isVertical) {
		boolean currIsVertical = puzzle.getVehicle(id).getIsVertical();
		if (currIsVertical != isVertical) {
			
		}
	}

	@Override
	public void changeVehicleColor(int id, Color color) {
		puzzle.getVehicle(id).setColor(color);
	}


}
