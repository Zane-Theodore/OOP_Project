package model;

import board.Board;

public abstract class Piece {
	// Tọa độ X
	protected int x;
	// Tọa độ Y
	protected int y;
	// Kiểm tra xem quân cờ phải quân đỏ hay không
	protected boolean isRed;

	// Các getter, setter
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean getIsRed() {
		return isRed;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Piece(int x, int y, boolean isRed) {
		this.x = x;
		this.y = y;
		this.isRed = isRed;
	}

	// Hàm kiểm tra nước đi có hợp lệ cho quân cờ đó hay không
	public abstract boolean isValidMove(int newX, int newY, Board board);
}
