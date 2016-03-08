import java.awt.Color;
import java.awt.Point;

public class Config {
	public static final int DELAY_KEY_RELEASE = 10;
	public static final int DELAY_BETWEEN_ACTION = 100;
	public static final int DELAY_LOADING = 2000;
	public static final int DELAY_INACTIVE = 10000;
	public static final int BOARD_HEIGHT = 6;
	public static final int BOARD_WIDTH = 6;
	public static final int GRID_SIZE = 55;
	public static final Point COORD_FIRST_GRID = new Point(363, 256);
	public static final Point COORD_BUTTON_RESTART = new Point(535, 645);
	public static final Point COORD_BUTTON_NEWGAME = new Point(535, 535);
	public static final Point COORD_COMPLETED = new Point(462, 224);
	public static double RANDOM_MOVE = 0.33;
}

enum MyColor {
    YELLOW		(new Color(168, 183, 113), 10),
    PINK		(new Color(178, 110, 145), 10),
    ORANGE		(new Color(188, 173,  92), 20),
    PURPLE		(new Color(163, 150, 195), 10),
    GREEN		(new Color( 91, 179,  82), 10),
    BLUE		(new Color(104, 181, 174), 10),
    COMPLETED	(new Color(255, 255,   0), 10),
    FIREFOX		(new Color(198, 198, 198), 10),
    UNKNOWN		(new Color(  0,   0,   0),  0);
	
    public final Color color;
    public final int tolerance;

    MyColor(Color color, int tolerance) {
        this.color = color;
        this.tolerance = tolerance;
    }
}

enum Direction {
    RIGHT, DOWN;
}