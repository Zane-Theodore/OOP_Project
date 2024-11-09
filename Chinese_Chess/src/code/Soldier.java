package code;

public class Soldier extends Piece {
	public Soldier(int x, int y, boolean isRed) {
		super(x, y, isRed);
	}

	// Hiển thị quân tốt ra màn console
	@Override
	public String toString() {
		return isRed ? red + "S" + reset : blue + "S" + reset;
	}

	// Kiểm tra nước đi có hợp lệ hay không
	@Override
	public boolean isValidMove(int newX, int newY, Board board) {
		// Không được đi vào ô đã có quân ta
		if (board.getPiece(newX, newY) != null && board.getPiece(newX, newY).isRed == this.isRed)
			return false;
		int dX = newX - x;
		int dY = newY - y;
		if (isRed) {
			// Trước khi qua sông không được đi ngang
			if (x <= 4)
				return dX == 1 && dY == 0;
			// Sau khi qua sông
			else
				return (dX == 1 && dY == 0) || (dX == 0 && Math.abs(dY) == 1);
		} else {
			// Trước khi qua sông không được đi ngang
			if (x >= 5)
				return dX == -1 && dY == 0;
			// Sau khi qua sông
			else
				return (dX == -1 && dY == 0) || (dX == 0 && Math.abs(dY) == 1);
		}
	}
}
