package heignamerican.gfgazou.yae学園交流;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import heignamerican.gfgazou.base.Files;
import heignamerican.gfgazou.base.ImageUtils;
import heignamerican.gfgazou.base.Orientation;
import heignamerican.gfgazou.base.RotateDirection;
import heignamerican.gfgazou.base.UNext;
import heignamerican.gfgazou.base.UNextBase;

public class Yae学園交流 implements UNext {
	@Override
	public void gazou(Files... filesList) throws Exception {
		final Files files = filesList[0];

		// No.10226 [学園交流17]三条八重 ... 最終進展
		final UNextBase unextbase = new UNextBase(this.getClass(), "base.jpg", new String[] {
				// "yubi.txt",
		});

		final BufferedImage add1 = unextbase.transform(ImageIO.read(filesList[0].getSourceFile()),
				1.0,
				290, 369,
				289, 407,
				331, 411,
				331, 374,
				Orientation.Portrait,
				RotateDirection.CounterClockwise);
		final BufferedImage add2 = unextbase.transform(ImageIO.read(filesList[1].getSourceFile()),
				1.0,
				274, 377,
				275, 412,
				286, 406,
				286, 369,
				Orientation.Portrait,
				RotateDirection.CounterClockwise);

		final BufferedImage add = new BufferedImage(800, 640, BufferedImage.TYPE_INT_ARGB); // FIXME
		ImageUtils.overlay(add, add1);
		ImageUtils.overlay(add, add2);

		ImageIO.write(unextbase.createUNext(add), files.getExtension(), files.getDestFile());
	}
}
