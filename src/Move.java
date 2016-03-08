import java.awt.event.InputEvent;

public class Move extends GhoulCatchers {
	int i;
	int j;
	Direction direction;

	public Move(int i, int j, Direction direction) {
		this.i = i;
		this.j = j;
		this.direction = direction;
	}

	public void doMove() throws InterruptedException {
		robot.mouseMove(coordinates[i][j].x + (int) (GRID_SIZE * 0.5),
		                coordinates[i][j].y + (int) (GRID_SIZE * 0.5));
		Thread.sleep(DELAY_BETWEEN_ACTION);

		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		Thread.sleep(DELAY_BETWEEN_ACTION);

		switch (direction) {
		case RIGHT:
			robot.mouseMove(coordinates[i][j].x + (int) (GRID_SIZE * 1.5),
			                coordinates[i][j].y + (int) (GRID_SIZE * 0.5));
			break;
		case DOWN:
			robot.mouseMove(coordinates[i][j].x + (int) (GRID_SIZE * 0.5),
			                coordinates[i][j].y + (int) (GRID_SIZE * 1.5));
			break;
		}
		Thread.sleep(DELAY_BETWEEN_ACTION);

		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		Thread.sleep(DELAY_BETWEEN_ACTION);

		robot.mouseMove(0, 0);
		Thread.sleep(DELAY_BETWEEN_ACTION);
	}
}
