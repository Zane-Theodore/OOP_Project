package code;

import java.util.Scanner;

public class Run_Chinese_Chess {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String button;
		System.out.println("Lưu ý trước khi chơi: ");
		System.out.print("Tọa độ các quân cờ được đánh từ 0 -> 8 đối với hàng ngang và 0 -> 9 đối với hàng dọc\n"
				+ "Các quân cờ được viết tắt ở dạng chữ cái đầu trong tên:\n" + "Tướng: General  (G)\n"
				+ "Sĩ   : Advisor  (A)\n" + "Tượng: Elephant (E)\n" + "Mã   : Hourse   (H)\n" + "Xe   : Rook     (R)\n"
				+ "Pháo : Cannon   (C)\n" + "Tốt  : Soldier  (S)\n");
		System.out.println(
				"Khi nhập tọa độ cho các vị trí phải nhập ở định dạng x y, với x là trục dọc và y là trục ngang.");
		do {
			System.out.print("Nhập Y/y để bắt đầu trò chơi: ");
			button = sc.nextLine();
			if (button.isEmpty() || !button.equalsIgnoreCase("y"))
				System.out.println("Nhập lại.");
		} while (button.isEmpty() || !button.equalsIgnoreCase("y"));
		// Bắt đầu trò chơi
		Game newGame = new Game();
		newGame.runGame();
		sc.close();
	}

}
