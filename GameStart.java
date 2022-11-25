
import java.awt.Graphics;
import javax.swing.*;

public class GameStart extends JPanel {
    public ImageIcon imgHome = new ImageIcon(this.getClass().getResource("bgstart.jpg"));
    public ImageIcon imgbtnStart = new ImageIcon(this.getClass().getResource("btnStart.png"));
    public ImageIcon imgbtnExit = new ImageIcon(this.getClass().getResource("exit.png"));
    public JButton btnStart = new JButton(imgbtnStart);
    public JButton btnExit = new JButton(imgbtnExit);
    public ImageIcon gimg = new ImageIcon(this.getClass().getResource("Picture1.png"));
    
    
    public GameStart()
    {
        this.setFocusable(true);
        this.setLayout(null);
        JPanel p1 = new JPanel();
        btnStart.setBounds(370, 600, 200, 60);
        btnExit.setBounds(370, 670, 200, 60);
        add(btnStart);
        add(btnExit);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(imgHome.getImage(), 0, 0, 600,800,this);
        g.drawImage(gimg.getImage(), 10,10, 600, 300, this);
    }
}
