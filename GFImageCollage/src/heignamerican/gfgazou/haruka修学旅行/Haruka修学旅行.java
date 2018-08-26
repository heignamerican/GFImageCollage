package heignamerican.gfgazou.haruka修学旅行;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import heignamerican.gfgazou.base.Files;
import heignamerican.gfgazou.base.UNext;
import heignamerican.gfgazou.base.UNextBase;
import heignamerican.gfgazou.base.UNextBase.Orientation;
import heignamerican.gfgazou.base.UNextBase.RotateDirection;

public class Haruka修学旅行 implements UNext {
	@Override
	public void gazou(final Files files) throws Exception {
		// No.11307 [修学旅行18]風町陽歌 ... 最終進展
		final UNextBase unextbase = new UNextBase(this.getClass(), "base.jpg", new String[] {
				"yubi.txt",
		});

		BufferedImage add = unextbase.transform(ImageIO.read(files.getSourceFile()),
				0.585,
				399, 291,
				428, 414,
				499, 392,
				466, 270,
				Orientation.Portrait,
				RotateDirection.Clockwise);

		ImageIO.write(unextbase.createUNext(add), files.getExtension(), files.getDestFile());
	}
}
