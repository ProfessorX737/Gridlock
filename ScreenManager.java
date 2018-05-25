import javax.swing.*;
import java.awt.*;

public class ScreenManager extends JPanel {
    public static String MAINMENU = "Main menu screen";
    public static String LEVELSELECT = "Level select screen";
    public static String PUZZLESELECT = "Puzzle select screen";
    public static String GAMEVIEW = "Game screen";
    private MainMenu mainMenu;
    private LevelSelectScreen levelSelectScreen;
    private PuzzleSelectScreen puzzleSelectScreen;
    private GameView gameView;

    public ScreenManager(MainMenu mainMenu) {
        this.mainMenu = mainMenu;

        setLayout(new CardLayout());
        this.add(mainMenu, MAINMENU);
    }

}
