package Game;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Display extends JFrame {
    public Display(){
        super("Collect Rain Game");
        ImageIcon imageLogo = new ImageIcon(getClass().getResource("/Pictures/Rain.png"));
        this.setIconImage(imageLogo.getImage());
        this.setSize(375,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
    }
    public static void main(String[] args) {
        Display display = new Display();
        display.add(new StartMenu());
        display.setVisible(true);
    }
}