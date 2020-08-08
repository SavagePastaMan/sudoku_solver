import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BoardGenerator {
	public static void main(String[] args) {
		printBoards(3);
	}

	static Integer[][] generateBoard() {
		Integer[][] board = new Integer[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				board[i][j] = 0;
			}
		}

		fillBoard(board);

		Solver s = new Solver();
		for (int i = 0; i < 1; i++) {

			int row = (int) (Math.random() * 8);
			int col = (int) (Math.random() * 8);
			int t = board[row][col];
			board[row][col] = 0;

			if (!s.solutionExists(board) && s.solutionCount(board) != 1) {
				board[row][col] = t;
			}
		}

		return board;
	}

	static void fillBoard(Integer[][] board) {
		Solver s = new Solver();
		board[(int) (Math.random() * 8)][(int) (Math.random() * 8)] = (int) (Math.random() * 8) + 1;
		s.solve(board);
	}

	static void printBoards(int n) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter("src\\boards.txt"));
			out.println(n);

			for (int i = 0; i < n; i++) {
				out.print(formatBoard(generateBoard()));
			}

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static String formatBoard(Integer[][] board) {
		StringBuilder a = new StringBuilder();
		for (Integer[] i : board) {
			for (Integer j : i) {
				a.append(j).append(" ");
			}
			a.append("\n");
		}

		return a.toString();
	}
}