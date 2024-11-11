package code;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
	private Board board;
	private boolean isRedTurn;
	private boolean isValidValue;
	private int yourChoice;
	private ArrayList<String> historyMove;
	// Mã màu vàng dành cho những cảnh báo
	private String yellow = "\u001B[33m";
	// mã màu đỏ, tượng trưng cho quân đỏ
	private String red = "\u001B[31m";
	// mã màu xanh tượng trưng cho quân xanh
	private String blue = "\u001B[34m";
	// Mã để reset màu
	private String reset = "\u001B[0m";

	public Game() {
		board = new Board();
		historyMove = new ArrayList<>();
		// Bên đỏ đi trước
		isRedTurn = true;
	}

	public void runGame() {
		Scanner sc = new Scanner(System.in);
		int startX, startY, endX, endY;

		while (true) {
			System.out.print('\n');
			board.drawBoard();
			System.out.println("Lượt của bên " + (isRedTurn ? red + "đỏ" + reset : blue + "xanh" + reset));

			// Kiểm tra xem người chơi hiện tại có bị chiếu hay không
			if (board.isInCheck(isRedTurn)) {
				System.out.println(yellow + "NGƯỜI CHƠI " + (isRedTurn ? "ĐỎ" : "XANH") + " ĐANG BỊ CHIẾU!!" + reset);
				// Kiểm tra xem người chơi hiện tại có đang chiếu bí hay không
				if (board.isInCheckMate(isRedTurn)) {
					System.out.println(
							yellow + "NGƯỜI CHƠI " + (!isRedTurn ? "ĐỎ" : "XANH") + " THẮNG DO CHIẾU BÍ!!" + reset);
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
					System.out.println("Hãy chọn hành động:");
					System.out.println("[1] Di chuyển quân cờ");
					System.out.println("[2] Xem lại lịch sử di chuyển");
					System.out.print("Lựa chọn của bạn là: ");

					String input = sc.nextLine().trim();

					if (input.isEmpty() || input.length() != 1) {
						System.out.println("Sai định dạng, hãy nhập lại");
						continue;
					}
					yourChoice = Integer.parseInt(input);

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
			case 1:
				isValidValue = false;
				while (!isValidValue) {
					System.out.print('\n');
					board.drawBoard();
					try {
						System.out.print("Nhập vào tọa độ quân cờ bạn muốn di chuyển: ");
						startX = sc.nextInt() - 1;
						startY = sc.nextInt() - 1;
						if (!isValidPosition(startX, startY)) {
							System.out.println("Vị trí không hợp lệ.");
						} else if (board.getPiece(startX, startY) == null) {
							System.out.println("Vị trí bạn chọn không có quân cờ nào.");
						} else if (board.getPiece(startX, startY).isRed != isRedTurn) {
							System.out.println("Quân cờ đó không phải của bạn.");
						} else {
							isValidValue = true;
						}
					} catch (InputMismatchException e) {
						System.out.println("Tọa độ bạn nhập đã sai định dạng, hãy nhập lại.");
						sc.nextLine();
					}
				}
				Piece piece = board.getPiece(startX, startY);
				// In ra các nước đi hợp lệ với quân cờ người chơi đã chọn
				System.out.println("Các nước đi hợp lệ với quân cờ bạn chọn là: ");
				for (int i = 0; i < board.getRow(); i++) {
					for (int j = 0; j < board.getCol(); j++) {
						if (piece.isValidMove(i, j, board)) {
							System.out.println("(" + (i + 1) + ", " + (j + 1) + ")");
						}
					}
				}
				// Nhập tọa độ muốn đặt quân cờ
				isValidValue = false;
				while (!isValidValue) {
					try {
						System.out.print("Nhập vào tọa độ bạn muốn đặt quân cờ: ");
						endX = sc.nextInt() - 1;
						endY = sc.nextInt() - 1;
						if (!isValidPosition(endX, endY) || !piece.isValidMove(endX, endY, board)) {
							System.out.println("Vị trí không hợp lệ.");
						} else if ((board.getPiece(endX, endY) != null)
								&& (board.getPiece(endX, endY).isRed == isRedTurn)) {
							System.out.println("Không thể di chuyển quân cờ đến vị trí này.");
						} else {
							isValidValue = true;
						}
					} catch (InputMismatchException e) {
						System.out.println("Tọa độ bạn nhập đã sai định dạng, hãy nhập lại.");
						sc.nextLine();
					}
				}
				// Lưu lại quân cờ mục tiêu
				Piece targetPiece = board.getPiece(endX, endY);
				// Di chuyển quân cờ
				board.movePiece(startX, startY, endX, endY);
				// Kiểm tra xem nước đi đó có dẫn đến chiếu tướng cho người chơi hay không
				if (board.isInCheck(isRedTurn)) {
					System.out.println(yellow + "\nNƯỚC ĐI NÀY KHIẾN BẠN BỊ CHIẾU, HÃY XEM XÉT LẠI" + reset);
					// Nếu nước đi khiến người chơi bị chiếu
					// Hoàn tác nước đi và đặt lại quân cờ mục tiêu đã bị ăn mất (nếu có)
					board.setPiece(startX, startY, piece);
					if (targetPiece != null)
						board.setPiece(endX, endY, targetPiece);
					else
						board.setPiece(endX, endY, null);
					sc.nextLine();
					continue;
				}
				System.out.println("Đã di chuyển quân cờ ở vị trí " + "(" + (startX + 1) + ", " + (startY + 1)
						+ ") đến vị trí " + "(" + (endX + 1) + ", " + (endY + 1) + ")");
				// Lưu nước đi vào lịch sử
				historyMove
						.add("Bên " + (isRedTurn ? "đỏ :" : "xanh:") + " đã di chuyển cờ ở vị trí " + "(" + (startX + 1)
								+ ", " + (startY + 1) + ") đến vị trí " + "(" + (endX + 1) + ", " + (endY + 1) + ")");
				// Đổi lượt đi
				isRedTurn = !isRedTurn;
				sc.nextLine();
				break;
			case 2:
				printHistoryMove();
				break;
			}
		}
		sc.close();
	}

	// Hàm kiểm tra vị trí có hợp lệ hay không
	private boolean isValidPosition(int x, int y) {
		return x >= 0 && x < 10 && y >= 0 && y < 9;
	}

	// In lịch sử các nước đi
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