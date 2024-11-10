package code;

public class Rook extends Piece {
	public Rook(int x, int y, boolean isRed) {
		super(x, y, isRed);
	}

	// Hiển thị quân xe ra màn console
	@Override
	public String toString() {
		return isRed ? red + "R" + reset : blue + "R" + reset;
	}

	// Kiểm tra nước đi có hợp lệ hay không
	@Override
	public boolean isValidMove(int newX, int newY, Board board) {
		// Không được đi vào ô đã có quân ta
		if (board.getPiece(newX, newY) != null && board.getPiece(newX, newY).isRed == this.isRed)
			return false;
		// Quân xe chỉ được đi thẳng hoặc đi ngang
		if (newX != x && newY != y)
			return false;
		// Kiểm tra xem đường đi có bị chặn hay không
		// Đi ngang
		if (newX == x) {
			int minY = Math.min(y, newY);
			int maxY = Math.max(y, newY);
			for (int i = minY + 1; i < maxY; i++) {
				if (board.getPiece(x, i) != null)
					return false;
			}
		}
		// Đi dọc
		else {
			int minX = Math.min(x, newX);
			int maxX = Math.max(x, newX);
			for (int i = minX + 1; i < maxX; i++) {
				if (board.getPiece(i, y) != null)
					return false;
			}
		}
		return true;
	}
}
