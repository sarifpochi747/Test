

import javax.swing.*;
import java.awt.event.*;
class Display extends JFrame implements ActionListener{
    
    GameStart gameStart = new GameStart();
    GamePlay gamePlay = new GamePlay();
    Display()
    {
        add(gameStart);
        gameStart.btnStart.addActionListener(this);
        gameStart.btnExit.addActionListener(this);
        gamePlay.btnreplay.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == gameStart.btnStart )
        {
            gamePlay.actor.Hp = 200;
            gamePlay.score =0;
            gamePlay.arrayCoin.clear();
            gamePlay.arrayWave.clear();
            gamePlay.fireball.clear();
            gamePlay.start = true;
            this.setLocationRelativeTo(null);
            this.remove(gameStart);
            this.add(gamePlay);
            gamePlay.requestFocusInWindow();
        }
        else if(e.getSource()== gameStart.btnExit)
        {
            System.exit(0);
        }
        else if(e.getSource() == gamePlay.btnreplay)
        {
            gamePlay.actor.Hp = 200;
            gamePlay.score =0;
            gamePlay.start = false;
            this.setLocationRelativeTo(null);
            this.remove(gamePlay);
            this.add(gameStart);
            gamePlay.requestFocusInWindow();
        }
        this.validate();
        this.repaint();
    }
    
    
    
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame frame = new Display();
        frame.setTitle("Game");
        frame.setSize(600,800);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    
}
