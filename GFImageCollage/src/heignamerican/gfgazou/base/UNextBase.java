package heignamerican.gfgazou.base;

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
		ImageUtils.overlay(newImage, add);

		final BufferedImage base = ImageIO.read(this.clazz.getResource(this.baseFileName));
		for (final String modosuyatu : this.modosuyatura) {
			modosu(base, newImage, this.clazz.getResourceAsStream(modosuyatu));
		}
		return newImage;
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
	 * @param image
	 *            変換する元画像
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
	public BufferedImage transform(final BufferedImage image,
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

		// 参考 http://mf-atelier.sakura.ne.jp/mf-atelier/modules/tips/index.php/program/algorithm/a6.html
		// from=x,y
		// to=X,Y
		// from: source (<- addFile)
		// to: result (<- baseFile)

		final BufferedImage sourceBeforeCut = rotateImage(image, choiseDirection(image, expectedOrientation, rotateDirection));
		final BufferedImage source = cutoffIfOver(expectedRateForCutoff, sourceBeforeCut);

		final int sourceWidth = source.getWidth();
		final int sourceHeight = source.getHeight();

		final int fromX1 = 0;
		final int fromY1 = 0;
		final int fromX2 = 0;
		final int fromY2 = sourceHeight - 1;
		final int fromX3 = sourceWidth - 1;
		final int fromY3 = sourceHeight - 1;
		final int fromX4 = sourceWidth - 1;
		final int fromY4 = 0;

		final int targetWidth;
		final int targetHeight;
		try {
			final BufferedImage newImage = ImageIO.read(this.clazz.getResource(this.baseFileName));
			targetWidth = newImage.getWidth();
			targetHeight = newImage.getHeight();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}

		final int bairitsu = 2; // 計算途中はこの倍率で変換する。

		final BufferedImage tempTarget = new BufferedImage(targetWidth * bairitsu, targetHeight * bairitsu, BufferedImage.TYPE_INT_ARGB); //
		ImageUtils.transform(source,
				fromX1, fromY1,
				fromX2, fromY2,
				fromX3, fromY3,
				fromX4, fromY4,
				tempTarget,
				toX1 * bairitsu, toY1 * bairitsu,
				toX2 * bairitsu, toY2 * bairitsu,
				toX3 * bairitsu, toY3 * bairitsu,
				toX4 * bairitsu, toY4 * bairitsu);

		final BufferedImage result = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
		final AffineTransform affine = new AffineTransform();
		affine.scale(1.0 / bairitsu, 1.0 / bairitsu);
		final AffineTransformOp scaleOp = new AffineTransformOp(affine, AffineTransformOp.TYPE_BILINEAR);
		return scaleOp.filter(tempTarget, result);
	}


	private static BufferedImage cutoffIfOver(final double expectedRateForCutoff, final BufferedImage source) {
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
