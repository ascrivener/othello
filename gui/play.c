#include <stdio.h>

struct location
{
	int x;
	int y;
	struct location *next;
};


static int board[8][8];

static struct location moveTable[8][8][2];

static struct location playerMoves[2]



int main()
{
	initialize();
}



void initialize(){
	board[3][3] = 1
	board[4][4] = 1
	board[4][3] = 0
	board[3][4] = 0

	moveTable[3][3][1] = (struct location*)malloc
	<< [3,5] << [5,3]
	moveTable[4][4][1] << [2,4] << [4,2]
	moveTable[3][4][0] << [3,2] << [5,4]
	moveTable[4][3][0] << [2,3] << [4,5]

	moveTable[5][3][1] << [3,3]
	moveTable[3][5][1] << [3,3]
	moveTable[2][4][1] << [4,4]
	moveTable[4][2][1] << [4,4]

	moveTable[3][2][0] << [3,4]
	moveTable[5][4][0] << [3,4]
	moveTable[2][3][0] << [4,3]
	moveTable[4][5][0] << [4,3]
}