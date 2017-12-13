
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rajib
 */
public class GamePlay extends javax.swing.JFrame 
{
    
    private static JButton b[][] = new JButton[100][100];
    private static int Board[][] = new int[100][100];
    private static AI_Strategy AI;
    public static ButtonHandler handler = new ButtonHandler();
    private static int n=15;
    private static boolean turn;
    private static ImageIcon XXX = new ImageIcon("images/XXX.jpg");
    private static ImageIcon OOO = new ImageIcon("images/OOO.jpg");
    private static int MovesKount;
    private static Stack<Point> stk = new Stack<Point>();
    private ImageIcon img = new ImageIcon("images/gamePlay.jpg");
    private JLabel background;
    
    public String GetDifficultyName()
    {
        
        String diff = "";
        int dd = StartingPoint.difficulty;
        if(dd==0) diff="Beginner";
        else if(dd==1) diff="Intermediate";
        else diff="Advanced";
        return diff;
    }
    
    public GamePlay() 
    {
        super("FIVE-IN-A-ROW Tic Tac Toe");
        background = new JLabel(img);
        setContentPane(background);
        n = StartingPoint.BoardSize;
        Random rand = new Random();
        int rr = rand.nextInt();
        if(rr%3==0) turn=false;
        else turn=true;
        
        
        stk.clear();
        initComponents();
        InitializeGameBoard(n);
        AI = new AI_Strategy();
        setLayout(null);
        add(GamePanel);
        validate();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        GameLabel.setBounds(650, 100, 350, 30);
        MoveLabel.setBounds(650, 200, 350, 30);
        diffLabel.setBounds(650, 300, 350, 30);
        diffLabel.setText("Difficulty: "+GetDifficultyName());
        MovesKount=0;
        
        if (turn == true) 
        {
            int x,y;
            Point p;
            p = AI.getOptimalMove(Board, n, 2);
            stk.add(p);
            //System.out.println("Yes Integer found " + p.x + " " + p.y);
            x = p.x;
            y = p.y;
            b[x][y].setIcon(OOO);
            Board[x][y] = 2;
            if (AI.Dead_Five(Board, x, y, 2) >= 1) 
            {
                System.out.println("Computer is the winner");
                JOptionPane.showMessageDialog(null, "Computer is the winner");
                

            }
            MovesKount++;
            MoveLabel.setText("Moves: " + MovesKount);
//            TurnLabel.setText("Turn: Human");
            turn = false;
        }
        
    }
    
