package heignamerican.gfgazou.neji中学;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import heignamerican.gfgazou.base.Files;
import heignamerican.gfgazou.base.UNext;
import heignamerican.gfgazou.base.UNextBase;
import heignamerican.gfgazou.base.UNextBase.Orientation;
import heignamerican.gfgazou.base.UNextBase.RotateDirection;

public class Neji中学1 implements UNext {
	@Override
	public void gazou(Files... filesList) throws Exception {
		final Files files = filesList[0];

		// No.4579 [中学時代]螺子川来夢 ... 第1進展
		final UNextBase unextbase = new UNextBase(this.getClass(), "base1.jpg", new String[] {
		});

		BufferedImage add = unextbase.transform(ImageIO.read(files.getSourceFile()),
				1.48,
				375, 423,
				367, 491,
				473, 506,
				480, 438,
				Orientation.Landscape,
				RotateDirection.None);

		ImageIO.write(unextbase.createUNext(add), files.getExtension(), files.getDestFile());
	}
}
