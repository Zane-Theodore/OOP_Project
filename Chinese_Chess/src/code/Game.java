package code;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
	private Board board;
	private boolean isRedTurn;
	private boolean isValidValue;
	private ArrayList<String> moveHistory;
	// Khởi tạo tọa độ x cho quân cờ
	private int startX = -1;
	// Khởi tạo tọa độ y cho quân cờ
	private int startY = -1;
	// Tọa độ x mà quân cờ cần di chuyển đến
	private int endX = -1;
	// Tọa độ y mà quân cờ cần di chuyển đến
	private int endY = -1;
	// Đại diện cho quân cờ hiện tại
	private Piece piece;
	// Đại diện cho quân cờ mục tiêu
	private Piece targetPiece;

	public Game() {
		board = new Board();
		moveHistory = new ArrayList<>();
		// Bên đỏ đi trước, xanh đi sau
		isRedTurn = true;
	}

	public void runGame() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			// Kiểm tra trạng thái chiếu hoặc chiếu bí của bàn cờ
			if (checkGameStatus()) {
				// Xuất hiện chiếu bí, kết thúc trò chơi
				break;
			}

			// Lựa chọn hành động
			int yourChoice = getChoice(sc);

			switch (yourChoice) {
			// case 1: di chuyển quân cờ
			// case 2: in ra lịch sử nước đi
			case 1:
				// Nhập tọa độ quân cờ muốn di chuyển
				inputStartPiece(sc);
				// Lưu lại quân cờ
				piece = board.getPiece(startX, startY);

				// In ra các nước đi hợp lệ với quân cờ người chơi đã chọn
				printValidMove();

				// Nhập tọa độ muốn đặt quân cờ
				inputTargetPiece(sc);
				// Lưu lại quân cờ mục tiêu
				targetPiece = board.getPiece(endX, endY);

				// Di chuyển quân cờ và kiểm tra độ an toàn của nước đi đó
				if (movePieceAndCheckMove()) {
					// Nếu gây ra chiếu thì lựa chọn lại
					continue;
				}

				// Lưu nước đi vào lịch sử
				saveMove("Bên " + (isRedTurn ? "đỏ: " : "xanh: ") + printMessage());

				// Đổi lượt đi
				isRedTurn = !isRedTurn;
				break;

			case 2:
				printMoveHistory();
				break;
			}
		}
		sc.close();
	}

	private void setIsValidValue() {
		this.isValidValue = false;
	}

	// Hàm kiểm tra vị trí có hợp lệ hay không
	private boolean isValidPosition(int x, int y) {
		return x >= 0 && x < 10 && y >= 0 && y < 9;
	}

	// Hàm in lịch sử các nước đi
	private void printMoveHistory() {
		if (moveHistory.size() == 0) {
			System.out.println("\nKhông có nước đi được lưu lại.");
			return;
		}
		System.out.println("\nLịch sử các nước đi: ");
		for (String move : moveHistory) {
			System.out.println(move);
		}
	}

	// Hàm lưu lại lịch sử các nước đi
	private void saveMove(String move) {
		moveHistory.add(move);
	}

	// In ra trạng thái hiện tại của bàn cờ và thông báo đến lượt của bên nào
	private void printGameStatus() {
		System.out.print('\n');
		board.drawBoard();
		System.out.println(
				"Lượt của bên " + (isRedTurn ? Color.red + "đỏ" + Color.reset : Color.blue + "xanh" + Color.reset));
	}

	// Kiểm tra tình trạng chiếu của ván cờ
	private boolean checkGameStatus() {
		printGameStatus();
		// Kiểm tra xem người chơi hiện tại có đang chiếu bí hay không,
		// nếu có trả về true
		if (board.isInCheckMate(isRedTurn)) {
			System.out.print(
					Color.yellow + "NGƯỜI CHƠI " + (!isRedTurn ? "ĐỎ" : "XANH") + " THẮNG DO CHIẾU BÍ!!" + Color.reset);
			return true;
		}
		// Kiểm tra xem người chơi hiện tại có đang bị chiếu hay không
		if (board.isInCheck(isRedTurn)) {
			System.out.println(
					Color.yellow + "NGƯỜI CHƠI " + (isRedTurn ? "ĐỎ" : "XANH") + " ĐANG BỊ CHIẾU!!" + Color.reset);
		}
		// Nếu không có chiếu bí thì trả về false
		return false;
	}

	// Hàm in ra menu lựa chọn
	private void printMenu() {
		System.out.println("Hãy chọn hành động:");
		System.out.println("[1] Di chuyển quân cờ");
		System.out.println("[2] Xem lại lịch sử di chuyển");
	}

	// Hàm lựa chọn hành động trong mỗi lượt chơi
	private int getChoice(Scanner sc) {
		setIsValidValue();
		String input;
		int yourChoice = -1;
		while (!isValidValue) {
			try {
				printMenu();
				System.out.print("Lựa chọn của bạn là: ");
				input = sc.next();
				sc.nextLine();

				if (input.isEmpty()) {
					System.out.println(Color.lightYellow + "\nKhông được bỏ trống lựa chọn.\n" + Color.reset);
					printGameStatus();
					continue;
				}
				yourChoice = Integer.parseInt(input);
				if (yourChoice >= 1 && yourChoice <= 2) {
					isValidValue = true;
				} else {
					System.out.println(Color.lightYellow + "\nLựa chọn không hợp lệ, hãy chọn lại.\n" + Color.reset);
					printGameStatus();
				}
			} catch (NumberFormatException e) {
				System.out.println(Color.lightYellow + "\nSai định dạng, hãy nhập lại.\n" + Color.reset);
				printGameStatus();
			}
		}
		return yourChoice;
	}

	// Hàm nhập tọa độ cho quân cờ người chơi muốn di chuyển
	private void inputStartPiece(Scanner sc) {
		setIsValidValue();
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
					System.out.println(Color.lightYellow + "Vị trí bạn chọn không có quân cờ nào." + Color.reset);
				} else if (board.getPiece(startX, startY).isRed != isRedTurn) {
					System.out.println(Color.lightYellow + "Quân cờ đó không phải của bạn." + Color.reset);
				} else {
					isValidValue = true;
				}
			} catch (InputMismatchException e) {
				System.out.println(Color.lightYellow + "Tọa độ bạn nhập đã sai định dạng, hãy nhập lại." + Color.reset);
				sc.nextLine();
			}
		}
	}

	// Hàm nhập tọa độ người chơi muốn đặt quân cờ
	private void inputTargetPiece(Scanner sc) {
		setIsValidValue();
		while (!isValidValue) {
			try {
				System.out.print("Nhập vào tọa độ bạn muốn đặt quân cờ: ");
				endX = sc.nextInt() - 1;
				endY = sc.nextInt() - 1;
				if (!isValidPosition(endX, endY) || !piece.isValidMove(endX, endY, board)) {
					System.out.println(Color.lightYellow + "Vị trí không hợp lệ." + Color.reset);
				} else if ((board.getPiece(endX, endY) != null) && (board.getPiece(endX, endY).isRed == isRedTurn)) {
					System.out.println(Color.lightYellow + "Không thể di chuyển quân cờ đến vị trí này." + Color.reset);
				} else {
					isValidValue = true;
				}
			} catch (InputMismatchException e) {
				System.out.println(Color.lightYellow + "Tọa độ bạn nhập đã sai định dạng, hãy nhập lại." + Color.reset);
				sc.nextLine();
			}
		}
	}

	// Hàm in ra các nước đi hợp lệ với quân cờ đã chọn
	private void printValidMove() {
		System.out.println("Các nước đi hợp lệ với quân cờ bạn chọn là: ");
		for (int i = 0; i < board.getRow(); i++) {
			for (int j = 0; j < board.getCol(); j++) {
				if (piece.isValidMove(i, j, board)) {
					System.out.println("(" + (i + 1) + ", " + (j + 1) + ")");
				}
			}
		}
	}

	// In ra thông báo nước đi
	private String printMessage() {
		String message = "Đã di chuyển quân cờ ở vị trí " + "(" + (startX + 1) + ", " + (startY + 1) + ") đến vị trí "
				+ "(" + (endX + 1) + ", " + (endY + 1) + ")";
		System.out.println(message);
		return message;
	}

	// Di chuyển quân cờ và kiểm tra chiếu sau nước đi đó
	private boolean movePieceAndCheckMove() {
		board.movePiece(startX, startY, endX, endY);
		if (board.isInCheck(isRedTurn)) {
			System.out.println(Color.yellow + "\nNƯỚC ĐI NÀY KHIẾN BẠN BỊ CHIẾU, HÃY XEM XÉT LẠI" + Color.reset);
			// Nếu nước đi khiến người chơi bị chiếu
			// Hoàn tác nước đi và đặt lại quân cờ mục tiêu đã bị ăn mất (nếu có)
			board.setPiece(startX, startY, piece);
			if (targetPiece != null)
				board.setPiece(endX, endY, targetPiece);
			else
				board.setPiece(endX, endY, null);
			return true;
		}
		return false;
	}
}