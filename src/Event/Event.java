package Event;
import Game.MyGame;
import Object.Person;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Event{
    public int x,y,width,height;
    public int yStart;
    public static boolean checkHit(Person person,Event obj){
        if (((person.x+person.width>obj.x) && (person.x < obj.x))||((person.x+person.width>obj.x+obj.width)&&(person.x<obj.x+obj.width))){
            if (person.y+person.height>obj.y+obj.height && person.y<obj.y+obj.height){
                return true;
            }
        }
        return false;
    }
    Timer timer1;
    public void move(JPanel game){
        if (timer1 != null) {
            timer1.stop();
        }
        timer1 = new Timer(50,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                y += MyGame.gameSpeed;
                game.repaint();
                if (y>600){
                    y = yStart;
                    x = (int)(10+Math.floor(Math.random()*300));
                }  
            }
        });
        timer1.start();
    }
}