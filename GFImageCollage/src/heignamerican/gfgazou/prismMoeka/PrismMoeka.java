package heignamerican.gfgazou.prismMoeka;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import heignamerican.gfgazou.base.Files;
import heignamerican.gfgazou.base.UNext;
import heignamerican.gfgazou.base.UNextBase;
import heignamerican.gfgazou.base.UNextBase.Orientation;
import heignamerican.gfgazou.base.UNextBase.RotateDirection;

public class PrismMoeka implements UNext {
	@Override
	public void gazou(Files files) throws Exception {
		// No.7109 [恋する三役]新田萌果 ... プリズムCOOL
		final UNextBase unextbase = new UNextBase(this.getClass(), "base.jpg", new String[] {
				"yubi.txt",
		});

		BufferedImage add = unextbase.transform(ImageIO.read(files.getSourceFile()),
				0.5395,
				366, 254,
				358, 327,
				398, 331,
				405, 256,
				Orientation.Portrait,
				RotateDirection.Clockwise);

		ImageIO.write(unextbase.createUNext(add), files.getExtension(), files.getDestFile());
	}
}
