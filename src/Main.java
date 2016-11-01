import javax.swing.*;
import java.awt.*;

import PictureFilter.FilterType;

/**
 * Created by Magda on 15.10.2016.
 */
public class Main {

    private FilterType[] tabOrder = FilterType.values();


    private TabbedPane tabbedPane;

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Portfolio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new TabbedPane(tabOrder);
        frame.add(tabbedPane, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                (new Main()).createAndShowGUI();
            }
        });
    }

}
