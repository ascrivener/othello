public class Location{
	public int x;
	public int y;
	public int value;

	Location(int tx, int ty){
		x = tx;
		y = ty;
		value = -1;
	}

	Location(int tx, int ty, int tvalue){
		x = tx;
		y = ty;
		value = tvalue;
	}
}