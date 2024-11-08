package code;

public class Advisor extends Piece {

	public Advisor(int x, int y, boolean isRed) {
		super(x, y, isRed);
	}

	// Hiển thị quân sĩ ra màn console
	@Override
	public String toString() {
		return isRed ? "A" : "a";
	}

	// Kiểm tra nước đi có hợp lệ hay không
	@Override
	public boolean isValidMove(int newX, int newY, Board board) {
		// Không được đi vào ô đã có quân ta
		if (board.getPiece(newX, newY) != null && board.getPiece(newX, newY).isRed == this.isRed)
			return false;

		// Quân sĩ chỉ được phép di chuyển trong "cung"
		if (isRed && (newX < 0 || newX > 2 || newY < 3 || newY > 5))
			return false;
		if (!isRed && (newX < 7 || newX > 9 || newY < 3 || newY > 5))
			return false;

		// Quân sỉ chỉ được phép đi chéo
		int dX = Math.abs(newX - x);
		int dY = Math.abs(newY - y);
		return dX == 1 && dY == 1;
	}
}
