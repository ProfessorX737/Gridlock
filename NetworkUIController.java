import java.awt.event.ActionListener;

public interface NetworkUIController {

    public ActionListener getChallengeBtnListener();
    public ActionListener getSetUserBtnListener();
    public ActionListener getUpdateBtnListener();
    public ActionListener ConnectBtnListener();
    public ActionListener QuitBtnListener();
    public ActionListener ForfeitListener();

}
