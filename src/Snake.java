public class Snake {
    private static int lengthOfSnake = 6;
    private int MAX_SIZE;
    private int x[];
    private int y[];

    public Snake(int maxSize) {
        this.MAX_SIZE = maxSize;
        x = new int[maxSize];
        y = new int[maxSize];
    }

    public static int getLengthOfSnake() {
        return lengthOfSnake;
    }

    public static void setLengthOfSnake(int lengthOfSnake) {
        Snake.lengthOfSnake = lengthOfSnake;
    }

    public int[] getX() {
        return x;
    }

    public void setX(int[] x) {
        this.x = x;
    }

    public int[] getY() {
        return y;
    }

    public void setY(int[] y) {
        this.y = y;
    }

    public void move(char direction, int CELL_SIZE) {
        for (int i = Snake.lengthOfSnake; i > 0; i--) {
            this.x[i] = this.x[i - 1];
            this.y[i] = this.y[i - 1];
        }

        switch (direction) {
            case 'U':
                this.y[0] = this.y[0] - CELL_SIZE;
                break;
            case 'D':
                this.y[0] = this.y[0] + CELL_SIZE;
                break;
            case 'L':
                this.x[0] = this.x[0] - CELL_SIZE;
                break;
            case 'R':
                this.x[0] = this.x[0] + CELL_SIZE;
                break;
        }
    }

    public boolean checkCollision() {
        for (int i = getLengthOfSnake(); i > 0; i--) {
            if ((this.x[0] == this.x[i]) && (this.y[0] == this.y[i])) {
                return true;
            }
        }
        return false;
    }

}
