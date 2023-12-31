import java.util.Random;

public class Apple implements Food {

    private int apple_x;
    private int apple_y;
    private int applesEaten = 0;
    private Random random;
    private int BOARD_WIDTH;
    private int CELL_SIZE;
    private int BOARD_HIGHT;
   // Apple constructor adds the BOARD_WIDTH, BROAD_HIGHT and CELL_SIZE
   // that are needed to generate the X and Y of apple position.
    public Apple(int boardwidth, int boardhight, int cellsize) {
        BOARD_WIDTH = boardwidth;
        CELL_SIZE = cellsize;
        BOARD_HIGHT = boardhight;
        random = new Random();
    }
    public int getApple_x() {
        return apple_x;
    }

    public int getApple_y() {
        return apple_y;
    }

    public int getApplesEaten() {
        return applesEaten;
    }

    public void setApplesEaten(int applesEaten) {
        this.applesEaten = applesEaten;
    }

    // newFood randomly generate X and Y location for apple in the board
    @Override
    public void newFood() {
        apple_x = random.nextInt((int) (BOARD_WIDTH / CELL_SIZE)) * CELL_SIZE;
        apple_y = random.nextInt((int) (BOARD_HIGHT / CELL_SIZE)) * CELL_SIZE;
    }

}
