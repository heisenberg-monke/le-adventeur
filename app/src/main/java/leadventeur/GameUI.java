package leadventeur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class GameUI 
{
    private final JPanel root = new JPanel(new BorderLayout(12, 12));
    private final JLabel title = new JLabel("", SwingConstants.CENTER);
    private final JTextArea description = new JTextArea();
    private final JPanel choices = new JPanel(new GridBagLayout());
    private final JTextArea status = new JTextArea();

    private static final Color BACKGROUND = new Color(15, 18, 24);
    private static final Color PANEL = new Color(29, 35, 45);
    private static final Color ACCENT = new Color(100, 210, 165);
    private static final Color TEXT = new Color(240, 240, 240);

    public GameUI()
    {
        root.setBackground(BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        title.setForeground(ACCENT);
        title.setFont(new Font("Serif", Font.BOLD, 32));

        root.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(12, 16));

        center.setBackground(PANEL);
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        description.setEditable(false);
        description.setFocusable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setOpaque(false);
        description.setForeground(TEXT);
        description.setFont(new Font("SansSerif", Font.PLAIN, 18));
        description.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        choices.setBackground(PANEL);

        center.add(description, BorderLayout.CENTER);
        center.add(choices, BorderLayout.SOUTH);
        
        root.add(center, BorderLayout.CENTER);

        status.setEditable(false);
        status.setFocusable(false);
        status.setOpaque(false);
        status.setForeground(ACCENT);
        status.setFont(new Font("Monospaced", Font.PLAIN, 14));
        status.setAlignmentX(Component.CENTER_ALIGNMENT);
        status.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        root.add(status, BorderLayout.SOUTH);
    }

    public JPanel getRoot() {
        return root;
    }

    public void render(GameScene scene, GameState state, Consumer<SceneOption> onChoice)
    {
        title.setText(scene.title());
        description.setText(scene.text());
        status.setText(state.toString());

        choices.removeAll();

        int count = scene.options().size();

        if(count > 0)
        {
            choices.setLayout(new GridBagLayout());

            for(int i = 0; i < count; ++i)
            {
                SceneOption option = scene.options().get(i);
                JButton button = new JButton(option.label());

                button.setFont(new Font("SansSerif", Font.BOLD, 15));
                button.setForeground(TEXT);
                button.setBackground(new Color(43, 53, 67));
                button.setFocusPainted(false);
                button.setMargin(new Insets(10, 14, 10, 14));
                button.addActionListener(e -> onChoice.accept(option));

                GridBagConstraints c = new GridBagConstraints();

                c.gridx = 0;
                c.gridy = i;
                c.weightx = 1;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = new Insets(6, 0, 6, 0);

                choices.add(button, c);
            }
        }

        root.revalidate();
        root.repaint();
    }
}
