package heignamerican.gfgazou.mahiro誕生日;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import heignamerican.gfgazou.base.Files;
import heignamerican.gfgazou.base.UNext;
import heignamerican.gfgazou.base.UNextBase;
import heignamerican.gfgazou.base.UNextBase.Orientation;
import heignamerican.gfgazou.base.UNextBase.RotateDirection;

public class Mahiro誕生日 implements UNext {
	@Override
	public void gazou(Files... filesList) throws Exception {
		final Files files = filesList[0];

		// No.7070 [誕生日16]夏目真尋 ... 第1進展
		final UNextBase unextbase = new UNextBase(this.getClass(), "base.jpg", new String[] {
				"sode.txt",
				"namae.txt",
		});

		BufferedImage add = unextbase.transform(ImageIO.read(files.getSourceFile()),
				0.78,
				510, 364,
				467, 500,
				535, 577,
				587, 443,
				Orientation.Portrait,
				RotateDirection.Clockwise);

		ImageIO.write(unextbase.createUNext(add), files.getExtension(), files.getDestFile());
	}
}
