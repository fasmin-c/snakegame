
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
    static int BOARD_HIGHT = 500;
    static int BOARD_WIDTH = 500;
    static int CELL_SIZE = 15;
    static int MAX_SIZE = (BOARD_WIDTH * BOARD_HIGHT) / (CELL_SIZE * CELL_SIZE);
    private static final String WINDOW_TITLE = "Snake";
    int DELAY = 200;
    boolean gameOver = false;
    boolean running = false;
    char direction = 'R';
    Timer timer;
    Sound sound;
    Snake snake;
    Frame frame;
    Apple food;
    private boolean gamePaused = false;
    public JLabel scoreLabel;

    public Board(Sound s, Frame f) {
        sound = s;
        frame = f;
        food = new Apple(BOARD_WIDTH, BOARD_HIGHT, CELL_SIZE);
        snake = new Snake(MAX_SIZE);
        initBoard();
        initGame();
    }

    static void showAboutDialog(JFrame parentFrame) {
        JOptionPane.showMessageDialog(parentFrame, "Press P to Pause The Game\nPress SPACE to Restart Game\n", "Instructions",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void initGameVariables() {
        gamePaused = false;
    }

    public void togglePaused() {
        gamePaused = !gamePaused;
        if (gamePaused) {
            sound.endBackground();
            timer.stop();         
        } else {
            sound.startBackgroundMusic();
            timer.start();
        }
    }
    // initBoard set the size and background color of the BOARD.
    // it also implements the KeyListener.
    private void initBoard() {
        this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HIGHT));
        this.setBackground(Color.GREEN);
        this.setFocusable(true);
        // addKeyListener methods adds the KeyAdapter listener interface for receiving keyboard events (keystrokes)
        // and do different functions depending on the key pressed.
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_P) {
                    togglePaused();                   
                }

                if (key == KeyEvent.VK_LEFT) {
                    if (direction != 'R') {
                        direction = 'L';
                    }
                }

                if (key == KeyEvent.VK_RIGHT) {
                    if (direction != 'L') {
                        direction = 'R';
                    }
                }

                if (key == KeyEvent.VK_UP) {
                    if (direction != 'D') {
                        direction = 'U';
                    }
                }

                if (key == KeyEvent.VK_DOWN) {
                    if (direction != 'U') {
                        direction = 'D';
                    }
                }
                if (key == KeyEvent.VK_SPACE) {
                    restartGame();
                }
            }

        });

    }
    // initGame adds an apple at random location on the board, set the game as running and
    // start the timer with default delay. 
    void initGame() {
        food.newFood();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    // restartGame restarts the game by resetting the snake size and its positions
    // diection of the snake, speed of the snake as initial delay, and resetting the apples eaten.
    public void restartGame() {
        Snake.setLengthOfSnake(6);
        snake.setX(new int[MAX_SIZE]);
        snake.setY(new int[MAX_SIZE]);
        gameOver = false;
        food.setApplesEaten(0);
        direction = 'R';
        running = true;
        repaint();
        timer.setDelay(DELAY);
        timer.start();
        food.newFood();
        sound.startBackgroundMusic();
    }
    // paintComponent the function in JComponents is overridden here to add additional styling.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (running) {
            g.setColor(Color.RED);
            g.fillOval(food.getApple_x(), food.getApple_y(), CELL_SIZE, CELL_SIZE);

            for (int i = 0; i < Snake.getLengthOfSnake(); i++) {
                if (i == 0) {
                    g.setColor(Color.BLACK);
                    g.fillRect(snake.getX()[i], snake.getY()[i], CELL_SIZE, CELL_SIZE);
                } else {
                    g.setColor(Color.blue);
                    g.fillRect(snake.getX()[i], snake.getY()[i], CELL_SIZE, CELL_SIZE);
                }
            }

            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics metrics = getFontMetrics(g.getFont());

            frame.setTitle(WINDOW_TITLE + " | Score: " + food.getApplesEaten());

        } else {
            gameOver(g);
        }

    }

    public void checkFood() {
        if ((snake.getX()[0] == food.getApple_x()) && (snake.getY()[0] == food.getApple_y())) {
            Snake.setLengthOfSnake(Snake.getLengthOfSnake() + 1);
            food.setApplesEaten(food.getApplesEaten() + 1);
            Sound.playSound("sound/apple-munch.wav");

            food.newFood();
            if (food.getApplesEaten() % 3 == 0) {
                increaseSpeed();
            }
        }
    }

    private void increaseSpeed() {
        int currentDelay = timer.getDelay();
        if (currentDelay > 50) {
            timer.setDelay(currentDelay - 25);
        }
    }

    private void checkCollision() {

        if (snake.checkCollision()) {
            running = false;
        }
        // check if head touches left border
        if (snake.getX()[0] < 0) {
            running = false;
        }
        // check if head touches right border
        if (snake.getX()[0] > BOARD_WIDTH) {
            running = false;
        }
        // check if head touches top border
        if (snake.getY()[0] < 0) {
            running = false;
        }
        // check if head touches bottom border
        if (snake.getY()[0] > BOARD_HIGHT) {
            running = false;
        }

        if (!running) {
            if (timer != null) {
                timer.stop();

            }
        }
    }

    void gameOver(Graphics g) {
        sound.endBackground();
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + food.getApplesEaten(),
                (BOARD_WIDTH - metrics1.stringWidth("Score: " + food.getApplesEaten())) / 2,
                g.getFont().getSize());

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (BOARD_WIDTH - metrics2.stringWidth("Game Over")) / 2, BOARD_HIGHT / 2);

        g.setFont(new Font("Courier New", Font.PLAIN, 20));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Press 'SPACE' to play again",
                (BOARD_WIDTH - metrics3.stringWidth("Press 'SPACE' to play again")) / 2, BOARD_HIGHT / 2 + 70);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {
            snake.move(direction, CELL_SIZE);
            checkFood();
            checkCollision();
        }
        repaint();
    }

}