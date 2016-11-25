import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.FileSystems;
import java.util.ArrayList;

import PictureFilter.FilterFactory;
import PictureFilter.FilterType;
import RGBImage.FilterablePicture;

class TabbedPane extends JPanel {


    private final JTabbedPane tabbedPane;
    private final ArrayList<FilterType> tabOrder;

    private  FilterablePicture currentPicture;

    //TODO allow custom layering of filters
    //TODO multiple args in memory
    //TODO better way to initialize memory
    //TODO add description of arguments

    private final Memory memory;

    TabbedPane(ArrayList<FilterType> tabOrder) {
        super(new GridLayout(1, 1));
        setLayout(new BorderLayout());
        buildMainMenu();

        memory = new Memory();

        FilterFactory.setLayer(memory.getDefaultLayerImage());

        tabbedPane = new JTabbedPane();
        this.tabOrder = tabOrder;
        for (FilterType type : tabOrder)
            createTab(tabbedPane, type);

        add(tabbedPane);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setTabPlacement(SwingConstants.LEFT);

        tabbedPane.addChangeListener((ChangeEvent e) -> reloadSelectedImage());

        reloadSelectedImage();
    }

    private void reloadSelectedImage(){
        int index = tabbedPane.getSelectedIndex();
        FilterType key = getTabsFilterType(index);

        File base = memory.baseGet(key);
        File layer = memory.layerGet(key);
        Double arg = memory.argGet(key);

        if (getTabsFilterType(index).isPictureCompound())
            loadImage(base, layer, index, arg);
        else
            loadImage(base, index, arg);
    }

    private FilterType getTabsFilterType(int index)    {
        return tabOrder.get(index);
    }

    private void setImage(ArrayList<JComponent> images, int index)   {
        //TODO allow two histograms to be loaded for comparison
        JPanel tab = (JPanel)(tabbedPane.getComponentAt(index));
        tab.removeAll();

        JPanel panel = new JPanel();

        if (getTabsFilterType(index).hasVariable()) {
            JPanel arg = new JPanel();
            TextField textField = new TextField();
            Button button = new Button("Apply");

            button.addActionListener(
                    (ActionEvent ae) -> {
                        String textFieldValue = textField.getText();
                        FilterType key = getTabsFilterType(tabbedPane.getSelectedIndex());
                        if (!textFieldValue.isEmpty())  {
                            Double value = Double.valueOf(textFieldValue);
                            memory.argPut(key, value);
                        }
                        else
                            memory.argReset(key);
                        reloadSelectedImage();

                    }
            );

            arg.add(textField);
            arg.add(button);
            panel.add(arg);
        }

        for (JComponent image : images)
            panel.add(image);

        tab.add(panel);
        tab.revalidate();
    }

    private void createTab(JTabbedPane tabbedPane, FilterType type) {
        JPanel panel = new JPanel(false);
        panel.setLayout(new GridLayout(1, 1));
        panel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        tabbedPane.addTab(type.getName(), panel);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
    }

    private FilterablePicture getFilterablePicture(File file, FilterType filterType, Double arg) {
        return new FilterablePicture(file.getPath(), filterType, arg);
    }

    private void loadImage(File file, int index, Double arg) {
        FilterType filterType = getTabsFilterType(index);

        FilterablePicture mainPicture = getFilterablePicture(file, filterType, arg);
        currentPicture = mainPicture;

        ArrayList<JComponent> images = new ArrayList<>();
        images.add(mainPicture.getJComponent());

        FilterType[] subTypes = filterType.getSubTypes();
        for (FilterType subType : subTypes)
            images.add(getFilterablePicture(file, subType, arg).getJComponent());

        //TODO what about the current picture? maybe I need an array instead
        setImage(images, index);
    }

    private void loadImage(File baseFile, File layerFile, int index, Double arg) {
        FilterFactory.setLayer(layerFile);
        loadImage(baseFile, index, arg);
    }

    private void buildMainMenu(){
        JMenuBar menuBar;
        JMenu menu;

        menuBar = new JMenuBar();

        menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        JMenuItem menuItem1 = new JMenuItem(new AbstractAction("Load base image...") {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(getDefaultDirectory());
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    FilterType key = getTabsFilterType(tabbedPane.getSelectedIndex());
                    File value = chooser.getSelectedFile();
                    memory.basePut(key, value);
                    reloadSelectedImage();
                }
            }}
        );
        menu.add(menuItem1);

        JMenuItem menuItem2 = new JMenuItem(new AbstractAction("Load layer image...") {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(getDefaultDirectory());
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    FilterType key = getTabsFilterType(tabbedPane.getSelectedIndex());
                    File value = chooser.getSelectedFile();
                    memory.layerPut(key, value);
                    FilterFactory.setLayer(chooser.getSelectedFile());
                    reloadSelectedImage();
                }
            }}
        );
        menu.add(menuItem2);

        JMenuItem menuItem3 = new JMenuItem(new AbstractAction("Save image as...") {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(getDefaultDirectory());
                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    currentPicture.save(file.getPath());
                }
            }}
        );
        menu.add(menuItem3);


        add(menuBar, BorderLayout.NORTH);
    }

    private File getDefaultDirectory() {
        return new File(FileSystems.getDefault().getPath("images").toString());
    }


}