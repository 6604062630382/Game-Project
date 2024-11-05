package Game;
import Object.Ice;
import Object.IncreaseBlood;
import Object.Person;
import Object.Rain;
import Object.SlowDown;
import Object.SpeedUp;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public final class MyGame extends JPanel implements KeyListener{
    private final ImageIcon character = new ImageIcon("src/Pictures/Character.png");
    private final Image characterImage = character.getImage();
    private final ImageIcon character2 = new ImageIcon("src/Pictures/Character2.png");
    private final Image characterImage2 = character2.getImage();
    private final ImageIcon lighting1 = new ImageIcon("src/Pictures/Lighting.png");
    private final Image lighting1Image = lighting1.getImage();
    private final ImageIcon darkCloud = new ImageIcon("src/Pictures/DarkCloud.png");
    private final Image darkCloudImage = darkCloud.getImage();
    private final ImageIcon darkCloud2 = new ImageIcon("src/Pictures/DarkCloud2.png");
    private final Image darkCloudImage2 = darkCloud2.getImage();
    private final ImageIcon rainIcon = new ImageIcon("src/Pictures/Rain.png");
    private final Image rainImage = rainIcon.getImage();
    private final ImageIcon blood = new ImageIcon("src/Pictures/Blood.png");
    private final Image bloodImage = blood.getImage();
    private final ImageIcon iceIcon = new ImageIcon("src/Pictures/Ice.png");
    private final Image iceImage = iceIcon.getImage();
    private final ImageIcon umbrella = new ImageIcon("src/Pictures/Umbrella.png");
    private final Image umbrellaImage = umbrella.getImage();
    private final ImageIcon heart = new ImageIcon("src/Pictures/Heart.png");
    private final Image heartImage = heart.getImage();
    public static int gameSpeed = 20;
    public static int score = 0;
    private int timeLeft = 60;
    private boolean slowCheck = false;
    private boolean speedCheck = false;
    public static Person person = new Person(7,440,80,130,15);
    Rain[] rainSet;
    Ice[] iceSet;
    IncreaseBlood[] increaseBloodSet;
    SlowDown[] slowDownSet;
    public SpeedUp[] speedUpSet;
    private Timer timer;
    private Thread gameThread;
    public void resetGame() {
        if (timer != null) {
            timer.stop();
        }
        rainSet = makeRainSet(4);
        iceSet = makeIceSet(2);
        increaseBloodSet = makeIncreaseBloodSet(1);
        slowDownSet = makeSlowDownSet(2);
        speedUpSet = makeSpeedUpSet(1);
        person.speed = 15;
        person.blood = 100;
        timeLeft = 60;
        gameSpeed = 20;
        slowCheck = false;
        speedCheck = false;
        score = 0;
        gameThread = new Thread(()->{
            while(timeLeft > 0 && person.blood > 0){
                if (score >= 30 || timeLeft <= 30) {
                    gameSpeed = 28;
                    person.speed = 17;
                    speedCheck = true;
                }
                try {
                    gameThread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                this.repaint();
            }
            remove(this);
            this.removeAll();
            this.repaint();
            this.add(new EndMenu());
        });
        gameThread.start();
        
        timer = new Timer(1000, (ActionEvent e) -> {
            if (timeLeft > 0) {
                timeLeft--;
            }
            this.repaint();
        });
        timer.start();
    }
    
    public MyGame(){
        resetGame();
        this.setBounds(0,0,500,600);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setLayout(null);
        this.setVisible(true);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(169,169,169));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.drawImage(darkCloudImage2, 0, 30, 350, 100, this);
        
        if (this.timeLeft<=30 || score>=30){
            g2.setColor(Color.GRAY);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.drawImage(darkCloudImage, 0, 30, 350, 100, this);
        }
        g2.drawImage(characterImage, person.x, person.y, person.width, person.height, this);
        g2.setFont(new Font("Comic Sans MS",Font.BOLD,25));
        g2.setColor(Color.WHITE);
        g2.drawString("Time: "+this.timeLeft, 233, 75);
//        g2.drawString("HP: "+person.blood+" %", 20, 80);
        g2.drawString("Score: "+score, 220, 40);
//        g2.drawString("Speed: "+MyGame.gameSpeed, 200, 60);
        
        g2.drawImage(heartImage,10,20, 20,20,this);
        g2.setStroke(new BasicStroke(18.0f));
        g2.setColor(new Color(241, 98, 69));
        if (person.blood>0){  
            g2.drawLine(60, 30,person.blood+60,30);
        }
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(6.0f));
        g2.drawRect(50,20,120,20);
        
        if (this.slowCheck){
                g2.drawImage(characterImage2, person.x, person.y, person.width, person.height, this);
        }
        for(Rain rain:rainSet){
            g2.drawImage(rainImage, rain.x, rain.y, rain.width, rain.height, this);
            if(Rain.checkHit(person,rain) && person.blood > 0){
                if (speedCheck){
                    score += 2;
                }
                else{
                    score += 1; 
                }
                rain.y = rain.yStart;
                rain.x = (int)(10+Math.floor(Math.random()*300));
            }
        }
        for(Ice ice:iceSet){
            g2.drawImage(iceImage, ice.x, ice.y, ice.width, ice.height, this);
            if(Ice.checkHit(person,ice)){
                if (person.blood > 0){
                    g2.setStroke(new BasicStroke(10.0f));
                    g2.setColor(Color.RED);
                    g2.drawRect(0, 10, 360, 570);
                    person.blood -= 5;
                    ice.y = ice.yStart;
                    ice.x = (int)(10+Math.floor(Math.random()*300));
                }
                
            }
        }
        for(IncreaseBlood in_blood:increaseBloodSet){
            g2.drawImage(bloodImage, in_blood.x, in_blood.y, in_blood.width, in_blood.height, this);
            if (IncreaseBlood.checkHit(person, in_blood)){
                if (person.blood < 100 && person.blood != 0){
                    g2.setStroke(new BasicStroke(10.0f));
                    g2.setColor(Color.GREEN);
                    g2.drawRect(0, 10, 360, 570);
                    person.blood += 2;
                    in_blood.y = in_blood.yStart;
                    in_blood.x = (int)(10+Math.floor(Math.random()*300));
                }
            }
        }
        for (SlowDown de_speed:slowDownSet){
            g2.drawImage(lighting1Image, de_speed.x, de_speed.y, de_speed.width, de_speed.height, this);
            if (SlowDown.checkHit(person, de_speed)){
                if (!this.slowCheck){
                    person.speed /= 2;
                    this.slowCheck = true;
                }
                de_speed.y = de_speed.yStart;
                de_speed.x = (int)(10+Math.floor(Math.random()*300));
                
                
            }
        }
        for (SpeedUp in_speed:speedUpSet){
            g2.drawImage(umbrellaImage, in_speed.x, in_speed.y, in_speed.width, in_speed.height, this);
            if (SpeedUp.checkHit(person, in_speed)){
                if (this.slowCheck){
                    person.speed *= 2;
                    this.slowCheck = false;
                }
                in_speed.y = in_speed.yStart;
                in_speed.x = (int)(10+Math.floor(Math.random()*300));
                
            }
        }   
    }
    private Rain[] makeRainSet(int rainNumber){
        Rain[] rainSet = new Rain[rainNumber];
        for (int i=0;i<rainNumber;i++){
            double rainLocation = 10+Math.floor(Math.random()*300);
            rainSet[i] = new Rain((int)rainLocation,60,30,50,this);
        }
        return rainSet;
    }
    private Ice[] makeIceSet(int iceNumber) {
        Ice[] iceSet = new Ice[iceNumber];
        for (int i=0;i<iceNumber;i++){
            double iceLocation = 10+Math.floor(Math.random()*300);
            iceSet[i] = new Ice((int)iceLocation,60,50,50,this);
        }
        return iceSet;
    }
    private IncreaseBlood[] makeIncreaseBloodSet(int increaseBloodNumber) {
        IncreaseBlood[] increaseBloodSet = new IncreaseBlood[increaseBloodNumber];
        for (int i=0;i<increaseBloodNumber;i++){
            double increaseBloodLocation = 10+Math.floor(Math.random()*300);
            increaseBloodSet[i] = new IncreaseBlood((int)increaseBloodLocation,60,40,50,this);
        }
        return increaseBloodSet;
    }
    private SlowDown[] makeSlowDownSet(int slowDownNumber) {
        SlowDown[] slowDownSet = new SlowDown[slowDownNumber];
        for (int i=0;i<slowDownNumber;i++){
            double slowDownLocation = 10+Math.floor(Math.random()*300);
            slowDownSet[i] = new SlowDown((int)slowDownLocation,60,30,50,this);
        }
        return slowDownSet;
    }
    private SpeedUp[] makeSpeedUpSet(int speedUpNumber) {
        SpeedUp[] speedUpSet = new SpeedUp[speedUpNumber];
        for (int i=0;i<speedUpNumber;i++){
            double speedUpLocation = 10+Math.floor(Math.random()*300);
            speedUpSet[i] = new SpeedUp((int)speedUpLocation,60,40,60,this);
        }
        return speedUpSet;
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==65 || e.getKeyCode()==37){
            person.left();
        }
        else if (e.getKeyCode()==68 || e.getKeyCode()==39){
            person.right();
        }
        this.repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {}
}

