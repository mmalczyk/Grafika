import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.FileSystems;
import java.util.HashMap;

import PictureFilter.FilterFactory;
import PictureFilter.FilterType;
import RGBImage.FilterablePicture;

public class TabbedPane extends JPanel {

    private File defaultBaseImage = new File("images\\LENA_512.jpg");
    private File defaultLayerImage = new File("images\\eagle.jpg");
    private Double defaultArg = null;

    private JTabbedPane tabbedPane;
    private FilterType[] tabOrder;

    private  FilterablePicture currentPicture;

    //TODO allow custom layering of filters

    //TODO refactor memory into inner class
    private HashMap<FilterType, File> baseImageMemory = new HashMap<>();
    private HashMap<FilterType, File> layerImageMemory = new HashMap<>();
    private HashMap<FilterType, Double> argMemory = new HashMap<>();

    private void loadMemory()   {
        //TODO: memory as separate class
        argMemory.put(FilterType.Sepia, 40.);

        argMemory.put(FilterType.Rotated, 30.);

        argMemory.put(FilterType.Displaced, 300.);

        argMemory.put(FilterType.Faded, 200.);

        baseImageMemory.put(FilterType.Added, new File("images\\lake.jpg"));
        layerImageMemory.put(FilterType.Added, new File("images\\eagle.jpg"));
        argMemory.put(FilterType.Added, 0.5);

        baseImageMemory.put(FilterType.AddedAndSaturated, new File("images\\lake.jpg"));
        layerImageMemory.put(FilterType.AddedAndSaturated, new File("images\\eagle.jpg"));
        argMemory.put(FilterType.AddedAndSaturated, 0.8);

        baseImageMemory.put(FilterType.AddedWithTransparency, new File("images\\sphere.jpg"));
        layerImageMemory.put(FilterType.AddedWithTransparency, new File("images\\texture.jpg"));
        argMemory.put(FilterType.AddedWithTransparency, 0.3);

        baseImageMemory.put(FilterType.Subtracted, new File("images\\lake.jpg"));
        layerImageMemory.put(FilterType.Subtracted, new File("images\\eagle.jpg"));

        baseImageMemory.put(FilterType.DifferencesBySubtraction, new File("images\\cinderella1.png"));
        layerImageMemory.put(FilterType.DifferencesBySubtraction, new File("images\\cinderella2.png"));

        baseImageMemory.put(FilterType.DifferencesByDivision, new File("images\\guy1.png"));
        layerImageMemory.put(FilterType.DifferencesByDivision, new File("images\\guy2.png"));

        baseImageMemory.put(FilterType.Multiplied, new File("images\\LENA_512.jpg"));
        layerImageMemory.put(FilterType.Multiplied, new File("images\\circle.jpg"));

        baseImageMemory.put(FilterType.Divided, new File("images\\lake.jpg"));
        layerImageMemory.put(FilterType.Divided, new File("images\\eagle.jpg"));

        argMemory.put(FilterType.Contrast, 1.9);

        baseImageMemory.put(FilterType.SpreadAndSmoothed, new File("images\\kobieta.jpg"));

        baseImageMemory.put(FilterType.SpreadSmoothedGreyscale, new File("images\\kobieta.jpg"));
    }

    public TabbedPane(FilterType[] tabOrder) {
        super(new GridLayout(1, 1));
        setLayout(new BorderLayout());
        buildMainMenu();

        loadMemory();
        FilterFactory.setLayer(defaultLayerImage);

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

        File base, layer;
        Double arg;
        if(baseImageMemory.containsKey(key))
            base = baseImageMemory.get(key);
        else
            base = defaultBaseImage;
        if(layerImageMemory.containsKey(key))
            layer = layerImageMemory.get(key);
        else
            layer = defaultLayerImage;
        if(argMemory.containsKey(key))
            arg = argMemory.get(key);
        else
            arg = defaultArg;


        if (getTabsFilterType(index).isCompound())
            loadImage(base, layer, index, arg);
        else
            loadImage(base, index, arg);
    }

    private FilterType getTabsFilterType(int index)    {
        return tabOrder[index];
    }

    private void setImage(JComponent image, int index)   {
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
                        Double value = Double.valueOf(textFieldValue);
                        FilterType key = getTabsFilterType(tabbedPane.getSelectedIndex());
                        if(argMemory.containsKey(key))
                            argMemory.remove(key);
                        argMemory.put(key, value);
                        reloadSelectedImage();
                    }
            );

            arg.add(textField);
            arg.add(button);
            panel.add(arg);
        }
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

    private void loadImage(File file, int index, Double arg) {
        FilterablePicture picture = new FilterablePicture(
                file.getPath(),
                getTabsFilterType(index),
                arg
        );

        currentPicture = picture;

        setImage(picture.getJComponent(), index);
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
                    if(baseImageMemory.containsKey(key))
                        baseImageMemory.remove(key);
                    baseImageMemory.put(key, chooser.getSelectedFile());

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
                    if(layerImageMemory.containsKey(key))
                        layerImageMemory.remove(key);
                    layerImageMemory.put(key, chooser.getSelectedFile());

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