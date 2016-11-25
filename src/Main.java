import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import PictureFilter.FilterType;

/**
 * Created by Magda on 15.10.2016.
 */
class Main {

    private final ArrayList<FilterType> tabOrder = FilterType.getMainTypes();


    @SuppressWarnings("FieldCanBeLocal")
    private TabbedPane tabbedPane;

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Portfolio");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        tabbedPane = new TabbedPane(tabOrder);
        frame.add(tabbedPane, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                (new Main()).createAndShowGUI();
            });
    }

}
