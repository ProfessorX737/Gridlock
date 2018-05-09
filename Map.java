import java.awt.Image;
import java.io.File;
import java.util.Scanner;

public class Map {
	
	private String fileName;
	
	private Scanner m;
	
	private String Map[] = new String[6];
	
	public Map(String fileName) {
		
		this.fileName = fileName;
		
		openFile();
		readFile();
		closeFile();
	}
	
	public String getMap(int x, int y) {
		String index = Map[y].substring(x, x + 1);
		return index;
	}
	
	public void openFile() {
		try {
			m = new Scanner(new File(fileName));
		}catch(Exception e) {
			System.out.println("error");
		}
	}
	
    public void readFile() {
		while(m.hasNext()) {
			for(int i = 0; i < 6; i++) {
				Map[i] = m.next();
			}
		}
	}
    
    public void closeFile() {
		m.close();
	}
}

