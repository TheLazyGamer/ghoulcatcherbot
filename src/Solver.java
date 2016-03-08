public class Solver extends GhoulCatchers {
	public Move findMove(MyColor[][] board) {
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				if (j < BOARD_WIDTH - 1) {
					MyColor[][] swapped = swap(duplicateBoard(board), i, j, Direction.RIGHT);
					if (verify(swapped)) {
						return new Move(i, j, Direction.RIGHT);
					}
				}

				if (i < BOARD_HEIGHT - 1) {
					MyColor[][] swapped = swap(duplicateBoard(board), i, j, Direction.DOWN);
					if (verify(swapped)) {
						return new Move(i, j, Direction.DOWN);
					}
				}
			}
		}
		return null;
	}

	private boolean verify(MyColor[][] board) {
		// check horizontally
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			int consecutive = 1;
			for (int j = 0; j < BOARD_WIDTH - 1; j++) {
				if (!board[i][j].equals(MyColor.UNKNOWN) && board[i][j].equals(board[i][j + 1])) {
					consecutive++;
					if (consecutive >= 3 && Math.random() > RANDOM_MOVE) {
						return true;
					}
				} else {
					consecutive = 1;
				}
			}
		}

		// check vertically
		for (int j = 0; j < BOARD_WIDTH; j++) {
			int consecutive = 1;
			for (int i = 0; i < BOARD_HEIGHT - 1; i++) {
				if (!board[i][j].equals(MyColor.UNKNOWN) && board[i][j].equals(board[i + 1][j])) {
					consecutive++;
					if (consecutive >= 3 && Math.random() > RANDOM_MOVE) {
						return true;
					}
				} else {
					consecutive = 1;
				}
			}
		}
		return false;
	}

	private MyColor[][] duplicateBoard(MyColor[][] board) {
		MyColor[][] tempBoard = new MyColor[BOARD_HEIGHT][BOARD_WIDTH];
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				tempBoard[i][j] = board[i][j];
			}
		}
		return tempBoard;
	}

	private MyColor[][] swap(MyColor[][] board, int y, int x, Direction direction) {
		MyColor temp = board[y][x];
		switch (direction) {
		case RIGHT:
			board[y][x] = board[y][x + 1];
			board[y][x + 1] = temp;
			break;
		case DOWN:
			board[y][x] = board[y + 1][x];
			board[y + 1][x] = temp;
			break;

		default:
			return null;
		}
		return board;
	}
}
