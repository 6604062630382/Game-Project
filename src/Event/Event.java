package Event;
import Game.MyGame;
import static Game.MyGame.person;
import Object.Person;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Event {
    public int x,y,width,height;
    public int yStart;
    public int count=0;
    public static boolean checkHit(Person person,Event obj){
        if (((person.x+person.width>obj.x) && (person.x < obj.x))||((person.x+person.width>obj.x+obj.width)&&(person.x<obj.x+obj.width))){
            if (person.y+person.height>obj.y+obj.height && person.y<obj.y+obj.height){
                return true;
            }
        }
        return false;
    }
    public Timer timer1;
    public void move(JPanel game){
        if (timer1 != null) {
            timer1.stop();
        }
        timer1 = new Timer(50,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (person.blood <= 0 || MyGame.timeLeft <= 0){
                    timer1.stop(); 
                }
                y += MyGame.gameSpeed;
                game.repaint();
                if (y>600){
                    y = yStart;
                    x = (int)(10+Math.floor(Math.random()*300));
                }  
            }
        });
        timer1.start();
        MyGame.pauseButton.addActionListener(e->{
            if (count % 2 != 0){
                timer1.start();
            }else{
                timer1.stop(); 
            }
            count++;
        });
        game.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==80){
                    if (count % 2 != 0){
                        timer1.start();
                    }else{
                        timer1.stop(); 
                    }
                    count++;
                }
        }});
//        game.addKeyListener(this);
//        game.requestFocusInWindow();
    }

//    @Override
//    public void keyTyped(KeyEvent e) {}
//    @Override
//    public void keyPressed(KeyEvent e) {
//        if (e.getKeyCode()==80){
//            if (count % 2 != 0){
//                timer1.start();
//            }else{
//                timer1.stop(); 
//            }
//            count++;
//        }
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {}
}