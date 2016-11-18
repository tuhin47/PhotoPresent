
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TUHIN
 */
public class SavePhoto {
    
    
    public static void main(String[] args){
    
        try {
            SavePhoto.saveIMG("CSE", "2016", "tada");
        } catch (IOException ex) {
            Logger.getLogger(SavePhoto.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public static String saveIMG(String course, String reg,String date) throws IOException{
        
//        BufferedImage img= ImageIO.read(new File(photo_url));
        String url="photo\\"+course+"\\"+reg;
         Path path = Paths.get(url);
        //if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                //fail to create directory
                e.printStackTrace();
            }
        }
        
        url+= "\\"+date+".jpg";       
//         File f = new File(url);
//        try {
//            ImageIO.write(img, "JPG", f);
//        } catch (IOException ex) {
//            Logger.getLogger(SavePhoto.class.getName()).log(Level.SEVERE, null, ex);
//        }
        System.out.println(url);
        return url;
    
    
    }
    
}
