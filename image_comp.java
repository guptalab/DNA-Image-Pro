import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * @author mkyong
 *
 */
public class image_comp {

	private static  int IMG_WIDTH = 32;
	private static  int IMG_HEIGHT = 32;

	public static void compress_image(String path,int width, int height) {

		try {
			String cur_dir = System.getProperty("user.dir");
			IMG_WIDTH = width;
			IMG_HEIGHT = height;
			BufferedImage originalImage = ImageIO.read(new File(path));
			int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB
					: originalImage.getType();

			BufferedImage resizeImagePng = resizeImage(originalImage, type);
			ImageIO.write(resizeImagePng, "png", new File("output.png"));

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	private static BufferedImage resizeImage(BufferedImage originalImage,
			int type) {
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT,
				type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();

		return resizedImage;
	}

	private static BufferedImage resizeImageWithHint(
			BufferedImage originalImage, int type) {

		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT,
				type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}
}