package code;

public class Board {
	private int row = 10;
	private int col = 9;
	private Piece[][] board;

	public Board() {
		board = new Piece[row][col];
		initializePieces();
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	// Khởi tạo các quân cờ ở vị trí ban đầu
	public void initializePieces() {
		// Bên đỏ
		board[0][0] = new Rook(0, 0, true);
		board[0][8] = new Rook(0, 8, true);
		board[0][1] = new Hourse(0, 1, true);
		board[0][7] = new Hourse(0, 7, true);
		board[0][2] = new Elephant(0, 2, true);
		board[0][6] = new Elephant(0, 6, true);
		board[0][3] = new Advisor(0, 3, true);
		board[0][5] = new Advisor(0, 5, true);
		board[0][4] = new General(0, 4, true);
		board[2][1] = new Cannon(2, 1, true);
		board[2][7] = new Cannon(2, 7, true);
		board[3][0] = new Soldier(3, 0, true);
		board[3][2] = new Soldier(3, 2, true);
		board[3][4] = new Soldier(3, 4, true);
		board[3][6] = new Soldier(3, 6, true);
		board[3][8] = new Soldier(3, 8, true);

		// Bên đen
		board[9][0] = new Rook(9, 0, false);
		board[9][8] = new Rook(9, 8, false);
		board[9][1] = new Hourse(9, 1, false);
		board[9][7] = new Hourse(9, 7, false);
		board[9][2] = new Elephant(9, 2, false);
		board[9][6] = new Elephant(9, 6, false);
		board[9][3] = new Advisor(9, 3, false);
		board[9][5] = new Advisor(9, 5, false);
		board[9][4] = new General(9, 4, false);
		board[7][1] = new Cannon(7, 1, false);
		board[7][7] = new Cannon(7, 7, false);
		board[6][0] = new Soldier(6, 0, false);
		board[6][2] = new Soldier(6, 2, false);
		board[6][4] = new Soldier(6, 4, false);
		board[6][6] = new Soldier(6, 6, false);
		board[6][8] = new Soldier(6, 8, false);
	}

	public Piece getPiece(int x, int y) {
		return board[x][y];
	}

	// Vẽ lại bàn cờ
	public void drawBoard() {
		for (int i = 0; i < col; i++) {
			if (i == 0)
				System.out.print(" ");
			System.out.print(" " + i);
		}
		for (int i = 0; i < row; i++) {
			if (i == 0)
				System.out.print('\n');
			System.out.print(i + " ");
			for (int j = 0; j < col; j++) {
				if (board[i][j] == null) {
					System.out.print(". ");
				} else {
					System.out.print(board[i][j] + " ");
				}
			}
			System.out.print('\n');
		}
	}

	// Di chuyển quân cờ
	public boolean movePiece(int startX, int startY, int endX, int endY) {
		Piece piece = getPiece(startX, startY);
		if (piece != null && piece.isValidMove(endX, endY, this)) {
			Piece targetPiece = getPiece(endX, endY);
			// Kiểm tra sự hợp lệ của vị trí cần đến
			if (targetPiece == null || (targetPiece.isRed != piece.isRed)) {
				board[endX][endY] = piece;
				board[startX][startY] = null;
				board[endX][endY].x = endX;
				board[endX][endY].y = endY;
				return true;
			}
		}
		return false;
	}

	// Tìm vị trí quân tướng
	public Piece findKing(boolean isRed) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				Piece piece = board[i][j];
				// Kiểm tra nếu quân cờ hiện tại là quân tướng của bên đang kiểm tra
				if (piece != null && piece instanceof General && piece.isRed == isRed) {
					return piece;
				}
			}
		}
		return null;
	}
}
