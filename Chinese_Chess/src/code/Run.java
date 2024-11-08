package code;

import java.util.Scanner;

public class Run {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String button;
		System.out.println("Lưu ý trước khi chơi: ");
		System.out.print("Tọa độ các quân cờ được đánh từ 0 -> 8 đối với hàng ngang và 0 -> 9 đối với hàng dọc\n"
				+ "Các quân cờ được viết tắt ở dạng chữ cái đầu trong tên:\n" + "Tướng: General  (G / g)\n"
				+ "Sĩ   : Advisor  (A / a)\n" + "Tượng: Elephant (E / e\n" + "Mã   : Hourse   (H / h\n"
				+ "Xe   : Rook     (R / r)\n" + "Pháo : Cannon   (C / c)\n" + "Tốt  : Soldier  (S / s)\n");
		System.out.println("Bàn cờ được chia làm hai nữa, nữa trên là phe đỏ, nữa dưới là phe đen.\n"
				+ "* Các quân cờ bên phe đỏ được đánh dấu với ký tự viết hoa, trong khi phe đen thì viết thường *\n"
				+ "Khi nhập tọa độ cho các vị trí phải nhập ở định dạng x y, với x là trục dọc và y là trục ngang.");
		do {
			System.out.print("Nhập Y/y để bắt đầu trò chơi: ");
			button = sc.nextLine();

		} while (button.isEmpty() || !button.equalsIgnoreCase("y"));
		Game newGame = new Game();
		newGame.runGame();
		sc.close();
	}

}
