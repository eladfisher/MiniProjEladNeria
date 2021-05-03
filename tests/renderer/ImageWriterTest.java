package renderer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void writeToImage() {
        ImageWriter iw = new ImageWriter("Pumba",10,16);
        iw.writeToImage();
    }
}