class StartMenu extends JPanel{
    //Game Name
    private final JLabel gameName = new JLabel("Collect Rain");
    //Rain
    private final JLabel rainPicture = new JLabel();
    private final ImageIcon rain = new ImageIcon("src/Pictures/Rain.png");
    //Dark Cloud
    private final JLabel darkCloudPicture = new JLabel();
    private final ImageIcon darkCloud = new ImageIcon("src/Pictures/DarkCloud.png");
    Image resizedImage = darkCloud.getImage().getScaledInstance(350, 100, Image.SCALE_SMOOTH);
    private final ImageIcon newDarkCloud = new ImageIcon(resizedImage);
    //Lighting
    private final JLabel lighting2Picture = new JLabel();
    private final ImageIcon lighting2 = new ImageIcon("src/Pictures/Lighting2.png");
    Image resizedImage2 = lighting2.getImage().getScaledInstance(300, 320, Image.SCALE_SMOOTH);
    private final ImageIcon newLighting = new ImageIcon(resizedImage2);
    //Character
    private final JLabel characterPicture = new JLabel();
    private final ImageIcon character = new ImageIcon("src/Pictures/Character.png");
    //Start Button
    private final JButton startButton = new JButton("Start");
    public StartMenu(){
        this.setBounds(0,0,500,600);
        gameName.setBounds(20, 130, 300, 100);
        gameName.setFont(new Font("Comic Sans MS",Font.BOLD,40));
        this.add(gameName);
        darkCloudPicture.setBounds(0, 20, 350, 100);
        darkCloudPicture.setIcon(newDarkCloud);
        this.add(darkCloudPicture);
        characterPicture.setBounds(15, 280, 200, 300);
        characterPicture.setIcon(character);
        this.add(characterPicture);
        lighting2Picture.setBounds(15, 50, 300, 320);
        lighting2Picture.setIcon(newLighting);
        this.add(lighting2Picture);
        rainPicture.setBounds(250, 80, 300, 200);
        rainPicture.setIcon(rain);
        this.add(rainPicture);
        startButton.setBounds(180, 400, 150, 70);
        startButton.setFont(new Font("Comic Sans MS",Font.BOLD,35));
        startButton.setFocusable(false);
        startButton.setBackground(new Color(211,211,211));
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5, true));
        startButton.addActionListener(e->{
            remove(this);
            this.removeAll();
            this.repaint();
            this.revalidate();
            this.setFocusable(true);
            MyGame game = new MyGame();
            game.resetGame();
            this.add(game);
            game.requestFocusInWindow();
        });
        this.add(startButton);
        this.setLayout(null);
        this.setVisible(true);
    }
}

