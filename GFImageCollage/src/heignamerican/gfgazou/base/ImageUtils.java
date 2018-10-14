package heignamerican.gfgazou.base;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * 画像変換系の共通処理
 */
public class ImageUtils {
	/**
	 * 時計回りに直角だけ回転させた新しい画像を生成して返す。
	 *
	 * @param img
	 *            元の画像
	 * @return
	 */
	public static BufferedImage rotateClockwise90Degree(final BufferedImage img) {
		final BufferedImage result = new BufferedImage(img.getHeight(), img.getWidth(), img.getType());

		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				result.setRGB(img.getHeight() - y - 1, x, img.getRGB(x, y));
			}
		}
		return result;
	}

	/**
	 * 反時計回りに直角だけ回転させた新しい画像を生成して返す。
	 *
	 * @param img
	 *            元の画像
	 * @return
	 */
	public static BufferedImage rotateCounterClockwise90Degree(final BufferedImage img) {
		final BufferedImage result = new BufferedImage(img.getHeight(), img.getWidth(), img.getType());

		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				result.setRGB(y, img.getWidth() - x - 1, img.getRGB(x, y));
			}
		}
		return result;
	}

	/**
	 * 縦方向に切り取った新しい画像を生成する。
	 *
	 * <p>
	 * 元画像の中央部分が切り取られるような位置が選択される。
	 *
	 * @param img
	 *            元の画像
	 * @param newHeight
	 *            新しい画像の高さ。もとの高さより小さいこと
	 * @return
	 */
	public static BufferedImage cutOffHeightLeavingCenter(final BufferedImage img, final int newHeight) {
		final BufferedImage result = new BufferedImage(img.getWidth(), newHeight, img.getType());

		for (int y = 0; y < result.getHeight(); y++) {
			for (int x = 0; x < result.getWidth(); x++) {
				final int offsetY = (img.getHeight() - result.getHeight()) / 2;
				result.setRGB(x, y, img.getRGB(x, y + offsetY));
			}
		}
		return result;
	}

	/**
	 * 横方向に切り取った新しい画像を生成する。
	 *
	 * <p>
	 * 元画像の中央部分が切り取られるような位置が選択される。
	 *
	 * @param img
	 *            元の画像
	 * @param newWidth
	 *            新しい画像の幅。もとの幅より小さいこと
	 * @return
	 */
	public static BufferedImage cutOffWidthLeavingCenter(final BufferedImage img, final int newWidth) {
		final BufferedImage result = new BufferedImage(newWidth, img.getHeight(), img.getType());

		for (int y = 0; y < result.getHeight(); y++) {
			for (int x = 0; x < result.getWidth(); x++) {
				final int offsetX = (img.getWidth() - result.getWidth()) / 2;
				result.setRGB(x, y, img.getRGB(x + offsetX, y));
			}
		}
		return result;
	}

	/**
	 * 指定した割合で画像を拡大率変換する。
	 *
	 * <p>
	 * アフィン変換するよ
	 *
	 * @param img
	 *            元の画像
	 * @param scale
	 *            幅・高さ方向両方で同一の拡大率
	 * @return
	 */
	public static BufferedImage scale(final BufferedImage img, final double scale) {
		return scale(img, scale, scale);
	}

	/**
	 * 指定した割合で画像を拡大率変換する。
	 *
	 * <p>
	 * アフィン変換するよ
	 *
	 * @param img
	 *            元の画像
	 * @param scaleX
	 *            幅方向の拡大率
	 * @param scaleY
	 *            高さ方向の拡大率
	 * @return
	 */
	public static BufferedImage scale(final BufferedImage img, final double scaleX, final double scaleY) {
		final AffineTransform affine = new AffineTransform();
		affine.scale(scaleX, scaleY);
		final AffineTransformOp affineTransformOp = new AffineTransformOp(affine, AffineTransformOp.TYPE_BILINEAR);

		final BufferedImage after = new BufferedImage((int) (img.getWidth() * scaleX), (int) (img.getHeight() * scaleY), BufferedImage.TYPE_INT_ARGB);
		affineTransformOp.filter(img, after);
		return after;
	}

	/**
	 * 画像を重ねる。
	 *
	 * @param base
	 *            重ねられる方。 破壊的変更
	 * @param add
	 *            重ねる方。
	 */
	public static void overlay(final BufferedImage base, final BufferedImage add) {
		final Graphics2D graphics2d = base.createGraphics();
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.drawImage(add, 0, 0, null);
	}

	/**
	 * 射影変換して変換先の画像に乗せる
	 *
	 * @param source
	 *            変換元の画像
	 * @param fromX1
	 *            変換元 左上のX座標
	 * @param fromY1
	 *            変換元 左上のy座標
	 * @param fromX2
	 *            変換元 左下のX座標
	 * @param fromY2
	 *            変換元 左下のy座標
	 * @param fromX3
	 *            変換元 右下のX座標
	 * @param fromY3
	 *            変換元 右下のy座標
	 * @param fromX4
	 *            変換元 右上のX座標
	 * @param fromY4
	 *            変換元 右上のy座標
	 * @param target
	 *            変換先を描画する画像
	 * @param toX1
	 *            変換先 左上のX座標
	 * @param toY1
	 *            変換先 左上のy座標
	 * @param toX2
	 *            変換先 左下のX座標
	 * @param toY2
	 *            変換先 左下のy座標
	 * @param toX3
	 *            変換先 右下のX座標
	 * @param toY3
	 *            変換先 右下のy座標
	 * @param toX4
	 *            変換先 右上のX座標
	 * @param toY4
	 *            変換先 右上のy座標
	 */
	public static void transform(
			final BufferedImage source,
			final int fromX1, final int fromY1,
			final int fromX2, final int fromY2,
			final int fromX3, final int fromY3,
			final int fromX4, final int fromY4,
			final BufferedImage target,
			final int toX1, final int toY1,
			final int toX2, final int toY2,
			final int toX3, final int toY3,
			final int toX4, final int toY4) {

		final double[][] matrixA = {
				{ fromX1, fromY1, 1, 0, 0, 0, -toX1 * fromX1, -toX1 * fromY1 },
				{ fromX2, fromY2, 1, 0, 0, 0, -toX2 * fromX2, -toX2 * fromY2 },
				{ fromX3, fromY3, 1, 0, 0, 0, -toX3 * fromX3, -toX3 * fromY3 },
				{ fromX4, fromY4, 1, 0, 0, 0, -toX4 * fromX4, -toX4 * fromY4 },
				{ 0, 0, 0, fromX1, fromY1, 1, -toY1 * fromX1, -toY1 * fromY1 },
				{ 0, 0, 0, fromX2, fromY2, 1, -toY2 * fromX2, -toY2 * fromY2 },
				{ 0, 0, 0, fromX3, fromY3, 1, -toY3 * fromX3, -toY3 * fromY3 },
				{ 0, 0, 0, fromX4, fromY4, 1, -toY4 * fromX4, -toY4 * fromY4 },
		};
		final double[][] matrixR = GyakuGyouretu.gyaku(matrixA);

		final double a1 = matrixR[0][0] * toX1 + matrixR[0][1] * toX2 + matrixR[0][2] * toX3 + matrixR[0][3] * toX4 + matrixR[0][4] * toY1 + matrixR[0][5] * toY2 + matrixR[0][6] * toY3 + matrixR[0][7] * toY4;
		final double a2 = matrixR[1][0] * toX1 + matrixR[1][1] * toX2 + matrixR[1][2] * toX3 + matrixR[1][3] * toX4 + matrixR[1][4] * toY1 + matrixR[1][5] * toY2 + matrixR[1][6] * toY3 + matrixR[1][7] * toY4;
		final double a3 = matrixR[2][0] * toX1 + matrixR[2][1] * toX2 + matrixR[2][2] * toX3 + matrixR[2][3] * toX4 + matrixR[2][4] * toY1 + matrixR[2][5] * toY2 + matrixR[2][6] * toY3 + matrixR[2][7] * toY4;
		final double a4 = matrixR[3][0] * toX1 + matrixR[3][1] * toX2 + matrixR[3][2] * toX3 + matrixR[3][3] * toX4 + matrixR[3][4] * toY1 + matrixR[3][5] * toY2 + matrixR[3][6] * toY3 + matrixR[3][7] * toY4;
		final double a5 = matrixR[4][0] * toX1 + matrixR[4][1] * toX2 + matrixR[4][2] * toX3 + matrixR[4][3] * toX4 + matrixR[4][4] * toY1 + matrixR[4][5] * toY2 + matrixR[4][6] * toY3 + matrixR[4][7] * toY4;
		final double a6 = matrixR[5][0] * toX1 + matrixR[5][1] * toX2 + matrixR[5][2] * toX3 + matrixR[5][3] * toX4 + matrixR[5][4] * toY1 + matrixR[5][5] * toY2 + matrixR[5][6] * toY3 + matrixR[5][7] * toY4;
		final double a7 = matrixR[6][0] * toX1 + matrixR[6][1] * toX2 + matrixR[6][2] * toX3 + matrixR[6][3] * toX4 + matrixR[6][4] * toY1 + matrixR[6][5] * toY2 + matrixR[6][6] * toY3 + matrixR[6][7] * toY4;
		final double a8 = matrixR[7][0] * toX1 + matrixR[7][1] * toX2 + matrixR[7][2] * toX3 + matrixR[7][3] * toX4 + matrixR[7][4] * toY1 + matrixR[7][5] * toY2 + matrixR[7][6] * toY3 + matrixR[7][7] * toY4;

		final int width = source.getWidth();
		final int height = source.getHeight();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				final double newX = (a1 * x + a2 * y + a3) / (a7 * x + a8 * y + 1);
				final double newY = (a4 * x + a5 * y + a6) / (a7 * x + a8 * y + 1);
				if (newX < 0 || newX >= target.getWidth() || newY < 0 || newY >= target.getHeight()) {
					// System.out.printf("(%d,%d,) -> (%f,%f)%n", x, y, newX, newY);
				} else {
					target.setRGB((int) newX, (int) newY, source.getRGB(x, y)); // 補正なし、近い位置から取得してるだけ
				}
			}
		}
	}
}
