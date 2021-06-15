package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ImageWriter
 */
class ImageWriterTest {

    @Test
    void writeToImage() {
        ImageWriter iw = new ImageWriter("Pumba",800,500);
        for (int x = 0; x < iw.getNx(); ++x)
        {
            for (int y = 0; y < iw.getNy(); ++y)
            {
                if ((x%50 == 0)||(y%50 == 0))
                    iw.writePixel(x,y,new Color(java.awt.Color.GREEN));
                else
                    iw.writePixel(x,y,new Color(java.awt.Color.BLUE));
            }
        }
        iw.writeToImage();
    }
}