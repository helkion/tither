/**
 * 
 */
package nz.hmp.tither.utils;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

//import org.jnbis.api.Jnbis;

/**
 * @author helcio
 *
 */
public class ImageUtils {
	
	public static ByteArrayInputStream getInputStream(
			Object obj){
        byte[] bytes;
        if (obj != null){
            if (obj instanceof String){
                String str = (String) obj;
                if (!str.trim().isEmpty()){
                    bytes = str.getBytes();
                    obj = Base64
							.getDecoder()
							.decode(bytes);
                } else {
                    obj = null;
                }
            }
        }
       
        bytes = (byte[]) obj;
        return bytes != null?
                new ByteArrayInputStream(bytes)
                : null;
	}
	
//	public static byte[] getImageBytes(String imagem){
//		byte[] bytes = null;
//		if (imagem != null){
//				
//			if (!imagem.equalsIgnoreCase("null")){
//				bytes = Base64
//							.getDecoder()
//							.decode(imagem.getBytes());
//				bytes = Jnbis
//						.wsq()
//						.decode(bytes)
//						.toJpg()
//						.asByteArray();
//			}
//		}
//			return bytes;
//			
//	}
	
	public static ByteArrayInputStream rotateImageLeft(
			ByteArrayInputStream assinatura)
					throws IOException{
       
        BufferedImage image = ImageIO.read(assinatura);
        Iterator<ImageReader> iterator = ImageIO
        		.getImageReaders(assinatura);
        ImageReader reader = iterator.next();
        String format = reader.getFormatName();
               
        int width = image.getWidth();
        int height = image.getHeight();
       
        BufferedImage rotated = new BufferedImage(
        		width, height, image.getType());
       
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rotated.setRGB(y, (width - 1) - x, image.getRGB(x, y));
            }
        }
       
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(rotated, format, baos);
        byte[] bytes = baos.toByteArray();
        assinatura = new ByteArrayInputStream(bytes);
        
        return assinatura;
       
    }
	
	
    public static ByteArrayInputStream rotateImage(
    		ByteArrayInputStream assinatura) 
    				throws Exception {
    	if(assinatura!= null) {
    		
	        BufferedImage source = ImageIO.read(assinatura);
	        
	        Iterator<ImageReader> iterator = ImageIO
	        		.getImageReadersByFormatName("jpg");
	        ImageReader reader = iterator.next();
	        String format = reader.getFormatName();
	        
	        int width = source.getWidth();
	        int height = source.getHeight();
	         
	        BufferedImage output = new BufferedImage(
	        		height, width, source.getType());
	            
	        AffineTransformOp op = new AffineTransformOp(
	        		rotateCounterClockwise90(source), 
	        		AffineTransformOp.TYPE_BILINEAR);
	        op.filter(source, output);
	 
	       
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(output, format, baos);
	        byte[] bytes = baos.toByteArray();
	        assinatura = new ByteArrayInputStream(bytes);
        
    	}
        return assinatura;
    }
    
    
    // Rotates counter clockwise 90 degrees. Uses rotation on 
    // center and then translating it to origin
    private static AffineTransform rotateCounterClockwise90(
    		BufferedImage source) {
        AffineTransform transform = new AffineTransform();
        transform.rotate(
        		-Math.PI/2, 
        		source.getWidth()/2, 
        		source.getHeight()/2);
        double offset = (source.getWidth()-source.getHeight())/2;
        transform.translate(-offset,-offset);
        return transform;
    }

}
