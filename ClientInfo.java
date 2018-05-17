
public class ClientInfo {
//	private BlockingQueue sendQueue = null;
	private String name;
	private boolean busy;
	private String playingAgainst;

	public ClientInfo(String username){
		this.name = username;
		this.busy = false;
		this.playingAgainst = null;
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


}
