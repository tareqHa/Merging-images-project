

package merger;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Habbab
 * This class handles the GUI and events triggers
 */



public class Window extends javax.swing.JFrame implements MouseListener, WindowListener{

    private ArrayList<File> directories;
    private ArrayList<ArrayList<BufferedImage>> toSave;
    private ArrayList<ArrayList<Photo>> images;
    private JPanel panel;   //  the main panel that contains directories and images
    private boolean isDirectory;    // are directories displayed or images ?
    private static Dimension screenDimension;   // used to resize main window
    // store selected directories for the operations (merge, dispose, save)
    private ArrayList<Component> selectedDirectories;

    private int selectedToSave = -1;
    // to group radio buttons
    private ButtonGroup bg;
    private ButtonGroup bg2;
    
    private String saveFormat;
    
    // GUI variables
    private JRadioButton AND;
    private JButton Dispose;
    private JButton Merge;
    private JRadioButton OR;
    private JButton Save;
    private JRadioButton XOR;
    private JRadioButton bmp;
    private JScrollPane jScrollPane1;
    private JRadioButton jpeg;
    private JRadioButton jpg;
    private JRadioButton png;
    private JButton selectDirectory;
    private JButton showDirectories;
    
    
    /**
     * Creates new form Window
     */
    public Window() {
        directories = new ArrayList<>();
        images = new ArrayList<>();
        isDirectory = true;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        screenDimension = new Dimension((int) (0.5 * screen.getWidth()), (int) (0.5 * screen.getHeight()));
        selectedDirectories = new ArrayList<>();
        toSave = new ArrayList<>();
        bg = new ButtonGroup();
        bg2 = new ButtonGroup();
        saveFormat = "png";
        initComponents();
    }

