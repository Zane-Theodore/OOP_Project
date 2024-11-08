package code;

public abstract class Piece {
	protected int x; // Tọa độ X
	protected int y; // Tọa độ Y
	protected boolean isRed; // True nếu quân cờ là đỏ, false nếu quân cờ là đen

	public Piece(int x, int y, boolean isRed) {
		this.x = x;
		this.y = y;
		this.isRed = isRed;
	}

	// Hàm kiểm tra nước đi có hợp lệ cho quân cờ đó hay không
	public abstract boolean isValidMove(int newX, int newY, Board board);
}
