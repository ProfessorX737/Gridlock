
public class ClientInfo {
//	private BlockingQueue sendQueue = null;
	private String name;
	private boolean busy;
	private boolean isOnline;
	private String playingAgainst;
	private int wins;
	private int losses;

	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	public void setOnline(boolean online) {
		isOnline = online;
	}

	public void setPlayingAgainst(String playingAgainst) {
		this.playingAgainst = playingAgainst;
	}

	public ClientInfo(String username){
		this.name = username;
		this.busy = false;
		this.playingAgainst = null;
		this.isOnline = true;

	}

	public String getName() {
		return name;
	}

	public boolean isBusy() {
		return busy;
	}

	public String getPlayingAgainst() {
		return playingAgainst;
	}

	public void resetPlayingAgainst(){
		this.playingAgainst = null;
	}

	public int getWins(){
		return wins;
	}

	public int getLosses(){
		return losses;
	}

	public void incrementWins(){
		this.wins ++;
	}

	public void incrementLosses(){
		this.losses ++;
	}

}
