
public class ClientInfo {
//	private BlockingQueue sendQueue = null;
	private String name;
	private boolean busy;
	private boolean isOnline;
	private String playingAgainst;

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


}
