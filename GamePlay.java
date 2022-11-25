
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;
public class GamePlay extends JPanel implements ActionListener{
    
    private ImageIcon imgbg = new ImageIcon(this.getClass().getResource("./bgplay.jpg"));
    private ImageIcon imgPause = new ImageIcon(this.getClass().getResource("./pause.png"));
    private ImageIcon imgPlay = new ImageIcon(this.getClass().getResource("./play.png"));
    
    public ImageIcon gameOver = new ImageIcon(this.getClass().getResource("./gameOver.jpg"));
    public ImageIcon imgreplay = new ImageIcon(this.getClass().getResource("./replay.png"));
    public JButton btnreplay = new JButton(imgreplay);
    
    private JButton BPause = new JButton(imgPause);
    private JButton BPlay = new JButton(imgPlay);
    
    public ArrayList<FireBall> fireball = new ArrayList<FireBall>();
    public ArrayList<Coin> arrayCoin  = new ArrayList<Coin>();
    public ArrayList<Wave> arrayWave  = new ArrayList<Wave>();
    public Actor actor = new Actor();
    
    public  int score = 0;
    public boolean start = false;

    //create object actor;
    //Thread Actor
    public Thread actorThread = new Thread(new  Runnable(){
        @Override
        public void run() {
            while(true)
            {
                actor.count ++;

                try{
                    Thread.sleep(80);
                } catch (InterruptedException ex) {
                }
                repaint();
            }
        }
    });

    public Thread add_c_w = new Thread(new Runnable(){
        @Override
        public void run() {
            while(true)
            {
                if(start)
                {
                    addCoinWave(false);
                }
                try{
                    Thread.sleep((int)(Math.random() * 5000) + 1000);
                } catch (InterruptedException ex) {
                }
            }
        }
    });
    
    
    public void addCoinWave(boolean flag)
    {
        if(flag)
        {
            fireball.add(new FireBall(actor.x+19,actor.y-33,this));
        }
        else
        {
            arrayCoin.add(new Coin(this));
            arrayWave.add(new Wave(this));
        }
            
    }


