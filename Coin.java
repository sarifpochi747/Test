
import javax.swing.*;
import java.awt.geom.Rectangle2D;
public class Coin {
    public int x = (int)((Math.random()*350) + 100);
    public int y = -100;
    public int count =0;
    public int width = 50;
    public int height  =50;
    public ImageIcon[] imgCoin = new ImageIcon[6];
    public Thread coinMove;
    public Coin(JPanel game)
    {
        for(int i=0;i<imgCoin.length;i++)
        {
            imgCoin[i] = new ImageIcon(this.getClass().getResource("./"+(i+1)+".png"));
        }
        coinMove = new Thread(new Runnable(){
            @Override
            public void run() {
                while(true)
                {
                    count ++;
                    y += 10;
                    game.repaint();
                    try{
                        Thread.sleep(60);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        });
        coinMove.start();
    }
    
    
    public Rectangle2D getBounds()
    {
        return (new Rectangle2D.Double(x, y, width,height ));
    }
    
}