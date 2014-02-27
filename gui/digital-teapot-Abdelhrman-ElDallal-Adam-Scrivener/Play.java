import java.util.*;
public class Play{
	public static void main(String [] args){

		Scanner scan = new Scanner(System.in);

		String line[] = scan.nextLine().split(" ");

		System.out.println(line[1]);

		Game game = new Game(8);

		// System.out.println(game);

		// eval(2, 3, 0, game);

		Game newGame = new Game(game);


		if (line[1].equals("B")){
			while (true){
				Location choice = alphaBeta(game,0,Integer.MIN_VALUE,Integer.MAX_VALUE,0);
				// System.out.println(choice.x);
				// System.out.println(choice.y);
				System.out.println(game.makeMove(choice,0));
				// System.out.println(game);
				// game.printMoves(0);
				// game.printMoves(1);
				String[] move = scan.nextLine().split(" ");
				int[] moveInts = {Integer.parseInt(move[0]),Integer.parseInt(move[1])};
				game.update(moveInts[0],moveInts[1],1);
			}
		}
		else{
			while(true){
				String[] move = scan.nextLine().split(" ");
				int[] moveInts = {Integer.parseInt(move[0]),Integer.parseInt(move[1])};
				game.update(moveInts[0],moveInts[1],0);
				Location choice = alphaBeta(game,0,Integer.MIN_VALUE,Integer.MAX_VALUE,1);
				System.out.println(game.makeMove(choice,1));
				// System.out.println(game);	
				// game.printMoves(0);
				// game.printMoves(1);	
			}
		}

	}

	public static int eval(int player, Game game){
		int enemy = 1;
		if (player == 1)
			enemy = 0;

		Game newGame = new Game(game);

		int max = 0;
		//Location best = null;

		//List<Location> moveList = newGame.playerMoves[player].moves;

		List<Location> moveList = new ArrayList<Location>();
		for (Location loc : newGame.playerMoves[player].moves){
			moveList.add(loc);
		}
		
		for (Location loc : moveList){

			newGame.update(loc.x,loc.y,player);

			int moveDifference = newGame.playerMoves[player].moves.size() - newGame.playerMoves[enemy].moves.size();

			int pieceCount = newGame.pieceCount[player];

			int sum = pieceCount+moveDifference;

			if (sum > max){
				max = sum;
				//best = loc;
			}
			// System.out.println(newGame.pieceCount[player]);

			// System.out.println(moveDifference);


			newGame = new Game(game);
		}

		return max;
	}

	public static Location alphaBeta(Game game, int depth, int alpha, int beta, int player){
		int enemy = 1;
		if (player == 1)
			enemy = 0;

		if (depth == 4){
			return new Location(-1,-1,eval(1,game)-eval(0,game));
		}

		Location best = null;

		if (player == 1){
			Game newGame = new Game(game);
			List<Location> moveList = new ArrayList<Location>();
			for (Location loc : newGame.playerMoves[player].moves){
				moveList.add(loc);
			}
			for (Location loc : moveList){
				newGame.update(loc.x,loc.y,player);
				int val = alphaBeta(newGame, depth+1,alpha,beta,enemy).value;
				// System.out.println("white:");
				// System.out.println(val);
				// System.out.println(alpha);
				if (val > alpha){
					alpha = val;
					best = loc;
					//System.out.println("best was updated");
				}
				if (beta <= alpha){
					break;
				}
				newGame = new Game(game);
			}
			if (best != null)
				return new Location(best.x,best.y,alpha);
			else
				return new Location(-1,-1,alpha);
		}
		else{
			Game newGame = new Game(game);
			List<Location> moveList = new ArrayList<Location>();
			for (Location loc : newGame.playerMoves[player].moves){
				moveList.add(loc);
			}
			for (Location loc : moveList){
				newGame.update(loc.x,loc.y,player);
				Location ab = alphaBeta(newGame, depth+1,alpha,beta,enemy);
				int val = ab.value;
				// System.out.println("black:");
				// System.out.println(val);
				// System.out.println(alpha);
				if (val < beta){
					beta = val;
					best = loc;
					//System.out.println("best was updated");
				}
				if (beta <= alpha){
					break;
				}
				newGame = new Game(game);
			}
			if (best != null)
				return new Location(best.x,best.y,beta);
			else
				return new Location(-1,-1,beta);
		}
	}
}