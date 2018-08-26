package heignamerican.gfgazou.hanafusaトナカイタイツ;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import heignamerican.gfgazou.base.Files;
import heignamerican.gfgazou.base.UNext;
import heignamerican.gfgazou.base.UNextBase;
import heignamerican.gfgazou.base.UNextBase.Orientation;
import heignamerican.gfgazou.base.UNextBase.RotateDirection;

public class Hanafusaトナカイタイツ implements UNext {
	@Override
	public void gazou(Files files) throws Exception {
		// No.8452 [ｸﾘｽﾏｽ16]花房優輝 ... 最終進展
		final UNextBase unextbase = new UNextBase(this.getClass(), "base.jpg", new String[] {
				"yubi-left.txt",
				"yubi-right.txt",
		});

		final BufferedImage add = unextbase.transform(ImageIO.read(files.getSourceFile()),
				0.60,
				239, 342,
				235, 547,
				358, 551,
				365, 347,
				Orientation.Portrait,
				RotateDirection.Clockwise);

		ImageIO.write(unextbase.createUNext(add), files.getExtension(), files.getDestFile());
	}
}
