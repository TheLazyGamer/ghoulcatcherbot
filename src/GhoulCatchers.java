import java.awt.Point;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GhoulCatchers extends Config{
	static Robot robot;
	static Point[][] coordinates;

	public static void main(String[] args) throws Exception {
		robot = new Robot();
		coordinates = initialiseCoordinates();
		Solver solver = new Solver();
		ScreenScanner scanner = new ScreenScanner();

		if (!SystemTray.isSupported()) {
			System.out.println("SystemTray is not supported");
			return;
		}
		TrayIcon trayIcon = new TrayIcon(new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB));
		SystemTray.getSystemTray().add(trayIcon);

		// switch to game window
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_TAB);
		Thread.sleep(DELAY_KEY_RELEASE);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_ALT);
		Thread.sleep(DELAY_BETWEEN_ACTION);

		robot.mouseMove(0, 0);
		Thread.sleep(DELAY_BETWEEN_ACTION);

		int winCount = 0;
		double lastMoveTime = System.currentTimeMillis();
		while (winCount < 51) {
			if (scanner.inactive()) {
				Thread.sleep(DELAY_INACTIVE);
				if (scanner.inactive()) {
					// if inactive for more than 10 seconds then quit
					System.out.println("EXIT");
					trayIcon.displayMessage("Ghoul Catcher", "EXIT", TrayIcon.MessageType.ERROR);
					System.exit(0);
				}
			}
			if (scanner.complete()) {
				System.out.println(++winCount);
				trayIcon.displayMessage("Ghoul Catcher", Integer.toString(winCount), TrayIcon.MessageType.INFO);
				if (winCount < 50) {
					robot.mouseMove(COORD_BUTTON_NEWGAME.x, COORD_BUTTON_NEWGAME.y);
					Thread.sleep(DELAY_BETWEEN_ACTION);

					robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
					Thread.sleep(DELAY_KEY_RELEASE);
					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
					Thread.sleep(DELAY_LOADING);
				}
			}
			MyColor[][] board = scanner.scanBoard();
			Move move = solver.findMove(board);
			if (move != null) {
				move.doMove();
				lastMoveTime = System.currentTimeMillis();
			} else {
				if (System.currentTimeMillis() - lastMoveTime > DELAY_INACTIVE) {
					robot.keyPress(KeyEvent.VK_ESCAPE);
					Thread.sleep(DELAY_KEY_RELEASE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					Thread.sleep(DELAY_BETWEEN_ACTION);

					robot.mouseMove(COORD_BUTTON_RESTART.x, COORD_BUTTON_RESTART.y);
					Thread.sleep(DELAY_BETWEEN_ACTION);

					robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
					Thread.sleep(DELAY_KEY_RELEASE);
					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
					Thread.sleep(DELAY_LOADING);

					System.out.println("RESTARTED");
					trayIcon.displayMessage("Ghoul Catcher", "RESTARTED", TrayIcon.MessageType.WARNING);
					lastMoveTime = System.currentTimeMillis();
				}
			}
		}

		System.out.println("DONE");
		trayIcon.displayMessage("Ghoul Catcher", "DONE", TrayIcon.MessageType.INFO);
	}

	private static Point[][] initialiseCoordinates() {
		Point[][] coordinates = new Point[BOARD_HEIGHT][BOARD_WIDTH];

		for (int row = 0; row < BOARD_HEIGHT; row++) {
			for (int col = 0; col < BOARD_WIDTH; col++) {
				coordinates[row][col] = new Point(COORD_FIRST_GRID.x + GRID_SIZE * col,
						                          COORD_FIRST_GRID.y + GRID_SIZE * row);
			}
		}
		return coordinates;
	}
}
