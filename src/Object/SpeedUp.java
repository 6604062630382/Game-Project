package Object;
import Event.Event;
import javax.swing.JPanel;

public class SpeedUp extends Event {
    public SpeedUp(int x,int y,int w,int h,JPanel game){
        this.x = x;
        this.yStart = y;
        this.y = y;
        this.width = w;
        this.height = h;
        this.move(game);
    }
}