    private void InitializeGameBoard(int n)
    {
        GamePanel.setLayout(null);
        int SZ = 600/n;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                b[i][j]=new JButton("");
                b[i][j].setBounds(i*SZ, j*SZ, SZ,SZ);
                b[i][j].setForeground(Color.gray);
                GamePanel.add(b[i][j]);
                b[i][j].addActionListener(handler);
                Board[i][j]=0;
            }
        }
        
        GamePanel.setVisible(true);
        GamePanel.setBounds(10, 40, 600, 600);
    }
    
    public static class ButtonHandler implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
            int x = 0, y = 0;
            for(int i=0;i<n;i++)
                for(int j=0;j<n;j++)
                    if(e.getSource()==b[i][j])
                    {
                        //System.out.println(i+", "+j);
                    
                        if(Board[i][j]==0)
                        {
                            x=i;
                            y=j;
                            System.out.println("Human's Skilled Move: "+i+", "+j);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Please Select an Empty Slot");
                            return;
                        }
                            
                        
                    }
            if(turn==false)
            {
                
                stk.add(new Point(x, y));
                b[x][y].setIcon(XXX);
                
                Board[x][y]=1;
                if(AI.Dead_Five(Board, x, y, 1)>=1)
                {
                    System.out.println("First Player is the winner");
                    MovesKount++;
                    MoveLabel.setText("Moves: "+MovesKount);
                    JOptionPane.showMessageDialog(null, "First Player is the winner");
                    newGame.doClick();
                    return;
                    
                }
                MovesKount++;
                MoveLabel.setText("Moves: "+MovesKount);
//                TurnLabel.setText("Turn: Computer");
                turn=true;
                if(MovesKount==StartingPoint.BoardSize*StartingPoint.BoardSize)
                {
                    JOptionPane.showMessageDialog(null, "Drawn");
                    newGame.doClick();
                    return;
                }
                
            }
            if(turn==true)
            {
               
                Point p;
                p = AI.getOptimalMove(Board, n, 2);
                stk.add(p);
                
                
                //s System.out.println("Yes Integer found "+p.x+" "+p.y);
                x = p.x;
                y = p.y;
                b[x][y].setIcon(OOO);
                Board[x][y]=2;
                if(AI.Dead_Five(Board, x, y, 2)>=1)
                {
                    System.out.println("Computer is the winner");
                    MovesKount++;
                    MoveLabel.setText("Moves: "+MovesKount);
                    JOptionPane.showMessageDialog(null, "Computer is the winner");
                    newGame.doClick();
                    return;
                    
                }
                MovesKount++;
                MoveLabel.setText("Moves: "+MovesKount);
//                TurnLabel.setText("Turn: Human");
                turn=false;
                if(MovesKount==StartingPoint.BoardSize*StartingPoint.BoardSize)
                {
                    JOptionPane.showMessageDialog(null, "Drawn");
                    newGame.doClick();
                    return;
                }
                
                
            }
            
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GamePanel = new javax.swing.JPanel();
        GameLabel = new javax.swing.JLabel();
        MoveLabel = new javax.swing.JLabel();
        newGame = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        undo = new javax.swing.JButton();
        diffLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout GamePanelLayout = new javax.swing.GroupLayout(GamePanel);
        GamePanel.setLayout(GamePanelLayout);
        GamePanelLayout.setHorizontalGroup(
            GamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 654, Short.MAX_VALUE)
        );
        GamePanelLayout.setVerticalGroup(
            GamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );

        GameLabel.setFont(new java.awt.Font("Trajan Pro", 1, 20)); // NOI18N
        GameLabel.setForeground(new java.awt.Color(255, 255, 255));
        GameLabel.setText("GAme: Human vs Computer");

        MoveLabel.setFont(new java.awt.Font("Trajan Pro", 1, 20)); // NOI18N
        MoveLabel.setForeground(new java.awt.Color(255, 255, 255));
        MoveLabel.setText("Moves: ");

        newGame.setText("New Game");
        newGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        undo.setText("Undo");
        undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoActionPerformed(evt);
            }
        });

        diffLabel.setFont(new java.awt.Font("Trajan Pro", 1, 20)); // NOI18N
        diffLabel.setForeground(new java.awt.Color(255, 255, 255));
        diffLabel.setText("Difficulty:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(GamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GameLabel)
                    .addComponent(MoveLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(newGame)
                        .addGap(18, 18, 18)
                        .addComponent(undo, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(diffLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(74, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(GamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(GameLabel)
                        .addGap(30, 30, 30)
                        .addComponent(MoveLabel)
                        .addGap(18, 18, 18)
                        .addComponent(diffLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(newGame)
                            .addComponent(backButton)
                            .addComponent(undo))
                        .addGap(152, 152, 152))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameActionPerformed
        // TODO add your handling code here:
        StartingPoint.frame.dispose();
        StartingPoint.frame = new GamePlay();
        StartingPoint.frame.setVisible(true);
    }//GEN-LAST:event_newGameActionPerformed

    private void undoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoActionPerformed
        // TODO add your handling code here:
        if(stk.size()<2) return;
        turn=false;
        Point pp = new Point(0, 0);
        pp = stk.pop();
        if(Board[pp.x][pp.y]==1)
        {
            MovesKount--;
            Board[pp.x][pp.y]=0;
            b[pp.x][pp.y].setIcon(null);
            return;
        }
        Board[pp.x][pp.y]=0;
        b[pp.x][pp.y].setIcon(null);
        
        pp = stk.pop();
        Board[pp.x][pp.y]=0;
        b[pp.x][pp.y].setIcon(null);
        MovesKount-=2;
        MoveLabel.setText("Moves: "+MovesKount);
        
    }//GEN-LAST:event_undoActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        StartingPoint.frame.dispose();
        StartingPoint.frame = new FrontPage();
        StartingPoint.frame.setVisible(true);
    }//GEN-LAST:event_backButtonActionPerformed

    /**
     * @param args the command line arguments
     */
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JLabel GameLabel;
    private javax.swing.JPanel GamePanel;
    private static javax.swing.JLabel MoveLabel;
    private javax.swing.JButton backButton;
    private javax.swing.JLabel diffLabel;
    private static javax.swing.JButton newGame;
    private javax.swing.JButton undo;
    // End of variables declaration//GEN-END:variables
}


