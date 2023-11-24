import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Game extends Board {
  public Game(Sound s, Frame f) {
    super(s, f);
  }

  public static void main(String[] args) {
    Frame frame = new Frame();
    frame.setVisible(true);
     JButton aboutButton = new JButton("Instructions");
     aboutButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        showAboutDialog(frame);
       }
     });
     frame.add(aboutButton, BorderLayout.PAGE_END);
     frame.pack();
     frame.setLocationRelativeTo(null);
  }
}
