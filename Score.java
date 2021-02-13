package helmet;

public class Score {     // score object is compounded by the user name and its score
	private int points;
	private String user;

	
	public Score(int points, String user){
		this.points = points;
		this.user = user;
	}

	public int getPunts() {			// setters & getters
		return points;
	}

	public void setPunts(int punts) {
		this.points = punts;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
}
