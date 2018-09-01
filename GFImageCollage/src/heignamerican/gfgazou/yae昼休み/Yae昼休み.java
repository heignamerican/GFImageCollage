package heignamerican.gfgazou.yae昼休み;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import heignamerican.gfgazou.base.Files;
import heignamerican.gfgazou.base.Orientation;
import heignamerican.gfgazou.base.RotateDirection;
import heignamerican.gfgazou.base.UNext;
import heignamerican.gfgazou.base.UNextBase;

public class Yae昼休み implements UNext {
	@Override
	public void gazou(Files... filesList) throws Exception {
		final Files files = filesList[0];

		// No.9603 [お昼休み17]三条八重 ... 第2進展
		final UNextBase unextbase = new UNextBase(this.getClass(), "base2.jpg", new String[] {
				"yubi.txt",
		});

		final BufferedImage add = unextbase.transform(ImageIO.read(files.getSourceFile()),
				399, 361,
				321, 434,
				445, 460,
				518, 385,
				Orientation.Portrait,
				RotateDirection.CounterClockwise);

		ImageIO.write(unextbase.createUNext(add), files.getExtension(), files.getDestFile());
	}
}
