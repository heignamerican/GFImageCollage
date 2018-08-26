package heignamerican.gfgazou.tooyamaHandkerchief;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import heignamerican.gfgazou.base.Files;
import heignamerican.gfgazou.base.UNext;
import heignamerican.gfgazou.base.UNextBase;
import heignamerican.gfgazou.base.UNextBase.Orientation;
import heignamerican.gfgazou.base.UNextBase.RotateDirection;

public class Tooyamaハンカチ implements UNext {
	@Override
	public void gazou(Files files) throws Exception {
		final UNextBase unextbase = new UNextBase(this.getClass(), "base.jpg", new String[] {
				"yubi1.txt",
				"yubi2.txt",
				"yubi3.txt",
				"yubi4.txt",
		});

		final BufferedImage add = unextbase.transform(ImageIO.read(files.getSourceFile()),
				1.66,
				189, 512,
				176, 662,
				402, 622,
				384, 477,
				Orientation.Landscape,
				RotateDirection.Clockwise);

		ImageIO.write(unextbase.createUNext(add), files.getExtension(), files.getDestFile());
	}
}
