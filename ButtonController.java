import java.awt.event.ActionListener;

public interface ButtonController {
	public ActionListener getRedoButtonListener();
	public ActionListener getUndoButtonListener();
	public ActionListener getLoadGameButtonListener();
	public ActionListener getCreateGameButtonListener();
	public ActionListener getHintButtonListener();
	public ActionListener getResetButtonListener();
}