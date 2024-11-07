package Object;
import Event.Event;
import javax.swing.JPanel;

public class Ice extends Event {
    public Ice(int x,int y,int w,int h,JPanel game){
        this.x = x;
        this.yStart = y;
        this.y = y;
        this.width = w;
        this.height = h;
        super.move(game);
    }
}