package heignamerican.gfgazou.mashiroNurse;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import heignamerican.gfgazou.base.Files;
import heignamerican.gfgazou.base.UNext;
import heignamerican.gfgazou.base.UNextBase;
import heignamerican.gfgazou.base.UNextBase.Orientation;
import heignamerican.gfgazou.base.UNextBase.RotateDirection;

public class MashiroNurse implements UNext {
	public void gazou(final Files files) throws Exception {
		// No.11259 [白衣の天使]真白透子 ... 第1進展
		final UNextBase unextbase = new UNextBase(this.getClass(), "base.jpg", new String[] {
				"yubi.txt",
		});

		BufferedImage add = unextbase.transform(ImageIO.read(files.getSourceFile()),
				389, 381,
				343, 537,
				458, 503,
				513, 347,
				Orientation.Portrait,
				RotateDirection.CounterClockwise);

		ImageIO.write(unextbase.createUNext(add), files.getExtension(), files.getDestFile());
	}
}
