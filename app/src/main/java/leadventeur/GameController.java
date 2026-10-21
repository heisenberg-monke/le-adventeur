package leadventeur;

import javax.swing.JOptionPane;

public class GameController 
{
    private final GameState state;
    private final SceneManager scenes;
    
    public GameController(GameState state, SceneManager scenes)
    {
        this.state = state;
        this.scenes = scenes;
    }

    public void handle(SceneOption option)
    {
        switch(option.action())
        {
            case "start" ->
            {
                state.reset();
                scenes.goTo(option.target());
            }

            case "attack" ->
            {
                state.damage(2);

                if(state.getHP() == 0)
                {
                    JOptionPane.showMessageDialog(null, "You have been defeated!");
                    scenes.goTo("title");
                }

                else
                    scenes.goTo(option.target());
            }

            case "heal" ->
            {
                state.heal();
                scenes.goTo(option.target());
            }

            case "back" -> scenes.back();
            case "quit" -> System.exit(0);
            
            default -> scenes.goTo(option.target());
        }
    }
}
