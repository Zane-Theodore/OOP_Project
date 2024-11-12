package code;

public class Horse extends Piece {
	public Horse(int x, int y, boolean isRed) {
		super(x, y, isRed);
	}

	// Hiển thị quân mã ra màn console
	@Override
	public String toString() {
		return isRed ? Color.red + "H" + Color.reset : Color.blue + "H" + Color.reset;
	}

	// Kiểm tra nước đi có hợp lệ hay không
	@Override
	public boolean isValidMove(int newX, int newY, Board board) {
		// Không được đi vào ô đã có quân ta
		if (board.getPiece(newX, newY) != null && board.getPiece(newX, newY).isRed == this.isRed)
			return false;
		int dX = newX - x;
		int dY = newY - y;
		// Kiểm tra đường đi có phải theo hình chữ L hay không
		if ((Math.abs(dX) == 1 && Math.abs(dY) == 2) || (Math.abs(dX) == 2 && Math.abs(dY) == 1)) {
			// Kiểm tra đường đi có bị chặn hay không
			if (Math.abs(dX) == 2)
				return board.getPiece(x + dX / 2, y) == null;
			if (Math.abs(dY) == 2)
				return board.getPiece(x, y + dY / 2) == null;
		}
		return false;
	}
}
