package heignamerican.gfgazou.base;

/**
 * http://www.geocities.jp/java_sample_program/GyakuGyouretu.html
 */
public class GyakuGyouretu {

	// pivotは、消去演算を行う前に、対象となる行を基準とし、それ以降の
	// 行の中から枢軸要素の絶対値が最大となる行を見つけ出し、対象の行と
	// その行とを入れ替えることを行う関数である。
	private static void pivot(final int k, final double[][] a, final int n) {
		double max, copy;
		// ipは絶対値最大となるk列の要素の存在する行を示す変数で、
		// とりあえずk行とする
		int ip = k;
		// k列の要素のうち絶対値最大のものを示す変数maxの値をとりあえず
		// max=|a[k][k]|とする
		max = Math.abs(a[k][k]);
		// k+1行以降、最後の行まで、|a[i][k]|の最大値とそれが存在する行を
		// 調べる
		for (int i = k + 1; i < n; i++) {
			if (max < Math.abs(a[i][k])) {
				ip = i;
				max = Math.abs(a[i][k]);
			}
		}
		if (ip != k) {
			for (int j = 0; j < 2 * n; j++) {
				// 入れ替え作業
				copy = a[ip][j];
				a[ip][j] = a[k][j];
				a[k][j] = copy;
			}
		}
	}

	// ガウス・ジョルダン法により、消去演算を行う
	private static void sweep(final int k, final double[][] a, final int n) {
		double piv, mmm;
		// 枢軸要素をpivとおく
		piv = a[k][k];
		// k行の要素をすべてpivで割る a[k][k]=1となる
		for (int j = 0; j < 2 * n; j++)
			a[k][j] = a[k][j] / piv;
		//
		for (int i = 0; i < n; i++) {
			mmm = a[i][k];
			// a[k][k]=1で、それ以外のk列要素は0となる
			// k行以外
			if (i != k) {
				// i行において、k列から2N-1列まで行う
				for (int j = k; j < 2 * n; j++)
					a[i][j] = a[i][j] - mmm * a[k][j];
			}
		}
	}

	// 逆行列を求める演算
	public static double[][] gyaku(final double[][] input) {
		final int n = input.length;

		final double[][] a = new double[n][];
		for (int y = 0; y < n; y++) {
			a[y] = new double[2 * n];
			for (int x = 0; x < n; x++) {
				a[y][x] = input[y][x];
				a[y][x + n] = (y == x) ? 1.0 : 0.0;
			}
		}

		for (int k = 0; k < n; k++) {
			pivot(k, a, n);
			sweep(k, a, n);
		}

		final double[][] result = new double[n][];
		for (int y = 0; y < n; y++) {
			result[y] = new double[n];
			for (int x = 0; x < n; x++) {
				result[y][x] = a[y][x + n];
			}
		}
		return result;
	}
}
