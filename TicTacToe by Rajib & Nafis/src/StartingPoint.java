
import javax.swing.JFrame;


public class StartingPoint 
{
    
    public static JFrame frame;
    public static int BoardSize, difficulty;
    
    public static void main(String[] args) 
    {
        BoardSize=10;
        difficulty=0;
        
        frame = new FrontPage();
        //frame = new GamePlay();
        frame.setVisible(true);
    }
}
