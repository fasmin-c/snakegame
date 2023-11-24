
import javax.swing.JFrame;

public class Frame extends JFrame {
    Sound s;
    public Frame() {
        startBackground();
        Board b = new Board(s, this);
        add(b);
        setResizable(false);
        this.pack();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void startBackground() {
        try {
            s = new Sound();
            s.startBackgroundMusic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean headIntersects(Object apple) {
        return false;
    }
}

