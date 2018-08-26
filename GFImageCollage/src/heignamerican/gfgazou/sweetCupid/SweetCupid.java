package heignamerican.gfgazou.sweetCupid;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import heignamerican.gfgazou.base.Files;
import heignamerican.gfgazou.base.ImageUtils;
import heignamerican.gfgazou.base.UNext;
import heignamerican.gfgazou.base.UNextBase;
import heignamerican.gfgazou.base.UNextBase.Orientation;
import heignamerican.gfgazou.base.UNextBase.RotateDirection;

public class SweetCupid implements UNext {
	@Override
	public void gazou(final Files files) throws Exception {
		// キュピ背景に重ねたキュピ結果イメージを生成
		final BufferedImage cupidImage;
		{
			// キュピ背景のベース
			final UNextBase backgroundBase = new UNextBase(this.getClass(), "background.jpg", new String[] {
			});

			// 画像大きめにしておかないと良い感じで次工程で重ねられない。たぶん射影変換が手抜き実装だから。
			final int bairitsu = 2;

			// キュピ背景の排出画像
			final BufferedImage sourceImage = ImageUtils.scale(ImageIO.read(files.getSourceFile()), bairitsu);
			final BufferedImage innerImage = backgroundBase.transform(sourceImage,
					0.8,
					74, 109,
					74, 723,
					566, 723,
					566, 109,
					Orientation.Portrait,
					RotateDirection.CounterClockwise);

			cupidImage = ImageUtils.scale(backgroundBase.createUNext(innerImage), bairitsu);
			// ImageIO.write(cupidImage, files.getExtension(), new java.io.File(files.getDestFile() + "-inner.jpg"));
		}

		// 生成したキュピ結果を重ねてクソコラ生成
		{
			final UNextBase unextbase = new UNextBase(this.getClass(), "base.jpg", new String[] {
					"yubi.txt"
			});

			final BufferedImage add = unextbase.transform(cupidImage,
					1325, 342,
					1010, 568,
					1195, 804,
					1519, 567,
					Orientation.Portrait,
					RotateDirection.None);

			ImageIO.write(unextbase.createUNext(add), files.getExtension(), files.getDestFile());
		}
	}
}
