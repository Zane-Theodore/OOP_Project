package code;

public class Elephant extends Piece {
	public Elephant(int x, int y, boolean isRed) {
		super(x, y, isRed);
	}

	// Hiển thị quân tượng ra màn console
	@Override
	public String toString() {
		return isRed ? red + "E" + reset : blue + "E" + reset;
	}

	// Kiểm tra nước đi có hợp lệ hay không
	@Override
	public boolean isValidMove(int newX, int newY, Board board) {
		// Không được đi vào ô đã có quân ta
		if (board.getPiece(newX, newY) != null && board.getPiece(newX, newY).isRed == this.isRed)
			return false;
		// Quân tượng không thể qua sông
		if (isRed && newX > 4)
			return false;
		if (!isRed && newX < 5)
			return false;
		// Kiểm tra nước đi có phải là đường chéo của ô vuông 2x2 hay không
		int dX = newX - x;
		int dY = newY - y;
		if (Math.abs(dX) == 2 && Math.abs(dY) == 2) {
			int midX = x + (dX / 2);
			int midY = y + (dY / 2);
			// Kiểm tra đường đi có bị chặn hay không
			return board.getPiece(midX, midY) == null;
		}
		return false;
	}
}
