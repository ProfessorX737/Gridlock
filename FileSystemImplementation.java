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
public class FileSystemImplementation implements FileSystem {

	public FileSystemImplementation() {
	}

	/**
	 * To save a file you need to specify the file and the filename
	 * @param puzzle, which is the puzzle you want to save
	 * @param filename, which will be the name of the file saved
	 */
	@Override
	public void savePuzzleGame(PuzzleGame puzzle, String filename) {
		SaveFile f = new SaveFile(puzzle, filename);
		try {
			FileOutputStream fileOutput = new FileOutputStream(new File(filename + ".sav"));
			ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
			
			//write save file
			objectOutput.writeObject(f);
			
			objectOutput.close();
			fileOutput.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}
	}
	
	/**
	 * To load a game you need to specify the name of the file.
	 * Don't include the .sav extension, this function adds it for you.
	 * @param filename, name of the file
	 * @return
	 */
	@Override
	public PuzzleGame loadPuzzleGame(String filename) {
		try {
		FileInputStream fileInput = new FileInputStream(new File(filename + ".sav"));
		ObjectInputStream objectInput = new ObjectInputStream(fileInput);
		
		//reading save file from file
		SaveFile f = (SaveFile) objectInput.readObject();
		
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
	
    /**
     * Get list of games that have been saved
     * @return
     */
	@Override
	public List<String> getSaveGameList() {
    		List<String> fileList = new ArrayList<String>();
    		File folder = new File("").getAbsoluteFile();
    		for (File f : folder.listFiles()) {
    			if (f.getName().endsWith(".sav")) {
				fileList.add(f.getName());
    			}
    		}
		return fileList;
    }
}
