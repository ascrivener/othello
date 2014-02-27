import java.util.*;
public class Game{
	public int size;
	public int[][] board;
	public MoveList[][][] moveTable;
	public MoveList[] playerMoves;
	public int[] pieceCount;
	public Game(Game game){
		size = game.size;
		board = new int[size][size];
		moveTable = new MoveList[size][size][2];
		playerMoves = new MoveList[2];
		pieceCount = new int[2];

		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				board[i][j] = game.board[i][j];
			}
		}

		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				for (int k = 0; k < 2; k++){
					moveTable[i][j][k] = new MoveList();
					for (Location loc : game.moveTable[i][j][k].moves){
						moveTable[i][j][k].moves.add(loc);
					}
				}
			}
		}

		for (int i = 0; i < 2; i++){
			playerMoves[i] = new MoveList();
			for (Location loc : game.playerMoves[i].moves){
				playerMoves[i].moves.add(loc);
			}
		}

		pieceCount[0] = game.pieceCount[0];
		pieceCount[1] = game.pieceCount[1];
	}
	public Game(int n){
		size = n;
		board = new int[n][n];
		moveTable = new MoveList[n][n][2];
		playerMoves = new MoveList[2];
		pieceCount = new int[2];

		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				board[i][j] = -1;
			}
		}

		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				for (int k = 0; k < 2; k++){
					moveTable[i][j][k] = new MoveList();
				}
			}
		}

		for (int i = 0; i < 2; i++){
			playerMoves[i] = new MoveList();
		}


		board[3][3] = 1;
		board[4][4] = 1;
		board[4][3] = 0;
		board[3][4] = 0;

		moveTable[3][3][1].add(3,5);
		moveTable[3][3][1].add(5,3);
		moveTable[4][4][1].add(2,4);
		moveTable[4][4][1].add(4,2);
		moveTable[3][4][0].add(3,2);
		moveTable[3][4][0].add(5,4);
		moveTable[4][3][0].add(2,3);
		moveTable[4][3][0].add(4,5);

		moveTable[5][3][1].add(3,3);
		moveTable[3][5][1].add(3,3);
		moveTable[2][4][1].add(4,4);
		moveTable[4][2][1].add(4,4);

		moveTable[3][2][0].add(3,4);
		moveTable[5][4][0].add(3,4);
		moveTable[2][3][0].add(4,3);
		moveTable[4][5][0].add(4,3);

		playerMoves[1].add(3,5);
		playerMoves[1].add(5,3);
		playerMoves[1].add(2,4);
		playerMoves[1].add(4,2);

		playerMoves[0].add(2,3);
		playerMoves[0].add(4,5);
		playerMoves[0].add(3,2);
		playerMoves[0].add(5,4);

		pieceCount[0] = 2;
		pieceCount[1] = 2;
	}

	public boolean isDone(){
		return playerMoves[0].moves.isEmpty() && playerMoves[1].moves.isEmpty();
	}

	public void update(int i, int j, int player){
		List<Location> list = new ArrayList<Location>();

		list.add(new Location(i,j));

		int enemy = 1;
		if (player == 1)
			enemy = 0;

		playerMoves[player].delete(i,j);
		playerMoves[enemy].delete(i,j);

		board[i][j] = player;
		pieceCount[player]++;



		for (Location loc : moveTable[i][j][player].moves){
			int x = loc.x;
			int y = loc.y;

			moveTable[x][y][player].delete(i,j);

			int dx = i - x;
			int dy = j - y;

			int max = Math.max(Math.abs(dx),Math.abs(dy));

			dx /= max;
			dy /= max;

			x += dx;
			y += dy;

			while (x != i || y != j){
				list.add(new Location(x,y));

				for (Location loc2 : moveTable[x][y][enemy].moves){
					int a = loc2.x;
					int b = loc2.y;

					moveTable[a][b][enemy].delete(x,y);
					if (moveTable[a][b][enemy].moves.isEmpty()){
						playerMoves[enemy].delete(a,b);
					}
				}
				moveTable[x][y][enemy] = new MoveList();
				board[x][y] = player;
				pieceCount[player]++;
				pieceCount[enemy]--;
				x += dx;
				y += dy;
			}

		}

		moveTable[i][j][player] = new MoveList();
		moveTable[i][j][enemy] = new MoveList();

		for (Location loc : list){


			int x = loc.x;
			int y = loc.y;

			int tmpx = x;
			int tmpy = y;


			List<Location> directions = new ArrayList<Location>();

			directions.add(new Location(-1,-1));
			directions.add(new Location(-1,0));
			directions.add(new Location(-1,1));
			directions.add(new Location(0,-1));
			directions.add(new Location(0,1));
			directions.add(new Location(1,-1));
			directions.add(new Location(1,0));
			directions.add(new Location(1,1));


			for (Location dir : directions){
				int dx = dir.x;
				int dy = dir.y;


				x += dx;
				y += dy;

				while (isInbounds(x,y) && board[x][y] == player){
					x += dx;
					y += dy;
				}
				if (isInbounds(x,y) && board[x][y] == enemy){
					int locx = x;
					int locy = y;

					x = tmpx;
					y = tmpy;

					x -= dx;
					y -= dy;

					while (isInbounds(x,y) && board[x][y] == player){
						x -= dx;
						y -= dy;
					}


					if (isInbounds(x,y) && board[x][y] == -1){
						if (moveTable[x][y][enemy].moves.isEmpty())
							playerMoves[enemy].add(x,y);
						moveTable[locx][locy][enemy].add(x,y);
						moveTable[x][y][enemy].add(locx,locy);

					}
				}

				x = tmpx;
				y = tmpy;

				/************************************/

				x += dx;
				y += dy;

				while (isInbounds(x,y) && board[x][y] == enemy){
					x += dx;
					y += dy;
				}

				if (isInbounds(x,y) && board[x][y] == player){
					int locx = x;
					int locy = y;

					x = tmpx;
					y = tmpy;

					x -= dx;
					y -= dy;

					while (isInbounds(x,y) && board[x][y] == enemy){
						x -= dx;
						y -= dy;
					}

					if (isInbounds(x,y) && board[x][y] == -1){
						moveTable[locx][locy][player].delete(x,y);
						moveTable[x][y][player].delete(locx,locy);
						if (moveTable[x][y][player].moves.isEmpty())
							playerMoves[player].delete(x,y);
					}
				}
				else if (isInbounds(x,y) && board[x][y] == -1 && (tmpx+dx != x || tmpy+dy != y)){
					if (moveTable[x][y][player].moves.isEmpty())
						playerMoves[player].add(x,y);
					moveTable[tmpx][tmpy][player].add(x,y);
					moveTable[x][y][player].add(tmpx,tmpy);
				}
				else{}

				x = tmpx;
				y = tmpy;



			}

		}
		return;
	}

	public boolean isInbounds(int x, int y){
		return 0 <= x && x < size && 0 <= y && y < size;
	}

	public String makeMove(Location move, int color){
		if (move.x != -1 && move.y != -1){
			update(move.x,move.y,color);
			return "" + move.x + " " + move.y;
		}
		else{
			return "pass";
		}
	}

	public void printMoves(int color){
		String player = "";
		if (color == 1)
			player = "white";
		else
			player = "black";

		System.out.print(player + " moves: ");
		for (Location loc : playerMoves[color].moves){
			int x = loc.x;
			int y = loc.y;
			System.out.print("[" + x + "," + y + "]");
			System.out.print(": ");
			System.out.println(moveTable[x][y][color]);
		}
		System.out.println();

	}

	public void getWinner(){
		int bCount = 0;
		int wCount = 0;

		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				if (board[i][j] == 0)
					bCount++;
				if (board[i][j] == 1)
					wCount++;
			}
		}
		if (bCount > wCount)
			System.out.println("BLACK WINS!!");
		else if(wCount > bCount)
			System.out.println("WHITE WINS!!");
		else
			System.out.println("tie.");
	}
	public String toString(){
		String output = "";
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				if (board[i][j] == -1)
					output += "[ ]";
				else if (board[i][j] == 1)
					output += " W ";
				else
					output += " B ";
			}
			output += "\n";
		}
		output += "Black moves: " + playerMoves[0].toString() + "\n";
		output += "White Moves: " + playerMoves[1].toString() + "\n";
		return output;
	}
}