import java.util.*;
public class MoveList{
	public List<Location> moves;

	public MoveList(){
		moves = new ArrayList<Location>();
	}

	public void delete(int x, int y){
		for (int i = moves.size()-1; i >= 0; i--){
			Location loc = moves.get(i);
			if (x == loc.x && y == loc.y){
				moves.remove(i);
				break;
			}
		}
	}

	public void add(int i, int j){
		boolean contains = false;
		for (int k = moves.size()-1; k >= 0; k--){
			Location loc = moves.get(k);
			if (i == loc.x && j == loc.y){
				contains = true;
				break;
			}
		}
		if (!contains)
			moves.add(new Location(i,j));
	}

	public String toString(){
		String output = "";
		for (Location loc : moves){
			output += "[" + loc.x + "," + loc.y + "] ";
		}
		return output;
	}
}