import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Used for saving and loading files. 
 * TODO Change the serializationVersionUID when everything is set in stone.
 */
public class FileSystemImp implements FileSystem {

	public FileSystemImp() {
	}
	
	@Override
	public void savePuzzlesToFolder(List<PuzzleGame> puzzles, String folderPath) {
		try {
			File folder = new File(folderPath);
			for(PuzzleGame puzzle : puzzles) {
				File file = new File(folderPath, folder.getName() + String.format("%d", puzzle.getId()));
				this.savePuzzleGame(puzzle, file.getPath());
			}
		} catch(NullPointerException e) {
			System.out.print("Cannot access folder ");
			if(folderPath != null) System.out.printf("%s",folderPath);
			System.out.println("");
		}
	}

	@Override
	public void savePuzzleGame(PuzzleGame puzzle, String filename) {
		SaveFile f = new SaveFile(puzzle, filename);
		try {
			//open file and object as a stream and write stream to file
			FileOutputStream fileOutput = new FileOutputStream(filename + ".sav");
			ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);

			objectOutput.writeObject(f);
			
			//close file and object stream
			objectOutput.close();
			fileOutput.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}
	}
	
	@Override
	public List<PuzzleGame> loadPuzzlesFromFolder(String folderPath) {
		try {
			List<PuzzleGame> puzzles = new ArrayList<PuzzleGame>();
			File folder = new File(folderPath);
			for(File f : folder.listFiles()) {
				PuzzleGame puzzle = this.loadPuzzleGame(f.getPath());
				puzzles.add(puzzle);
			}
			return puzzles;
		} catch(NullPointerException e) {
			System.out.print("Cannot access folder ");
			if(folderPath != null) System.out.printf("%s",folderPath);
			System.out.println("");
		}
		return null;
	}
	
	/**
	 * To load a game you need to specify the name of the file.
	 * Don't include the .sav extension, this function adds it for you.
	 * @param filename name of the file
	 * @return the puzzleGame object stored in that file
	 */
	@Override
	public PuzzleGame loadPuzzleGame(String filename) {
		try {
			//open the file and input stream for the object and read into the object
			FileInputStream fileInput = new FileInputStream(filename);
			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			
			SaveFile f = (SaveFile) objectInput.readObject();
			
			//close object and file streams
			objectInput.close();
			fileInput.close();
			
			return f.getPuzzleGame();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
