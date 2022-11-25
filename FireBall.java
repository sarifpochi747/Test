
import java.awt.geom.Rectangle2D;
import javax.swing.*;
public class FireBall {
    public int x;
    public int y;
    public int yStart;
    public int width = 30;
    public int height = 30;
    public int count = 0;
    public ImageIcon[] fireBallimg = new ImageIcon[5];
    public Thread th_move;
    public FireBall(int x,int y1,JPanel game)
    {
        this.x = x;
        this.y = y1;
        yStart = y;
        for(int i=0;i<fireBallimg.length;i++)
        {
            fireBallimg[i] = new ImageIcon(this.getClass().getResource("b"+(i+1)+".png"));
        }
        th_move = new Thread(new Runnable(){
            @Override
            public void run()
            {
                while(true)
                {
                    count++;
                    y -= 10;
                    game.repaint();
                    try{
                        Thread.sleep(20);
                    }catch(InterruptedException e){}
                }
            }
        });
        
        th_move.start();
    }
    
    
    public Rectangle2D getBounds()
    {
        return (new Rectangle2D.Double(x, y, width,height ));
    }
}
