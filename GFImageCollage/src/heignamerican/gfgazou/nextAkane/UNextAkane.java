package heignamerican.gfgazou.nextAkane;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import heignamerican.gfgazou.base.Files;
import heignamerican.gfgazou.base.Orientation;
import heignamerican.gfgazou.base.RotateDirection;
import heignamerican.gfgazou.base.UNext;
import heignamerican.gfgazou.base.UNextBase;

public class UNextAkane implements UNext {
	@Override
	public void gazou(Files... filesList) throws Exception {
		final Files files = filesList[0];

		// No.3918 [お手軽映画]櫻井明音 ... U-NEXT明音 全ての始まり
		final UNextBase unextbase = new UNextBase(this.getClass(), "base.jpg", new String[] {
				"kami.txt",
				"yubi.txt",
				"yubi2.txt",
		});

		BufferedImage add = unextbase.transform(ImageIO.read(files.getSourceFile()),
				337, 229,
				418, 423,
				572, 359,
				486, 166,
				Orientation.Portrait,
				RotateDirection.Clockwise);

		ImageIO.write(unextbase.createUNext(add), files.getExtension(), files.getDestFile());
	}
}
