package leadventeur;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class App 
{
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> 
        {
            try {
                new Game();
            }   
            
            catch(Exception e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Could not load the game: " + e.getMessage(), "Startup error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
