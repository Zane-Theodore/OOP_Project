package code;

public abstract class Piece {
	protected int x; // Tọa độ X
	protected int y; // Tọa độ Y
	protected boolean isRed; // True nếu quân cờ là đỏ, false nếu quân cờ là đen
	protected String red = "\u001B[31m"; // mã màu đỏ, tượng trưng cho quân đỏ
	protected String blue = "\u001B[34m"; // mã màu xanh tượng trưng cho quân xanh
	protected String reset = "\u001B[0m"; // mã dùng để reset lại màu

	public Piece(int x, int y, boolean isRed) {
		this.x = x;
		this.y = y;
		this.isRed = isRed;
	}

	// Hàm kiểm tra nước đi có hợp lệ cho quân cờ đó hay không
	public abstract boolean isValidMove(int newX, int newY, Board board);
}
