package code;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
	private Board board;
	private boolean isRedTurn;
	private boolean isRunning;
	private boolean isValidValue;
	private int yourChoice;
	private ArrayList<String> historyMove;

	public Game() {
		board = new Board();
		historyMove = new ArrayList<>();
		isRedTurn = true; // Bên đỏ đi trước
		isRunning = true; // Biến để biết chương trình vẫn còn đang chạy
	}

	public void runGame() {
		Scanner sc = new Scanner(System.in);
		int startX, startY, endX, endY;

		while (isRunning) {
			System.out.print('\n');
			board.drawBoard();
			System.out.println("Lượt của " + (isRedTurn ? "bên đỏ" : "bên đen"));

			// Kiểm tra xem người chơi hiện tại có bị chiếu hay không
			if (isCheck(isRedTurn)) {
				System.out.println("NGƯỜI CHƠI " + (isRedTurn ? "ĐỎ" : "ĐEN") + " ĐANG BỊ CHIẾU!!");
				if (isCheckMate(isRedTurn)) {
					System.out.println("NGƯỜI CHƠI " + (!isRedTurn ? "ĐỎ" : "ĐEN") + " THẮNG DO CHIẾU BÍ!!");
					isRunning = false;
					break;
				}
			}
			startX = -1;
			startY = -1;
			endX = -1;
			endY = -1;

			// Lựa chọn hành động
			isValidValue = false;
			while (!isValidValue) {
				try {
					// Tạo menu
					System.out.println("Hãy chọn hành động:");
					System.out.println("[1] Di chuyển quân cờ");
					System.out.println("[2] Xem lại lịch sử di chuyển");
					System.out.print("Lựa chọn của bạn là: ");

					String input = sc.nextLine().trim();

					// Kiểm tra chuỗi có rỗng hoặc có độ dài khác 1 hay không
					if (input.isEmpty() || input.length() != 1) {
						System.out.println("Sai định dạng, hãy nhập lại");
						continue;
					}
					// Chuyển đổi sang số nguyên
					yourChoice = Integer.parseInt(input);

					// Kiểm tra đó có phải lựa chọn hợp lệ hay là không
					if (yourChoice >= 1 && yourChoice <= 2) {
						isValidValue = true;
					} else {
						System.out.println("Lựa chọn không hợp lệ, hãy chọn lại.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Sai định dạng, hãy nhập lại.");
				}

			}
			switch (yourChoice) {

			// Di chuyển quân cờ
			case 1:
				// Nhập tọa độ và kiểm tra sự hợp lệ của tọa độ được nhập vào
				isValidValue = false;
				while (!isValidValue) {
					System.out.print('\n');
					board.drawBoard();
					try {
						System.out.print("Nhập vào tọa độ quân cờ bạn muốn di chuyển: ");
						startX = sc.nextInt();
						startY = sc.nextInt();
						// Kiểm tra vị trí đó có nằm trong bàn cờ hay không
						if (!isValidPosition(startX, startY)) {
							System.out.println("Vị trí không hợp lệ.");
						}
						// Kiểm tra vị trí đó có quân cờ để di chuyển hay không
						else if (board.getPiece(startX, startY) == null) {
							System.out.println("Vị trí bạn chọn không có quân cờ nào.");
						}
						// Kiểm tra đó có phải quân cờ của người đang chơi hay không
						else if (board.getPiece(startX, startY).isRed != isRedTurn) {
							System.out.println("Quân cờ đó không phải của bạn.");
						} else {
							isValidValue = true;
						}
					}
					// catch khi sai định dạng số
					catch (InputMismatchException e) {
						System.out.println("Tọa độ bạn nhập đã sai định dạng, hãy nhập lại.");
						sc.nextLine();
					}
				}
				Piece piece = board.getPiece(startX, startY);

				// In ra các nước đi hợp lệ với quân cờ đã chọn
				System.out.println("Các nước đi hợp lệ với quân cờ bạn chọn là: ");
				for (int i = 0; i < board.getRow(); i++) {
					for (int j = 0; j < board.getCol(); j++) {
						if (piece.isValidMove(i, j, board)) {
							System.out.println("(" + i + ", " + j + ")");
						}
					}
				}

				// Nhập và kiểm tra vị trí bạn muốn đặt cờ
				isValidValue = false;
				while (!isValidValue) {
					try {
						System.out.print("Nhập vào tọa độ bạn muốn đặt quân cờ: ");
						endX = sc.nextInt();
						endY = sc.nextInt();
						// Kiểm tra vị trí đó có nằm trong bàn cờ hay không
						if (!isValidPosition(endX, endY)) {
							System.out.println("Vị trí không hợp lệ.");
						}
						// Kiểm tra đó có phải quân cờ của người đang chơi hay không
						else if ((board.getPiece(endX, endY) != null)
								&& (board.getPiece(endX, endY).isRed == isRedTurn)) {
							System.out.println("Không thể di chuyển quân cờ đến vị trí này.");
						} else {
							isValidValue = true;
						}
					}
					// catch khi sai định dạng số
					catch (InputMismatchException e) {
						System.out.println("Tọa độ bạn nhập đã sai định dạng, hãy nhập lại.");
						sc.nextLine();
					}
				}
				board.movePiece(startX, startY, endX, endY);

				// Kiểm tra nếu nước đi này khiến người chơi bị chiếu
				if (isCheck(isRedTurn)) {
					System.out.println("Nước đi này khiến bạn bị chiếu. Hãy nhập lại.");
					board.movePiece(endX, endY, startX, startY); // Hoàn tác nước đi
					continue; // Bỏ qua phần còn lại của vòng lặp và yêu cầu nhập lại
				}

				System.out.println("Đã di chuyển quân cờ ở vị trí " + "(" + startX + ", " + startY + ") đến vị trí "
						+ "(" + endX + ", " + endY + ")");

				// Lưu nước đi vào lịch sử
				historyMove.add("Bên " + (isRedTurn ? "đỏ :" : "đen:") + " đã di chuyển cờ ở vị trí " + "(" + startX
						+ ", " + startY + ") đến vị trí " + "(" + endX + ", " + endY + ")");

				// Đổi lượt
				isRedTurn = !isRedTurn;

				// Kiểm tra nếu tướng của bất kỳ bên nào đã bị ăn
				if (board.findKing(isRedTurn) == null) {
					System.out.println("BÊN " + (isRedTurn ? " ĐỎ" : " ĐEN") + " ĐÃ THUA!");
					isRunning = false;
				}
				break;

			// In ra lịch sử di chuyển
			case 2:
				printHistoryMove();
				break;
			}
		}
		sc.close();
	}

	// Kiểm tra vị trí đó có nằm trong bàn cờ hay không
	private boolean isValidPosition(int x, int y) {
		return x >= 0 && x < 10 && y >= 0 && y < 9;
	}

	// Kiểm tra bên đang đi có bị chiếu hay không
	private boolean isCheck(boolean isRedTurn) {
		Piece king = board.findKing(isRedTurn);
		if (king == null)
			return true; // Nếu không tìm thấy tướng thì xem như đã thua

		// Duyệt qua bàn cờ để tìm quân đối thủ có thể chiếu tướng
		for (int i = 0; i < board.getRow(); i++) {
			for (int j = 0; j < board.getCol(); j++) {
				Piece piece = board.getPiece(i, j);
				// Kiểm tra quân đối thủ có thể chiếu tướng không
				if (piece != null && piece.isRed != isRedTurn && piece.isValidMove(king.x, king.y, board)) {
					return true;
				}
			}
		}
		// Nếu không có thì trả về false
		return false;
	}

	private boolean isCheckMate(boolean isRedTurn) {
		// Duyệt qua toàn bộ bàn cờ
		for (int i = 0; i < board.getRow(); i++) {
			for (int j = 0; j < board.getCol(); j++) {
				// Chọn quân cờ do người chơi điều khiển
				Piece piece = board.getPiece(i, j);
				if (piece != null && piece.isRed == isRedTurn) {
					// Duyệt hết toàn bộ bàn cờ để tìm vị trí quân cờ của người chơi có thể di
					// chuyển đến
					for (int targetX = 0; targetX < board.getRow(); targetX++) {
						for (int targetY = 0; targetY < board.getCol(); targetY++) {
							if (piece.isValidMove(targetX, targetY, board)) {
								// Thử di chuyển
								board.movePiece(i, j, targetX, targetY);
								boolean isStillInCheck = isCheck(isRedTurn);
								// Khôi phục vị trí
								board.movePiece(targetX, targetY, i, j);
								// Kiểm tra nếu nước đi đó giúp người chơi thoát khỏi thế bí thì trả về false
								if (!isStillInCheck) {
									return false;
								}
							}
						}
					}
				}
			}
		}
		// Không tìm được quân cờ có nước đi phù hợp thì trả về true -> chiếu bí
		return true;
	}

	// In ra lịch sử các nước đi
	public void printHistoryMove() {
		if (historyMove.size() == 0) {
			System.out.println("Không có nước đi được lưu lại.");
			return;
		}
		System.out.println("Lịch sử các nước đi");
		for (String move : historyMove) {
			System.out.println(move);
		}
	}
}
