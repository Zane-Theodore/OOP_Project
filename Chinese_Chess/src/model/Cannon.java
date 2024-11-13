package model;

import board.Board;

public class Cannon extends Piece {
	public Cannon(int x, int y, boolean isRed) {
		super(x, y, isRed);
	}

	// Hiển thị quân pháo ra màn console
	@Override
	public String toString() {
		return isRed ? Color.red + "C" + Color.reset : Color.blue + "C" + Color.reset;
	}

	// Kiểm tra nước đi có hợp lệ hay không
	@Override
	public boolean isValidMove(int newX, int newY, Board board) {
		// Không được đi vào ô đã có quân ta
		if (board.getPiece(newX, newY) != null && board.getPiece(newX, newY).isRed == this.isRed)
			return false;
		// Quân pháo chỉ được đi thẳng hoặc ngang
		if (x != newX && y != newY)
			return false;
		int countBetween = countPieceBetween(x, y, newX, newY, board);
		// Trường hợp không ăn quân, thì số quân trên đường đi phải là 0
		if (board.getPiece(newX, newY) == null) {
			return countBetween == 0;
		}
		// Trường hợp ăn quân thì số quân trên đường đi phải là 1
		else {
			return countBetween == 1 && (this.isRed != board.getPiece(newX, newY).isRed);
		}
	}

	// Đếm số quân cờ trên đường quân pháo đi
	private int countPieceBetween(int startX, int startY, int endX, int endY, Board board) {
		int count = 0;
		// Đếm theo chiều ngang
		if (startX == endX) {
			int minY = Math.min(startY, endY);
			int maxY = Math.max(startY, endY);
			for (int i = minY + 1; i < maxY; i++) {
				if (board.getPiece(startX, i) != null)
					count++;
			}
		}
		// Đếm theo chiều dọc
		else if (startY == endY) {
			int minX = Math.min(startX, endX);
			int maxX = Math.max(startX, endX);
			for (int i = minX + 1; i < maxX; i++) {
				if (board.getPiece(i, startY) != null)
					count++;
			}
		}
		return count;
	}
}
