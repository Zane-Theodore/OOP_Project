package code;

public abstract class Piece {
	// Tọa độ X
	protected int x;
	// Tọa độ Y
	protected int y;
	// True nếu quân cờ là đỏ, false nếu quân cờ là xanh
	protected boolean isRed;
	// mã màu đỏ, tượng trưng cho quân đỏ
	protected String red = "\u001B[31m";
	// mã màu xanh tượng trưng cho quân xanh
	protected String blue = "\u001B[34m";
	// mã dùng để reset lại màu
	protected String reset = "\u001B[0m";

	public Piece(int x, int y, boolean isRed) {
		this.x = x;
		this.y = y;
		this.isRed = isRed;
	}

	// Hàm kiểm tra nước đi có hợp lệ cho quân cờ đó hay không
	public abstract boolean isValidMove(int newX, int newY, Board board);
}
