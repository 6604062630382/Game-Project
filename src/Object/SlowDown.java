package Object;
import Event.Event;
import javax.swing.JPanel;

public class SlowDown extends Event {
    public SlowDown(int x,int y,int w,int h,JPanel game){
        this.x = x;
        this.yStart = y;
        this.y = y;
        this.width = w;
        this.height = h;
        super.move(game);
    }
}