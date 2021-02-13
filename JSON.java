package helmet;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class JSON {
	
	public JSON(){
		
	}
	
	public ArrayList<Score> score;
	private boolean change = false;		// variable that notifies any change in the scores
	

	public void setScore(ArrayList<Score> score) {
		this.score = score;
	}
	public ArrayList<Score> getScore() {
		return this.score;
	}
	
	
	public ArrayList<Score> read(){
		
		final Type SCORE_TYPE = new TypeToken<ArrayList<Score>>(){}.getType(); 
		Gson gson = new Gson();
		JsonReader reader = null;
		try {
			reader = new JsonReader(new FileReader("scores.json"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Score> data = gson.fromJson(reader, SCORE_TYPE);
		
		return data;
	}
	
	
	public ArrayList <Score> compareScore(Score newScore){

		score = read();
		for(Score point : score){ 			// find out if the new is higher than the saved ones
			if(point.getPunts()<newScore.getPunts()){
				score.set(2, newScore);		// the new is inserted on the podium
				change = true;
				break;
			}else{
				change=false;				// if not, nothing is written
			}
			
		}
		if(change){
			Collections.sort(score, new Comparator<Score>() {	//sort from higher to lower score
			    @Override
			    public int compare(Score p1, Score p2) {
			    	return new Integer(p2.getPunts()).compareTo(new Integer(p1.getPunts()));
			    }
			});
			writeJSON(score);	// write new scores
		}
		
		return score;
	}
	public void writeJSON(ArrayList<Score> points) {
		// TODO Auto-generated method stub
		try (Writer writer = new FileWriter("scores.json")) {	// open writer
		    Gson gson = new GsonBuilder().create();
		    gson.toJson(points, writer);						//insert the parameter
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}

