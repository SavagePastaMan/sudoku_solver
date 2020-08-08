import java.util.*;

public class Solver {
	private static Integer[][] board;

	public void solve(Integer[][] board) {
		Solver.board = board;

//		System.out.println(board[0][1]);
//		System.out.println(possibleValues(0, 2));
//		System.out.println(bestSquares());

		dfs();
	}

	public int solutionCount(Integer[][] board) {
		Solver.board = board;
		return solCDFS();
	}

	public boolean solutionExists(Integer[][] board) {
		Solver.board = deepCopy(board);
		return dfs();
	}

	private int solCDFS() {
		int i = 0;
		if (boardFilled()) {
			i++;
		}

		ArrayList<Integer> t = bestSquare();
		int row = t.get(1);
		int col = t.get(2);

		for (Integer a : possibleValues(row, col)) {
			board[row][col] = a;
			i += solCDFS();
			board[row][col] = 0;
		}

		return i;
	}

	private boolean dfs() {
//		System.out.println(i);
		if (boardFilled()) {
			return true;
		}

		ArrayList<Integer> t = bestSquare();
		int row = t.get(1);
		int col = t.get(2);

//		System.out.println("row: " + row + " col: " + col);

		for (Integer a : possibleValues(row, col)) {
			board[row][col] = a;
//				System.out.println(a);
			if (dfs()) {
				return true;
			}
			board[row][col] = 0;
		}

		return false;
	}

	/**
	 *
	 * @return - array list of squares, sorted by the amount of possibleValues for that square
	 */
	private ArrayList<Integer> bestSquare() {
		ArrayList<ArrayList<Integer>> t = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j] == 0) {
					t.add(new ArrayList<>(Arrays.asList(possibleValues(i, j).size(), i, j)));
				} else {
					t.add(new ArrayList<>(Arrays.asList(10, i, j)));
				}
			}
		}

		int min = t.get(0).get(0);
		ArrayList<Integer> r = new ArrayList<>();
		for (ArrayList<Integer> a : t) {
			if (a.get(0) <= min) {
				min = a.get(0);
				r = a;
			} else if (a.get(0) == 10) {
				break;
			}
		}

		return r;
	}

	/**
	 * gives the values that could possibly be in this square
	 * @param i - row of board
	 * @param j - col of board
	 * @return - possible numbers that could go in this square
	 * precondition - only called when square is valid
	 */
	private Set<Integer> possibleValues(int i, int j) {
		Set<Integer> full = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

		Set<Integer> A = new HashSet<>(Arrays.asList(board[i]));

		ArrayList<Integer> t = new ArrayList<>();
		for (int k = 0; k < 9; k++) {
			t.add(board[k][j]);
		}

		Set<Integer> B = new HashSet<>(t);

		Set<Integer> C = new HashSet<>();
		Integer[][] fs = new Integer[][]{new Integer[]{0, 1, 2}, new Integer[]{3, 4, 5}, new Integer[]{6, 7, 8}};

		Integer[] row = new Integer[3], col = new Integer[3];
		for (int k = 0; k < 3; k++) {
			for (int l = 0; l < 3; l++) {
				if (fs[k][l] == i) {
					row = fs[k];
				}
				if (fs[k][l] == j) {
					col = fs[k];
				}
			}
		}

		for (int k = 0; k < 3; k++) {
			for (int l = 0; l < 3; l++) {
				C.add(board[row[k]][col[l]]);
			}
		}

//		System.out.println(A);
//		System.out.println(B);
//		System.out.println(C);

		full.removeAll(A);
		full.removeAll(B);
		full.removeAll(C);

		return full;
	}

	private boolean boardFilled() {
		for (int i = 0; i < 9; i++) {
			if (!rowFilled(i)) {
				return false;
			}
		}

		return true;
	}

	private boolean rowFilled(int row) {
		for (int i = 0; i < 9; i++) {
			if (board[row][i] == 0) {
				return false;
			}
		}

		return true;
	}

	private static Integer[][] deepCopy(Integer[][] board) {
		Integer[][] temp = new Integer[9][9];
		for (int i = 0; i < 9; i++) {
			System.arraycopy(board[i], 0, temp[i], 0, 9);
		}

		return temp;
	}
}