class EndMenu extends JPanel{
    private final JButton restartButton = new JButton("Restart");
    private final JLabel showScores = new JLabel("Your Scores");
    private final JLabel yourScores = new JLabel(String.valueOf(MyGame.score));
    private final JLabel gameOver = new JLabel("Game Over !");
    public EndMenu(){
         this.setBounds(0,0,500,600);
         gameOver.setBounds(70,20,300,100);
         gameOver.setForeground(Color.RED);
         gameOver.setFont(new Font("Comic Sans MS",Font.BOLD,40));
         showScores.setBounds(70,150,300,100);
         showScores.setFont(new Font("Comic Sans MS",Font.BOLD,40));
         yourScores.setBounds(160,this.getWidth()/2,300,100);
         yourScores.setFont(new Font("Comic Sans MS",Font.BOLD,50));
         restartButton.setBounds(80, 400, 200, 100);
         restartButton.setFont(new Font("Comic Sans MS",Font.BOLD,35));
         restartButton.setFocusable(false);
         restartButton.setBackground(new Color(211,211,211));
         restartButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5, true));
         restartButton.addActionListener(e->{
            this.removeAll();
            remove(this);
            MyGame game = new MyGame();
            game.resetGame();
            this.repaint();
            this.revalidate();
            this.add(game);
            game.requestFocusInWindow();
         });
         this.add(gameOver);
         this.add(showScores);
         this.add(yourScores);
         this.add(restartButton);
         this.setLayout(null);
         this.setVisible(true);
    }
}