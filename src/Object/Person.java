package Object;
import Event.Event;

public class Person extends Event {
    public int blood = 100;
    public int speed;
    public Person(int x,int y,int w,int h,int s){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.speed = s;
    }
    public void left(){
        if (x>8){
           this.x -= speed;  
        }
    }
    public void right(){
        if (x<302){
            this.x += speed;
        }
    }
}