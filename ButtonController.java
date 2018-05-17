import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public interface ButtonController {
    public ActionListener getRedoButtonListener();

    public ActionListener getUndoButtonListener();

    public ActionListener getLoadGameButtonListener();

    public ActionListener getCreateGameButtonListener();

    public ActionListener getHintButtonListener();

    public ActionListener getResetButtonListener();

	public ActionListener getTimerListener();
	
	public MouseAdapter getMouseAdapter();
}