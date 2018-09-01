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
	public static BufferedImage scale(final BufferedImage img, final int scale) {
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
	public static BufferedImage scale(final BufferedImage img, final int scaleX, final int scaleY) {
		final AffineTransform affine = new AffineTransform();
		affine.scale(scaleX, scaleY);
		final AffineTransformOp affineTransformOp = new AffineTransformOp(affine, AffineTransformOp.TYPE_BILINEAR);

		final BufferedImage after = new BufferedImage(img.getWidth() * scaleX, img.getHeight() * scaleY, BufferedImage.TYPE_INT_ARGB);
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
}
