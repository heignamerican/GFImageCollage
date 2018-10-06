package heignamerican.gfgazou.hanafusa裸セーター;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import heignamerican.gfgazou.base.Files;
import heignamerican.gfgazou.base.Orientation;
import heignamerican.gfgazou.base.RotateDirection;
import heignamerican.gfgazou.base.UNext;
import heignamerican.gfgazou.base.UNextBase;

public class Hanafusa裸セーター implements UNext {
	final Double rate;
	public Hanafusa裸セーター() {
		this.rate = null;
	}
	public Hanafusa裸セーター(final double rate) {
		this.rate = rate;
	}

	@Override
	public void gazou(Files... filesList) throws Exception {
		final Files files = filesList[0];

		// No.11766 [おうちｾｰﾀｰ18]花房優輝 ... 最終進展
		final UNextBase unextbase = new UNextBase(this.getClass(), "base.jpg", new String[] {
				"hair.txt",
				"waku.txt",
		});

		final BufferedImage add = unextbase.transform(ImageIO.read(files.getSourceFile()),
				(rate != null ? rate : 1.0),
				10, 197,
				6, 322,
				176, 290,
				158, 171,
				Orientation.Portrait,
				RotateDirection.Clockwise);

		ImageIO.write(unextbase.createUNext(add), files.getExtension(), files.getDestFile());
	}
}
