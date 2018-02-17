
package merger;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * @author Habbab
 * Purpose: Compose a BufferedImage with some needed operations like merging and scaling
 */
public class Photo {
    
    /**
     * merging types
     */
    public enum typeMerge{
        AND, OR, XOR
    }
    
    /**
     * acceptable formats
     */
    public static final String[] EXTENSIONS = new String[]{
         "bmp", "png", "jpg", "jpeg",  
    };
    
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            for (final String ext : EXTENSIONS){
                if (name.endsWith("." + ext))
                    return true;
            }
            return false;
        }
    };
    
    private final static int WIDTH_DISPLAY = 175;
    private final static int HEIGHT_DISPLAY = 200;
    private BufferedImage image;
    private  BufferedImage scaledImage;
    private String name;
    private File file;
    private static typeMerge mergeType;
    public Photo(File file) throws IOException {
        this.file = file;
        image = ImageIO.read(this.file);
        
        // scale the image to be displayed on the panel with other images
        scaledImage = scaleImage(WIDTH_DISPLAY, (int) (((double)WIDTH_DISPLAY / image.getWidth()) * image.getHeight()));
        name = this.file.getName();
        mergeType = typeMerge.AND;
    }

    public static typeMerge getMergeType() {
        return mergeType;
    }

    public static void setMergeType(typeMerge mergeType) {
        Photo.mergeType = mergeType;
    }
    
    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getScaledImage() {
        return scaledImage;
    }
    
    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }
    
    public static BufferedImage getScaledImage(BufferedImage par){
        return scaleImage(par, WIDTH_DISPLAY, (int) (((double)WIDTH_DISPLAY / par.getWidth()) * par.getHeight()));
    }
    
    /**
     * merge two images into one 
     * @param image1 First image
     * @param image2 Second image
     * @return Merged image 
     */
    public static BufferedImage merge(BufferedImage img1, BufferedImage img2) {
        int width = img1.getWidth();
        int heigh = img1.getHeight();
        img2 = Photo.scaleImage(img2, width, heigh);
        BufferedImage ret = new BufferedImage(width, heigh, BufferedImage.TYPE_3BYTE_BGR);
        for (int y = 0; y < heigh; y++) {
            for (int x = 0; x < width; x++) {

                if (x >= img1.getWidth() || y >= img1.getHeight()) { // set img2
                    if (x < ret.getWidth() && y < ret.getHeight() && x < img2.getWidth() && y < img2.getHeight()) {
                        ret.setRGB(x, y, img2.getRGB(x, y));
                    }
                } else if (x >= img2.getWidth() || y >= img2.getHeight()) {    //set img1
                    if (x < ret.getWidth() && y < ret.getHeight() && x < img2.getWidth() && y < img2.getHeight()) {
                        ret.setRGB(x, y, img1.getRGB(x, y));
                    }
                } else {   
                    if (mergeType == typeMerge.AND){
                        ret.setRGB(x, y,  Math.min(img1.getRGB(x, y), img2.getRGB(x, y)));
                    }
                    else if (mergeType == typeMerge.OR){
                        ret.setRGB(x, y,  Math.max(img1.getRGB(x, y), img2.getRGB(x, y)));
                    }
                    else{
                        ret.setRGB(x, y,  Math.abs(img1.getRGB(x, y) - img2.getRGB(x, y)) );
                    }
                }

            }
        }
        return ret;
    }
    
    /**
     * 
     * @param width The desired width
     * @param height The desired height
     * Scale current image
     */
    public BufferedImage scaleImage(int width, int height) {
        BufferedImage ret = new BufferedImage(width, height, this.image.getType());
        Graphics2D g = ret.createGraphics();
        g.drawImage(this.image, 0, 0, width, height, null);
        g.dispose();
        return ret;
    }
    /**
     * scale the passed image then return it
     * @param par
     * @param width
     * @param height
     * @return new scaled image 
     */
    public static BufferedImage scaleImage(BufferedImage par, int width, int height) {
        BufferedImage ret = new BufferedImage(width, height, par.getType());
        Graphics2D g = ret.createGraphics();
        g.drawImage(par, 0, 0, width, height, null);
        g.dispose();
        return ret;
    }
    
    /**
     * get all photos from directory
     * @param dir The directory to get photos from
     * @return list of photos
     * @throws IOException 
     */
    public static ArrayList<Photo> getPhotos(File dir) throws IOException{
        ArrayList<Photo> ret = new ArrayList<>();
        File[] files = dir.listFiles(IMAGE_FILTER);
        for (File file : files) {
            ret.add(new Photo(file));
        }
        return ret;
    }
}
