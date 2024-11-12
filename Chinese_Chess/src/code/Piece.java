package code;

public abstract class Piece {
	// Tọa độ X
	protected int x;
	// Tọa độ Y
	protected int y;
	// Kiểm tra xem quân cờ phải quân đỏ hay không
	protected boolean isRed;

	public Piece(int x, int y, boolean isRed) {
		this.x = x;
		this.y = y;
		this.isRed = isRed;
	}

	// Hàm kiểm tra nước đi có hợp lệ cho quân cờ đó hay không
	public abstract boolean isValidMove(int newX, int newY, Board board);
}