    public GamePlay()
    {
        this.setLayout(null);
        this.setFocusable(true);
        //btn game over
        btnreplay.setBounds(200, 600, 200, 60);
        BPlay.setBounds(500, 40, 40, 40);
        BPause.setBounds(540, 40, 40, 40);
        this.add(btnreplay);
        this.add(BPlay);
        this.add(BPause);
        
        BPlay.addActionListener(this);
        BPause.addActionListener(this);
        this.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == 37&&actor.x >= 100 )
                {
                    actor.moveLeft();
                    repaint();
                }
                if(e.getKeyCode() == 39 && actor.x <=430 )
                {
                    actor.moveRight();
                    repaint();
                }
                if(e.getKeyCode() == 38 && actor.y >= 5)
                {
                    actor.y -=10;
                    repaint();
                }
                if(e.getKeyCode() == 40 && actor.y <= actor.yStart)
                {
                    actor.y +=10;
                    repaint();
                }
                if(start)
                {
                    if(e.getKeyCode() == 32)
                    {
                        System.out.println(start);
                        addCoinWave(true);

                    }
                }
            }
        });
        actorThread.start();
        add_c_w.start();
        

    }


    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(actor.Hp > 0)
        {
            //draw bg
            this.add(BPause);
            this.add(BPlay);
            this.remove(btnreplay);

            g.drawImage(imgbg.getImage(), 0, 0, 600,800, this);

            //draw actor
            g.drawImage(actor.imgActor[actor.count%9].getImage(),actor.x, actor.y,actor.width , actor.height,this);

            //draw wave
            for(int i=0;i<arrayWave.size();i++)
            {
                Wave w = arrayWave.get(i);
                g.drawImage(w.imgWave.getImage(), w.x, w.y,w.width,w.height,this);

            }

            //draw coint
            for(int i=0;i<arrayCoin.size();i++)
            {
                Coin c = arrayCoin.get(i);
                g.drawImage(c.imgCoin[c.count%6].getImage(), c.x, c.y, c.width,c.height,this);
                if(c.y > 800)
                {
                    arrayCoin.remove(i);
                }

            }

            //draw fire ball
            for(int i=0;i<fireball.size();i++)
            {
                FireBall f = fireball.get(i);
                g.drawImage(f.fireBallimg[f.count%5].getImage(),f.x, f.y, f.width, f.height,null);
                if(f.y < 0 )
                {
                    fireball.remove(i);
                }
            }

            //draw score
            g.setFont(new Font("Hobo Std", Font.BOLD, 30));
            g.setColor(Color.BLUE);
            g.drawString("Score: "+score, 10, this.getHeight()-10);


            //draw Hp
            g.setFont(new Font("Hobo Std", Font.BOLD, 20));
            g.setColor(Color.black);
            g.drawString("HP ", 350, this.getHeight()-15);
            g.setColor(Color.white);
            g.drawRect(380, this.getHeight() - 40, 200, 30);
            g.setColor(Color.red);
            g.fillRect(380, this.getHeight() - 40, actor.Hp, 30);
            
            /////////////////////  check hit wave  //////////////////////////////////////
            for(int i=0;i<fireball.size();i++)
            {
                for(int j=0;j<arrayWave.size();j++)
                {
                    if( Intersect(fireball.get(i).getBounds(),arrayWave.get(j).getBounds()) )
                    {
                        fireball.remove(i);
                        arrayWave.remove(j);
                    }

                }
            }

            /////////////////////  check hit coin  //////////////////////////////////////
            for(int i=0;i<arrayCoin.size();i++)
            {
                if( Intersect(actor.getBounds(),arrayCoin.get(i).getBounds())  )
                {
                    score +=10;
                    arrayCoin.remove(i);
                }
            }


            /////////////////////  check hit wave  //////////////////////////////////////
            for(int i=0;i<arrayWave.size();i++)
            {
                if( Intersect(actor.getBounds(),arrayWave.get(i).getBounds())  )
                {
                    actor.Hp -=60;
                    arrayWave.remove(i);
                }
            }
        }

        if(actor.Hp <= 0)
        {
            this.remove(BPause);
            this.remove(BPlay);
            this.add(btnreplay);
            g.drawImage(gameOver.getImage(), 0, 0, 600,800,this);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Hobo Std", Font.HANGING_BASELINE, 40));
            g.drawString("SCORE : " + score, 240, 550);


        }

    }


    public boolean Intersect(Rectangle2D a, Rectangle2D b) {
        return (a.intersects(b));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == BPlay)
        {
            BPlay.setFocusable(false);
            actorThread.resume();
            add_c_w.resume();
            for(int i=0;i<fireball.size();i++)
            {
                FireBall f = fireball.get(i);
                f.th_move.resume();
            }
            for(int i=0;i<arrayCoin.size();i++)
            {
                Coin c = arrayCoin.get(i);
                c.coinMove.resume();
            }
            for(int i=0;i<arrayWave.size();i++)
            {
                Wave w = arrayWave.get(i);
                w.waveMove.resume();
            }
            start = true;
            
        }
        if(e.getSource() == BPause)
        {
            
            BPause.setFocusable(false);
            actorThread.suspend();
            add_c_w.suspend();
            for(int i=0;i<fireball.size();i++)
            {
                FireBall f = fireball.get(i);
                f.th_move.suspend();
            }
            for(int i=0;i<arrayCoin.size();i++)
            {
                Coin c = arrayCoin.get(i);
                c.coinMove.suspend();
            }
            for(int i=0;i<arrayWave.size();i++)
            {
                Wave w = arrayWave.get(i);
                w.waveMove.suspend();
            }
            start = false;
        }
        
    }

    
}
