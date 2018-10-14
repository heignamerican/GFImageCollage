package heignamerican.gfgazou.heiwatansan;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import heignamerican.gfgazou.base.Files;
import heignamerican.gfgazou.base.ImageUtils;
import heignamerican.gfgazou.base.Orientation;
import heignamerican.gfgazou.base.RotateDirection;
import heignamerican.gfgazou.base.UNext;
import heignamerican.gfgazou.base.UNextBase;

public class Tansan implements UNext {
	@Override
	public void gazou(Files... filesList) throws Exception {
		// final Files files = filesList[0];
		final Files files = new Files(this, filesList[0].getSourceFile().getName(), Files.EXTENSION_PNG);

		// 通常の炭酸の2倍の画像を用意して item2x.png に置く
		final UNextBase unextbase = new UNextBase(this.getClass(), "item2x.png", new String[] {
		});

		final BufferedImage source = ImageIO.read(files.getSourceFile());
		final BufferedImage curved = convert(source);
		final BufferedImage scaled = ImageUtils.scale(curved, 2);
		final BufferedImage add = unextbase.transform(scaled,
				38, 198,
				126, 284,
				240, 156,
				162, 82,
				Orientation.Landscape,
				RotateDirection.Clockwise);

		ImageIO.write(ImageUtils.scale(unextbase.createUNext(add), 0.5), files.getExtension(), files.getDestFile());
	}

	// カーブさせる
	private BufferedImage convert(final BufferedImage source) {
		final double rate = 1.08;
		final int curveRate = 2;

		final BufferedImage result = new BufferedImage((int) (source.getWidth() * rate), source.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

		final int midY = source.getHeight() / 2;
		final int maxOffset = result.getWidth() - source.getWidth();

		for (int y = 0; y < source.getHeight(); y++) {
			double a = (1.0 * Math.abs(y - midY) / midY);
			final int offset = (int) (Math.pow(a, curveRate) * maxOffset);

			for (int x = 0; x < source.getWidth(); x++) {
				int newX = x + offset;
				result.setRGB(newX, y, source.getRGB(x, y));
			}
		}
		return result;
	}
}
