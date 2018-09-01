package heignamerican.gfgazou.base;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class UNextBase {
	final private Class<?> clazz;
	final private String baseFileName;
	final private String[] modosuyatura;

	public UNextBase(final Class<?> clazz, final String baseFileName, final String... modosuyatura) {
		this.clazz = clazz;
		this.baseFileName = baseFileName;
		this.modosuyatura = modosuyatura;
	}

	public BufferedImage createUNext(final BufferedImage add) throws IOException, URISyntaxException {
		final BufferedImage newImage = ImageIO.read(this.clazz.getResource(this.baseFileName));
		kasaneru(newImage, add);

		final BufferedImage base = ImageIO.read(this.clazz.getResource(this.baseFileName));
		for (final String modosuyatu : this.modosuyatura) {
			modosu(base, newImage, this.clazz.getResourceAsStream(modosuyatu));
		}
		return newImage;
	}

	public static void kasaneru(final BufferedImage target, final BufferedImage add) {
		final Graphics2D graphics2d = target.createGraphics();
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.drawImage(add, 0, 0, null);
	}

	/**
	 * source 内で指定されたすべての座標について base の RGB 値で target に戻す。
	 *
	 * @param base
	 * @param target
	 * @param source
	 *
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private static void modosu(final BufferedImage base, final BufferedImage target, final InputStream source) throws IOException, URISyntaxException {
		try (final Scanner scanner = new Scanner(source)) {
			scanner.useDelimiter(",|\n");
			while (scanner.hasNext()) {
				final int x = scanner.nextInt();
				final int y = scanner.nextInt();
				target.setRGB(x, y, base.getRGB(x, y));
			}
		} finally {
			if (source != null)
				source.close();
		}
	}

	public enum Orientation {
		/**
		 * 縦長
		 */
		Portrait,
		/**
		 * 横長
		 */
		Landscape,
	}

	public enum RotateDirection {
		/**
		 * 時計回り＝右回り
		 */
		Clockwise,

		/**
		 * 反時計回り＝左回り
		 */
		CounterClockwise,

		/**
		 * 回転不要
		 */
		None,
	}

	private static BufferedImage rotateImage(final BufferedImage source, final RotateDirection rotateDirection) {
		if (rotateDirection == RotateDirection.Clockwise) {
			return ImageUtils.rotateClockwise90Degree(source);
		} else if (rotateDirection == RotateDirection.CounterClockwise) {
			return ImageUtils.rotateCounterClockwise90Degree(source);
		} else {
			return source;
		}
	}

	/**
	 * 指定した向きに合わせて射影変換を行う。
	 *
	 * <p>
	 * 要求する向きと異なる画像の場合は、指定した方向に直角に回転させる。
	 * <p>
	 * x1～y4 は、左上・左下・右下・右上の各座標の変換先座標。
	 *
	 * @param add
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
	 * @param expectedOrientation
	 *            要求する向き
	 * @param rotateDirection
	 *            {@link RotateDirection} ではなかった場合の回転方向
	 * @return
	 */
	public BufferedImage transform(final BufferedImage add,
			final int toX1,
			final int toY1,
			final int toX2,
			final int toY2,
			final int toX3,
			final int toY3,
			final int toX4,
			final int toY4,
			final Orientation expectedOrientation,
			final RotateDirection rotateDirection) {
		return transform(add, 0, toX1, toY1, toX2, toY2, toX3, toY3, toX4, toY4, expectedOrientation, rotateDirection);
	}

	/**
	 * 指定した向きに合わせて射影変換を行う。
	 *
	 * <p>
	 * 要求する向きと異なる画像の場合は、指定した方向に直角に回転させる。
	 * <p>
	 * x1～y4 は、左上・左下・右下・右上の各座標の変換先座標。
	 *
	 * @param add
	 * @param expectedRateForCutoff
	 *            期待する元画像の縦横レート。レートが合わない場合は超過した方を切り落とす。 <br>
	 *            値は rotateDirection した後の width/height で表現する。<br>
	 *            0 または省略した場合は切り取り不要として扱う。
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
	 * @param expectedOrientation
	 *            要求する向き
	 * @param rotateDirection
	 *            {@link RotateDirection} ではなかった場合の回転方向
	 * @return
	 */
	public BufferedImage transform(final BufferedImage add,
			final double expectedRateForCutoff,
			final int toX1,
			final int toY1,
			final int toX2,
			final int toY2,
			final int toX3,
			final int toY3,
			final int toX4,
			final int toY4,
			final Orientation expectedOrientation,
			final RotateDirection rotateDirection) {

		final int bairitsu = 2;

		// 参考 http://mf-atelier.sakura.ne.jp/mf-atelier/modules/tips/index.php/program/algorithm/a6.html
		// from=x,y
		// to=X,Y
		// from: source (<- addFile)
		// to: result (<- baseFile)

		final BufferedImage sourceBeforeCut = rotateImage(add, choiseDirection(add, expectedOrientation, rotateDirection));
		final BufferedImage source = cutoffIfOver(expectedRateForCutoff, sourceBeforeCut);

		final int width = source.getWidth();
		final int height = source.getHeight();
		final int fromX1 = 0;
		final int fromY1 = 0;
		final int fromX2 = 0;
		final int fromY2 = height - 1;
		final int fromX3 = width - 1;
		final int fromY3 = height - 1;
		final int fromX4 = width - 1;
		final int fromY4 = 0;
		final double[][] matrixA = {
				{ fromX1, fromY1, 1, 0, 0, 0, -toX1 * bairitsu * fromX1, -toX1 * bairitsu * fromY1 },
				{ fromX2, fromY2, 1, 0, 0, 0, -toX2 * bairitsu * fromX2, -toX2 * bairitsu * fromY2 },
				{ fromX3, fromY3, 1, 0, 0, 0, -toX3 * bairitsu * fromX3, -toX3 * bairitsu * fromY3 },
				{ fromX4, fromY4, 1, 0, 0, 0, -toX4 * bairitsu * fromX4, -toX4 * bairitsu * fromY4 },
				{ 0, 0, 0, fromX1, fromY1, 1, -toY1 * bairitsu * fromX1, -toY1 * bairitsu * fromY1 },
				{ 0, 0, 0, fromX2, fromY2, 1, -toY2 * bairitsu * fromX2, -toY2 * bairitsu * fromY2 },
				{ 0, 0, 0, fromX3, fromY3, 1, -toY3 * bairitsu * fromX3, -toY3 * bairitsu * fromY3 },
				{ 0, 0, 0, fromX4, fromY4, 1, -toY4 * bairitsu * fromX4, -toY4 * bairitsu * fromY4 },
		};
		final double[][] matrixR = GyakuGyouretu.gyaku(matrixA);

		final double a1 = matrixR[0][0] * toX1 * bairitsu + matrixR[0][1] * toX2 * bairitsu + matrixR[0][2] * toX3 * bairitsu + matrixR[0][3] * toX4 * bairitsu + matrixR[0][4] * toY1 * bairitsu + matrixR[0][5] * toY2 * bairitsu + matrixR[0][6] * toY3 * bairitsu + matrixR[0][7] * toY4 * bairitsu;
		final double a2 = matrixR[1][0] * toX1 * bairitsu + matrixR[1][1] * toX2 * bairitsu + matrixR[1][2] * toX3 * bairitsu + matrixR[1][3] * toX4 * bairitsu + matrixR[1][4] * toY1 * bairitsu + matrixR[1][5] * toY2 * bairitsu + matrixR[1][6] * toY3 * bairitsu + matrixR[1][7] * toY4 * bairitsu;
		final double a3 = matrixR[2][0] * toX1 * bairitsu + matrixR[2][1] * toX2 * bairitsu + matrixR[2][2] * toX3 * bairitsu + matrixR[2][3] * toX4 * bairitsu + matrixR[2][4] * toY1 * bairitsu + matrixR[2][5] * toY2 * bairitsu + matrixR[2][6] * toY3 * bairitsu + matrixR[2][7] * toY4 * bairitsu;
		final double a4 = matrixR[3][0] * toX1 * bairitsu + matrixR[3][1] * toX2 * bairitsu + matrixR[3][2] * toX3 * bairitsu + matrixR[3][3] * toX4 * bairitsu + matrixR[3][4] * toY1 * bairitsu + matrixR[3][5] * toY2 * bairitsu + matrixR[3][6] * toY3 * bairitsu + matrixR[3][7] * toY4 * bairitsu;
		final double a5 = matrixR[4][0] * toX1 * bairitsu + matrixR[4][1] * toX2 * bairitsu + matrixR[4][2] * toX3 * bairitsu + matrixR[4][3] * toX4 * bairitsu + matrixR[4][4] * toY1 * bairitsu + matrixR[4][5] * toY2 * bairitsu + matrixR[4][6] * toY3 * bairitsu + matrixR[4][7] * toY4 * bairitsu;
		final double a6 = matrixR[5][0] * toX1 * bairitsu + matrixR[5][1] * toX2 * bairitsu + matrixR[5][2] * toX3 * bairitsu + matrixR[5][3] * toX4 * bairitsu + matrixR[5][4] * toY1 * bairitsu + matrixR[5][5] * toY2 * bairitsu + matrixR[5][6] * toY3 * bairitsu + matrixR[5][7] * toY4 * bairitsu;
		final double a7 = matrixR[6][0] * toX1 * bairitsu + matrixR[6][1] * toX2 * bairitsu + matrixR[6][2] * toX3 * bairitsu + matrixR[6][3] * toX4 * bairitsu + matrixR[6][4] * toY1 * bairitsu + matrixR[6][5] * toY2 * bairitsu + matrixR[6][6] * toY3 * bairitsu + matrixR[6][7] * toY4 * bairitsu;
		final double a8 = matrixR[7][0] * toX1 * bairitsu + matrixR[7][1] * toX2 * bairitsu + matrixR[7][2] * toX3 * bairitsu + matrixR[7][3] * toX4 * bairitsu + matrixR[7][4] * toY1 * bairitsu + matrixR[7][5] * toY2 * bairitsu + matrixR[7][6] * toY3 * bairitsu + matrixR[7][7] * toY4 * bairitsu;

		final BufferedImage after;
		final BufferedImage result;
		try {
			final BufferedImage newImage = ImageIO.read(this.clazz.getResource(this.baseFileName));
			after = new BufferedImage(newImage.getWidth() * bairitsu, newImage.getHeight() * bairitsu, BufferedImage.TYPE_INT_ARGB);
			result = new BufferedImage(newImage.getWidth(), newImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				final double newX = (a1 * x + a2 * y + a3) / (a7 * x + a8 * y + 1);
				final double newY = (a4 * x + a5 * y + a6) / (a7 * x + a8 * y + 1);
				if (newX < 0 || newX >= after.getWidth() || newY < 0 || newY >= after.getHeight()) {
					// System.out.printf("(%d,%d,) -> (%f,%f)%n", x, y, newX, newY);
				} else {
					after.setRGB((int) newX, (int) newY, source.getRGB(x, y)); // 補正なし、近い位置から取得してるだけ
				}
			}
		}

		final AffineTransform affine = new AffineTransform();
		affine.scale(1.0 / bairitsu, 1.0 / bairitsu);
		final AffineTransformOp scaleOp = new AffineTransformOp(affine, AffineTransformOp.TYPE_BILINEAR);
		return scaleOp.filter(after, result);
	}

	private BufferedImage cutoffIfOver(final double expectedRateForCutoff, final BufferedImage source) {
		if (expectedRateForCutoff == 0) {
			return source;
		}

		final double sourceRate = (double) source.getWidth() / source.getHeight();

		if (sourceRate > expectedRateForCutoff) {
			// 横がより長い
			final int newWidth = (int) (source.getHeight() * expectedRateForCutoff);
			return ImageUtils.cutOffWidthLeavingCenter(source, newWidth);
		} else {
			// 縦がより長い
			final int newHeight = (int) (source.getWidth() / expectedRateForCutoff);
			return ImageUtils.cutOffHeightLeavingCenter(source, newHeight);
		}
	}

	private static RotateDirection choiseDirection(final BufferedImage add, final Orientation expectedOrientation, final RotateDirection rotateDirection) {
		if (add.getWidth() == add.getHeight()) {
			return RotateDirection.None;
		}

		final Orientation actualOrientation = (add.getWidth() < add.getHeight()) ? Orientation.Portrait : Orientation.Landscape;

		return (actualOrientation == expectedOrientation) ? RotateDirection.None : rotateDirection;
	}
}
