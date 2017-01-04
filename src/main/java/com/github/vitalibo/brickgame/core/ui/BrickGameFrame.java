package com.github.vitalibo.brickgame.core.ui;

import com.github.vitalibo.brickgame.util.Builder;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BrickGameFrame extends JFrame {

    @Getter
    private final BrickPanel board;
    @Getter
    private final BrickPanel preview;
    @Getter
    private final NumberPanel score;
    @Getter
    private final NumberPanel speed;
    @Getter
    private final NumberPanel level;
    @Getter
    private final IconPanel sound;
    @Getter
    private final IconPanel pause;

    public BrickGameFrame() {
        super("Brick Game");
        this.board = new BrickPanel(10, 20);
        this.preview = new BrickPanel(4, 4);
        this.score = new NumberPanel(6);
        this.speed = new NumberPanel(2, 15);
        this.level = new NumberPanel(2, 15);
        this.sound = Builder.of(new IconPanel("sound_on", "sound_off", true))
            .with(i -> i.setPreferredSize(new Dimension(16, 16)))
            .get();
        this.pause = Builder.of(new IconPanel("pause_on", "pause_off"))
            .with(i -> i.setPreferredSize(new Dimension(40, 13)))
            .get();
        this.init();
    }

    private void init() {
        this.setBackground(new Color(0x6D785C));
        this.setContentPane(root());
        this.pack();
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(new Dimension(190, 260));
        this.setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) ((dimension.getWidth() - this.getWidth()) / 2),
            (int) ((dimension.getHeight() - this.getHeight()) / 2));
        this.setVisible(true);
    }

    private JPanel root() {
        return Builder.of(new JPanel())
            .with(root -> root.setOpaque(false))
            .with(root -> Builder.of(board)
                .with(frame -> frame.setBorder(new LineBorder(new Color(0x0), 1)))
                .with(root::add))
            .with(root -> panel(new GridLayout(0, 1))
                .with(panel -> panel(new GridLayout(0, 1))
                    .with(o -> o.add(label("Score", JLabel.RIGHT)))
                    .with(o -> o.add(score))
                    .with(panel::add))
                .with(panel -> panel(new FlowLayout(FlowLayout.CENTER, 0, 0))
                    .with(o -> o.add(preview))
                    .with(panel::add))
                .with(panel -> panel(new GridLayout(0, 1))
                    .with(o -> o.add(label("Speed", JLabel.CENTER)))
                    .with(o -> o.add(speed))
                    .with(panel::add))
                .with(panel -> panel(new GridLayout(0, 1))
                    .with(o -> o.add(level))
                    .with(o -> o.add(label("Level", JLabel.CENTER)))
                    .with(panel::add))
                .with(panel -> panel(new GridLayout(0, 1))
                    .with(o -> o.add(sound))
                    .with(o -> o.add(pause))
                    .with(panel::add))
                .with(root::add))
            .get();
    }

    private static JLabel label(String text, int alignment) {
        return Builder.of(new JLabel(text))
            .with(l -> l.setFont(new Font("Consolas", Font.BOLD, 11)))
            .with(l -> l.setHorizontalAlignment(alignment))
            .get();
    }

    private static Builder<JPanel> panel(LayoutManager layout) {
        return Builder.of(new JPanel(layout))
            .with(p -> p.setOpaque(false));
    }

}