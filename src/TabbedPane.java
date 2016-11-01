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

    private JTabbedPane tabbedPane;
    private FilterType[] tabOrder;

    //TODO refactor memory into inner class
    private HashMap<FilterType, File> baseImageMemory = new HashMap<>();
    private HashMap<FilterType, File> layerImageMemory = new HashMap<>();

    private void loadMemory()   {
        baseImageMemory.put(FilterType.Added, new File("images\\lake.jpg"));
        layerImageMemory.put(FilterType.Added, new File("images\\eagle.jpg"));

        baseImageMemory.put(FilterType.AddedAndSaturated, new File("images\\lake.jpg"));
        layerImageMemory.put(FilterType.AddedAndSaturated, new File("images\\eagle.jpg"));

        baseImageMemory.put(FilterType.AddedWithTransparency, new File("images\\sphere.jpg"));
        layerImageMemory.put(FilterType.AddedWithTransparency, new File("images\\texture.jpg"));

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

        tabbedPane.addChangeListener((ChangeEvent e) -> reloadSelectedImage());

        reloadSelectedImage();
    }

    private void reloadSelectedImage(){
        int index = tabbedPane.getSelectedIndex();
        FilterType key = getTabsFilterType(index);

        File base, layer;
        if(baseImageMemory.containsKey(key))
            base = baseImageMemory.get(key);
        else
            base = defaultBaseImage;
        if(layerImageMemory.containsKey(key))
            layer = layerImageMemory.get(key);
        else
            layer = defaultLayerImage;

        if (getTabsFilterType(index).isCompound())
            loadImage(base, layer, index);
        else
            loadImage(base, index);
    }

    private FilterType getTabsFilterType(int index)    {
        return tabOrder[index];
    }

    public void setImage(JLabel image, int index)   {
        JPanel tab = (JPanel)tabbedPane.getComponentAt(index);
        tab.removeAll();
        tab.add(image);
        tab.revalidate();
    }

    private void createTab(JTabbedPane tabbedPane, FilterType type) {
        JPanel panel = new JPanel(false);
        panel.setLayout(new GridLayout(1, 1));
        panel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        //buildTabMenu(panel);
        tabbedPane.addTab(type.getName(), panel);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
    }

    private void loadImage(File file, int index) {
        FilterablePicture picture = new FilterablePicture(
                file.getPath(),
                getTabsFilterType(index)
        );

        setImage(picture.getPicture().getJLabel(), index);
    }

    private void loadImage(File baseFile, File layerFile, int index) {
        FilterFactory.setLayer(layerFile);
        FilterablePicture picture = new FilterablePicture(
                baseFile.getPath(),
                getTabsFilterType(index)
        );

        setImage(picture.getPicture().getJLabel(), index);
    }

    private void buildMainMenu(){
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        menuItem = new JMenuItem(new AbstractAction("Load base image...") {
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
        menu.add(menuItem);

        menuItem = new JMenuItem(new AbstractAction("Load layer image...") {
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
        menu.add(menuItem);

        add(menuBar, BorderLayout.NORTH);
    }

    private void buildTabMenu(JPanel tab){
        //TODO doesn't work
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("Options");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        menuItem = new JMenuItem(new AbstractAction("Open...") {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                //chooser.setCurrentDirectory(getDefaultFile());
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    //loadAllImages(chooser.getSelectedFile());
                }
            }}
        );
        menu.add(menuItem);

        tab.add(menuBar, BorderLayout.SOUTH);
    }

    private File getDefaultDirectory() {
        return new File(FileSystems.getDefault().getPath("images").toString());
    }


}