package leadventeur;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Game 
{
    private final JFrame window = new JFrame("LE ADVENTURER");

    private final GameState state = new GameState();
    private final SceneManager sceneManager = new SceneManager();
    private final GameUI ui = new GameUI();
    private final GameController controller = new GameController(state, sceneManager);
    
    public Game() throws Exception
    {
        for(var scene : new SceneLoader().loadResources("scenes.txt"))
            sceneManager.register(scene);

        if(sceneManager.get("title") == null)
            throw new IllegalStateException("scenes.txt must define a `title` scene");

        sceneManager.setOnChange(scene -> ui.render(scene, state, controller::handle));

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setMinimumSize(new Dimension(520, 420));
        window.setSize(900, 650);
        window.setLocationRelativeTo(null);
        window.setContentPane(ui.getRoot());

        sceneManager.goTo("title");

        window.setVisible(true);
    }
}
