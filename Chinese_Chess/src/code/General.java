package code;

public class General extends Piece {
	public General(int x, int y, boolean isRed) {
		super(x, y, isRed);
	}

	// Hiển thị quân tướng lên màn console
	@Override
	public String toString() {
		return isRed ? "G" : "g";
	}

	// Kiểm tra nước đi có hợp lệ hay không
	@Override
	public boolean isValidMove(int newX, int newY, Board board) {
		// Không được đi vào ô đã có quân ta
		if (board.getPiece(newX, newY) != null && board.getPiece(newX, newY).isRed == this.isRed)
			return false;

		// Quân tướng chỉ được di chuyển trong "cung"
		if (isRed && (newX < 0 || newX > 2 || newY < 3 || newY > 5))
			return false;
		if (!isRed && (newX < 7 || newX > 9 || newY < 3 || newY > 5))
			return false;

		// Quân tướng chỉ được di chuyển một ô trong một lần di chuyển
		int dX = Math.abs(newX - x);
		int dY = Math.abs(newY - y);
		return (dX + dY) == 1;
	}
}