    /**
     * This is to initialize the form
     */                      
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        selectDirectory = new javax.swing.JButton();
        showDirectories = new javax.swing.JButton();
        Merge = new javax.swing.JButton();
        Save = new javax.swing.JButton();
        AND = new javax.swing.JRadioButton();
        OR = new javax.swing.JRadioButton();
        XOR = new javax.swing.JRadioButton();
        Dispose = new javax.swing.JButton();
        png = new javax.swing.JRadioButton();
        jpg = new javax.swing.JRadioButton();
        jpeg = new javax.swing.JRadioButton();
        bmp = new javax.swing.JRadioButton();



        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 600));
        setResizable(false);

        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(750, 600));

        panel = new JPanel();
        panel.setPreferredSize(new java.awt.Dimension(750, 600));
        panel.setLayout(new MigLayout("wrap 2"));   // 4 columns

        jScrollPane1.setViewportView(panel);

        selectDirectory.setText("Select Directory");
        selectDirectory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectDirectoryActionPerformed(evt);
            }
        });

        showDirectories.setText("Show Directories");
        showDirectories.setMaximumSize(new java.awt.Dimension(109, 23));
        showDirectories.setPreferredSize(new java.awt.Dimension(109, 23));
        showDirectories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showDirectoriesActionPerformed(evt);
            }
        });

        Merge.setText("Merge");
        Merge.setMaximumSize(new java.awt.Dimension(109, 23));
        Merge.setMinimumSize(new java.awt.Dimension(109, 23));
        Merge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MergeActionPerformed(evt);
            }
        });

        Save.setText("Save");
        Save.setMaximumSize(new java.awt.Dimension(109, 23));
        Save.setMinimumSize(new java.awt.Dimension(109, 23));
        Save.setPreferredSize(new java.awt.Dimension(109, 23));
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        AND.setSelected(true);
        bg.add(AND);
        AND.setText("AND");
        AND.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ANDActionPerformed(evt);
            }
        });

        bg.add(OR);
        OR.setText("OR");
        OR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ORActionPerformed(evt);
            }
        });

        bg.add(XOR);
        XOR.setText("XOR");
        XOR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XORActionPerformed(evt);
            }
        });

        Dispose.setText("Dispose");
        Dispose.setMaximumSize(new java.awt.Dimension(109, 23));
        Dispose.setPreferredSize(new java.awt.Dimension(109, 23));
        Dispose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DisposeActionPerformed(evt);
            }
        });

        bg2.add(png);
        png.setSelected(true);
        png.setText("PNG");
        png.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pngActionPerformed(evt);
            }
        });

        bg2.add(jpg);
        jpg.setText("JPG");
        jpg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpgActionPerformed(evt);
            }
        });

        bg2.add(jpeg);
        jpeg.setText("JPEG");
        jpeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpegActionPerformed(evt);
            }
        });

        bg2.add(bmp);
        bmp.setText("BMP");
        bmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bmpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(showDirectories, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(selectDirectory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Merge, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Save, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Dispose, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(AND)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(OR)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(XOR)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jpeg)
                            .addComponent(png))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bmp)
                            .addComponent(jpg))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(selectDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(showDirectories, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Merge, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AND)
                    .addComponent(OR)
                    .addComponent(XOR))
                .addGap(21, 21, 21)
                .addComponent(Save, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(png)
                    .addComponent(jpg))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bmp)
                    .addComponent(jpeg))
                .addGap(50, 50, 50)
                .addComponent(Dispose, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        pack();
    }                    

    /**
     * Action performed on clicking Select Folder button
     * select directory from the computer
     * @param evt Event
     */
    private void selectDirectoryActionPerformed(java.awt.event.ActionEvent evt) {                                                
        
        JFileChooser chooser;
        chooser = new JFileChooser();
        chooser.setDialogTitle("Choose a directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        try {
            // get currentDirectory and check if it has only images
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                if (true) { //Fts.checkDirectory(chooser.getSelectedFile())
                    directories.add(chooser.getSelectedFile());
                    images.add(Photo.getPhotos(chooser.getSelectedFile()));

                    // draw new directory instantly
                    if (isDirectory) {
                        showDirectoriesActionPerformed(evt);
                    }
                } else {    // print directory does not contain only images
                    System.out.println("flase");
                }
            } else {
                System.out.println("No Selection ");
            }
        } catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                               

    /**
     * Display directories
     *
     * @param evt
     */
    private void showDirectoriesActionPerformed(java.awt.event.ActionEvent evt) {                                                
        selectedDirectories.clear();

        isDirectory = true;
        panel.removeAll();  // clear panel in order to show directories
        // show directories
        for (int i = 0; i < directories.size(); i++) {
            File directory = directories.get(i);
            JPanel insidePanel = new JPanel(new MigLayout("wrap 1"));   // the square of icon with folder's name

            // give a name [i] to insidePanel to know which directory is clicked and display it's images
            insidePanel.setName(Integer.toString(i));
            JLabel label1 = new JLabel(new ImageIcon(getClass().getResource("/icon.png")));
            JLabel label2 = new JLabel(directory.getName(), SwingUtilities.CENTER);

            // change font size of label2
            label2.setFont(new Font(label2.getFont().getName(), Font.PLAIN, label2.getFont().getSize() * 2));

            // put border around insidepanel
            insidePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            // resize label2 
            Dimension dd = label2.getPreferredSize();
            dd.setSize(dd.getWidth() * 2, dd.getHeight());
            label2.setPreferredSize(dd);

            // add image and name to the insidePanel
            insidePanel.add(label1);
            insidePanel.add(label2);
            // to open the folder
            insidePanel.addMouseListener(this);
            panel.add(insidePanel);

        }

        // show UnSaved directories
        for (int i = 0; i < toSave.size(); i++) {
            JPanel insidePanel = new JPanel(new MigLayout("wrap 1"));   // the square of icon with folder's name

            // give a name [i] to insidePanel to know which directory is clicked and display it's images
            insidePanel.setName("UnSaved" + Integer.toString(i));
            JLabel label1 = new JLabel(new ImageIcon(getClass().getResource("/icon.png")));
 //           JLabel label1 = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("/icon.png")));
            JLabel label2 = new JLabel("UnSaved", SwingUtilities.CENTER);

            // change font size of label2
            label2.setFont(new Font(label2.getFont().getName(), Font.PLAIN, label2.getFont().getSize() * 2));

            // put border around insidepanel
            insidePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            // resize label2 
            Dimension dd = label2.getPreferredSize();
            dd.setSize(dd.getWidth() * 2, dd.getHeight());
            label2.setPreferredSize(dd);

            // add image and name to the insidePanel
            insidePanel.add(label1);
            insidePanel.add(label2);
            // to open the folder
            insidePanel.addMouseListener(this);
            panel.add(insidePanel);
        }
        // update JFrame instantly
        SwingUtilities.updateComponentTreeUI(this);
    }                                               

    /**
     * Merge selected directories
     * @param evt 
     */
    private void MergeActionPerformed(java.awt.event.ActionEvent evt) {                                      
        // TODO add your handling code here:

        // check if user marked UnSaved directory
        boolean can = true;
        for (Component selectedDirectory : selectedDirectories) {
            if (selectedDirectory.getName().startsWith("UnSaved")){
                can = false;
                break;
            }
        }
        if (selectedDirectories.size() <= 1) {
            JOptionPane.showMessageDialog(this, "Select two or more directories");
        } // trying to merge unSaved directory
        else if (!can) {
            JOptionPane.showMessageDialog(this, "Do not choose unSaved directories, you can save them first");
        } else {   // merge
            // get list of bufferedImages
            ArrayList<BufferedImage> toAdd = new ArrayList<>();
            int sz = Integer.MAX_VALUE;

            for (Component i : selectedDirectories) {
               
                sz = Math.min(sz, images.get(Integer.parseInt(i.getName())).size());
            }
            for (int i = 0; i < sz; i++) {
                BufferedImage now = images.get(Integer.parseInt(selectedDirectories.get(0).getName())).get(i).getImage();
                for (int j = 0; j < selectedDirectories.size() - 1; j++) {
                    now = Photo.merge(now, images.get(Integer.parseInt(selectedDirectories.get(j + 1).getName())).get(i).getImage());
                }
                toAdd.add(now);
            }
            // add the list to toSave
            toSave.add(toAdd);
            showDirectoriesActionPerformed(evt);
        }
    }                                     

    /**
     * Save selected directory
     * @param evt 
     */
    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {                                     
        
        if (selectedDirectories.size() > 1) {
            JOptionPane.showMessageDialog(this, "Choose one directory to save");
        } else if (selectedDirectories.size() == 0) {
            JOptionPane.showMessageDialog(this, "Choose unSaved directory first");
        } else if (!selectedDirectories.get(0).getName().startsWith("UnSaved")) {
            JOptionPane.showMessageDialog(this, "Choose unSaved directory to save");
        } else {
            JFileChooser chooser;
            chooser = new JFileChooser();
            chooser.setDialogTitle("Choose a directory");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setApproveButtonText("Save");
           
            //
            // disable the "All files" option.
            //
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                // save images
                for (int i = 0; i < toSave.get(selectedToSave).size(); ++i) {
                    try {
                        // the file where current image to save, its parent is chooser directory
                        File file = new File(chooser.getSelectedFile(), "image" + Integer.toString(i) + "." + saveFormat);
                        if (!file.exists()) {
                            file.createNewFile();
                        }

                        ImageIO.write(toSave.get(selectedToSave).get(i), saveFormat, file);
                    } catch (IOException ex) {
                        Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                directories.add(chooser.getSelectedFile());
                try {
                    images.add(Photo.getPhotos(chooser.getSelectedFile()));
                } catch (IOException ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }

                // remove from toSave and reset selectedToSave
                toSave.remove(selectedToSave);
                selectedToSave = -1;
                // draw new directory instantly
                if (isDirectory) {
                    showDirectoriesActionPerformed(evt);
                }
            } 
    }                                    

    }
    private void XORActionPerformed(java.awt.event.ActionEvent evt) {                                    
        
        Photo.setMergeType(Photo.typeMerge.XOR);
    }                                   

    private void ORActionPerformed(java.awt.event.ActionEvent evt) {                                   
        
        Photo.setMergeType(Photo.typeMerge.OR);
    }                                  

    private void ANDActionPerformed(java.awt.event.ActionEvent evt) {                                    
        
        Photo.setMergeType(Photo.typeMerge.AND);
    }                                   

    /**
     * Dispose selected directories
     * @param evt 
     */
    private void DisposeActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
        
        
        ArrayList<Integer> save = new ArrayList<>();
        ArrayList<Integer> unSave = new ArrayList<>();
        for (int i = 0; i < selectedDirectories.size() ; ++i){
            if (selectedDirectories.get(i).getName().startsWith("UnSaved")){ // UnSaved
                int idx = Integer.parseInt(selectedDirectories.get(i).getName().substring(7));
                unSave.add(idx);
            }
            else{   // saved
                int idx = Integer.parseInt(selectedDirectories.get(i).getName());
                save.add(idx);
            }
        }
        
        // the indexes should be pairwise
        Collections.sort(save);
        Collections.sort(unSave);
        
        for (int i = 0 ; i < unSave.size() ; ++i){            
            toSave.remove(unSave.get(i).intValue() - i);
        }
        
        for (int i = 0 ; i < save.size() ; ++i){                        
            images.remove(save.get(i).intValue() - i);
            directories.remove(save.get(i).intValue() - i);
        }
        
        // refresh the screen directories
        showDirectoriesActionPerformed(evt);
    }                                       

    private void pngActionPerformed(java.awt.event.ActionEvent evt) {                                    
        saveFormat = "png";
    }                                   

    private void jpgActionPerformed(java.awt.event.ActionEvent evt) {                                    
        saveFormat = "jpg";
    }                                   

    private void jpegActionPerformed(java.awt.event.ActionEvent evt) {                                     
        saveFormat = "jpeg";
    }                                    

    private void bmpActionPerformed(java.awt.event.ActionEvent evt) {                                    
        saveFormat = "bmp";
    }                                   

    /**
     * Display images of a directory
     * @param name gives images[idx] or toSave[idx]
     */
    public void displayImages(String name) {

        panel.removeAll();
        if (!name.startsWith("UnSaved")) {
            int idx = Integer.parseInt(name);
            for (int i = 0; i < images.get(idx).size(); ++i) {
                Photo photo = images.get(idx).get(i);
                // Jlabel for displaying the image
                JLabel now = new JLabel(new ImageIcon(photo.getScaledImage()));
                // nice size
                now.setPreferredSize(new Dimension(175, 200));
                // name the photo panel for getting it in mouse click action
                now.setName(Integer.toString(idx) + "-" + Integer.toString(i));
                // display the image widely after clicking on it
                now.addMouseListener(this);
                panel.add(now);
            }
            panel.setPreferredSize(new Dimension(750, images.get(idx).size() * 100));
            panel.revalidate();
            panel.repaint();
            SwingUtilities.updateComponentTreeUI(this);
        } else {
            int idx = Integer.parseInt(name.substring(7));  // 7 = "UnSaved".length()
            for (int i = 0; i < toSave.get(idx).size(); ++i) {

                // Jlabel for displaying the image
                JLabel now = new JLabel(new ImageIcon(Photo.getScaledImage(toSave.get(idx).get(i))));
                // nice size
                now.setPreferredSize(new Dimension(175, 200));
                // name the photo panel for getting it in mouse click action
                now.setName("U" + Integer.toString(idx) + "-" + Integer.toString(i));
                // display the image widely after clicking on it
                now.addMouseListener(this);
                panel.add(now);
            }
            panel.setPreferredSize(new Dimension(750, images.get(idx).size() * 100));
            panel.revalidate();
            panel.repaint();
            SwingUtilities.updateComponentTreeUI(this);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Window window = new Window();
                window.setVisible(true);
                window.setSize(screenDimension);
                window.addWindowListener(window);
                window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            }
        });

    }

    // Variables declaration - do not modify                     
    
    // End of variables declaration                   

    @Override
    public void mouseClicked(MouseEvent e) {

        // double click
        // here we either open a folder, or display an image widely
        if (e.getClickCount() == 2) {
            // display selected directory images
            if (isDirectory) {
                isDirectory = !isDirectory;
                displayImages(e.getComponent().getName());
            } // display an image widely
            else {

                String[] parts = e.getComponent().getName().split("-");
                BufferedImage show;
                if (parts[0].charAt(0) == 'U') {
                    int idx = Integer.parseInt(parts[0].substring(1));
                    int i = Integer.parseInt(parts[1]);
                    show = toSave.get(idx).get(i);
                } else {
                    int idx = Integer.parseInt(parts[0]);
                    int i = Integer.parseInt(parts[1]);
                    show = images.get(idx).get(i).getImage();
                }
                JFrame window = new JFrame();
                JPanel panel = new JPanel();

                // if width is much bigger than screen width, then scale it to 90% of screen width
                int widthDesired = (int)(screenDimension.getWidth() * 1.5);
                int heightDesired = (int)(screenDimension.getHeight()* 1.5);
                if (show.getWidth() > widthDesired) {
                    
                    show = Photo.scaleImage(show, widthDesired, (int) (widthDesired * show.getHeight() / show.getWidth()));
                }

                // if height is much bigger than screen height, then scale it to fit the screen
                if (show.getHeight() > heightDesired) {
                    show = Photo.scaleImage(show, (int) (heightDesired * show.getWidth() / show.getHeight()), (int) heightDesired);
                }

                JLabel jlabel = new JLabel(new ImageIcon(show));
                panel.add(jlabel);
                window.add(panel);
                window.pack();
                window.setVisible(true);
            }
        } // clicking on folder will hilight it (mark selected)
        else if (e.getClickCount() == 1 && isDirectory) {
            if (e.getComponent().getName().startsWith("UnSaved")) {
                //  JOptionPane.showMessageDialog(this, "Dont choose unSaved folders ");
                JPanel now = (JPanel) e.getComponent();
                // unSelect 
                if (((LineBorder) now.getBorder()).getLineColor() == Color.CYAN) {
                    selectedDirectories.remove(e.getComponent());
                    now.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                } // select 
                else {
                    selectedDirectories.add(e.getComponent());
                    now.setBorder(BorderFactory.createLineBorder(Color.CYAN));
                    selectedToSave = Integer.parseInt(e.getComponent().getName().substring(7));
                }
            } else {
                JPanel now = (JPanel) e.getComponent();
                // select
                if (!selectedDirectories.contains(e.getComponent())) {
                    now.setBorder(BorderFactory.createLineBorder(Color.CYAN));
                    selectedDirectories.add(e.getComponent());
                } // unSelect
                else {
                    now.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    selectedDirectories.remove(e.getComponent());
                }
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

 
    
    @Override
    public void windowClosed(WindowEvent we) {
    }

    @Override
    public void windowOpened(WindowEvent we) {
    }

    @Override
    public void windowClosing(WindowEvent we) {
        // check if there is folders unsaved left
        if (toSave.size() > 0){
            int option = JOptionPane.showConfirmDialog(this, "There are unSaved directories, are you sure ?");
            if (option == JOptionPane.YES_OPTION){
                this.dispose();
            }
        }
        else
            this.dispose();
    }

    @Override
    public void windowIconified(WindowEvent we) {
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
    }

    @Override
    public void windowActivated(WindowEvent we) {
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        
    }

   
}
