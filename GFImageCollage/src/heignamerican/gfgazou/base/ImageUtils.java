package heignamerican.gfgazou.base;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageUtils {
	public static BufferedImage rotateClockwise90Degree(final BufferedImage img) {
		final BufferedImage result = new BufferedImage(img.getHeight(), img.getWidth(), img.getType());

		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				result.setRGB(img.getHeight() - y - 1, x, img.getRGB(x, y));
			}
		}
		return result;
	}

	public static BufferedImage rotateCounterClockwise90Degree(final BufferedImage img) {
		final BufferedImage result = new BufferedImage(img.getHeight(), img.getWidth(), img.getType());

		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				result.setRGB(y, img.getWidth() - x - 1, img.getRGB(x, y));
			}
		}
		return result;
	}

	public static BufferedImage cutOffHeightLeavingCenter(final BufferedImage source, final int newHeight) {
		final BufferedImage result = new BufferedImage(source.getWidth(), newHeight, source.getType());

		for (int y = 0; y < result.getHeight(); y++) {
			for (int x = 0; x < result.getWidth(); x++) {
				final int offsetY = (source.getHeight() - result.getHeight()) / 2;
				result.setRGB(x, y, source.getRGB(x, y + offsetY));
			}
		}
		return result;
	}

	public static BufferedImage cutOffWidthLeavingCenter(final BufferedImage source, final int newWidth) {
		final BufferedImage result = new BufferedImage(newWidth, source.getHeight(), source.getType());

		for (int y = 0; y < result.getHeight(); y++) {
			for (int x = 0; x < result.getWidth(); x++) {
				final int offsetX = (source.getWidth() - result.getWidth()) / 2;
				result.setRGB(x, y, source.getRGB(x + offsetX, y));
			}
		}
		return result;
	}

	public static BufferedImage scale(final BufferedImage source, final int scale) {
		return scale(source, scale, scale);
	}

	public static BufferedImage scale(final BufferedImage source, final int scaleX, final int scaleY) {
		final AffineTransform affine = new AffineTransform();
		affine.scale(scaleX, scaleY);
		final AffineTransformOp affineTransformOp = new AffineTransformOp(affine, AffineTransformOp.TYPE_BILINEAR);

		final BufferedImage after = new BufferedImage(source.getWidth() * scaleX, source.getHeight() * scaleY, BufferedImage.TYPE_INT_ARGB);
		affineTransformOp.filter(source, after);
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
