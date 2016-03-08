import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class ScreenScanner extends GhoulCatchers {

	public MyColor[][] scanBoard() {
		MyColor[][] board = new MyColor[BOARD_HEIGHT][BOARD_WIDTH];
		Rectangle area = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage bufferedImage = robot.createScreenCapture(area);

		// for each grid
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				int totalRed = 0;
				int totalGreen = 0;
				int totalBlue = 0;
				MyColor tileColor = MyColor.UNKNOWN;

				int[] rgb = bufferedImage.getRGB(coordinates[i][j].x, coordinates[i][j].y, GRID_SIZE, GRID_SIZE, null,
						0, GRID_SIZE);

				for (int k = 0; k < rgb.length; k++) {
					Color color = new Color(rgb[k]);
					totalRed += color.getRed();
					totalGreen += color.getGreen();
					totalBlue += color.getBlue();
				}

				Color averageColor = new Color(totalRed / (GRID_SIZE * GRID_SIZE), totalGreen / (GRID_SIZE * GRID_SIZE),
						totalBlue / (GRID_SIZE * GRID_SIZE));

				if (checkColor(averageColor, MyColor.YELLOW)) {
					tileColor = MyColor.YELLOW;

				} else if (checkColor(averageColor, MyColor.PINK)) {
					tileColor = MyColor.PINK;

				} else if (checkColor(averageColor, MyColor.ORANGE)) {
					tileColor = MyColor.ORANGE;

				} else if (checkColor(averageColor, MyColor.PURPLE)) {
					tileColor = MyColor.PURPLE;

				} else if (checkColor(averageColor, MyColor.GREEN)) {
					tileColor = MyColor.GREEN;

				} else if (checkColor(averageColor, MyColor.BLUE)) {
					tileColor = MyColor.BLUE;
				}
				board[i][j] = tileColor;
			}
		}

		return board;
	}

	public static boolean checkColor(Color target, MyColor ct) {
		Color pixel = ct.color;
		int tolerance = ct.tolerance;

		int rT = target.getRed();
		int gT = target.getGreen();
		int bT = target.getBlue();
		int rP = pixel.getRed();
		int gP = pixel.getGreen();
		int bP = pixel.getBlue();
		return ((rP - tolerance <= rT) &&
				(rP + tolerance >= rT) &&
				(gP - tolerance <= gT) &&
				(gP + tolerance >= gT) &&
				(bP - tolerance <= bT) &&
				(bP + tolerance >= bT));
	}

	public boolean complete() {
		Rectangle area = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage bufferedImage = robot.createScreenCapture(area);
		int rgb = bufferedImage.getRGB(COORD_COMPLETED.x, COORD_COMPLETED.y);
		Color color = new Color(rgb);
		if (checkColor(color, MyColor.COMPLETED)) {
			return true;
		}
		return false;
	}

	public boolean inactive() {
		Rectangle area = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage bufferedImage = robot.createScreenCapture(area);
		int rgb = bufferedImage.getRGB(0, 0);
		Color color = new Color(rgb);
		if (!checkColor(color, MyColor.FIREFOX)) {
			return true;
		}
		return false;
	}
}
