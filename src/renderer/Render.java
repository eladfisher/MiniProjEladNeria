package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.List;
import java.util.MissingResourceException;

/**
 * renderer class that gets a scene and ray tracing object and the camera and render a image and write this to a file
 */
public class Render {
	ImageWriter _imageWriter;
	
	Camera _camera;
	RayTracerBase _rayTracerBase;
	
	
	public Render setScene(Scene scene) {
		
		return this;
	}
	
	public Render setCamera(Camera camera) {
		_camera = camera;
		return this;
	}
	
	public Render setRayTracer(RayTracerBase rayTracerBase) {
		_rayTracerBase = rayTracerBase;
		
		return this;
	}
	
	public Render setImageWriter(ImageWriter imageWriter) {
		_imageWriter = imageWriter;
		
		return this;
	}
	
	/**
	 * for now the method checks that all the fields are initialized
	 */
	public void renderImage() {
		if (_imageWriter == null || _camera == null || _rayTracerBase == null)
			throw new MissingResourceException("one of the fields isn't proper initialized", "Render", "0");
		
		
		int nX = _imageWriter.getNx();
		int nY = _imageWriter.getNy();
		
		for (int i = 0; i < nX; ++i)
			for (int j = 0; j < nY; ++j)
			{
				
				Color color = getAverageColor(_camera.constructRaysThroughPixel(nX, nY, i, j));
				_imageWriter.writePixel(i, j, color);
				
				
			}
	}
	
	/**
	 * write the calculated image to an actual file
	 */
	public void writeToImage() {
		if (_imageWriter != null)
		{
			_imageWriter.writeToImage();
		}
		
		else
		{
			throw new MissingResourceException("image writer is not proper initialized", "Render", "0");
		}
	}
	
	/**
	 * print the grid into the image buffer
	 *
	 * @param interval the interval in pixels, the number of pixels between 2 parallel lines of the grid
	 * @param color    the color of the grid
	 */
	public void printGrid(int interval, Color color) {
		
		//print the vertical lines of the grid
		for (int x = 0; x < _imageWriter.getNx(); x += interval)
			for (int y = 0; y < _imageWriter.getNy(); ++y)
				_imageWriter.writePixel(x, y, color);
		
		//print the horizontal lines of the grid
		for (int y = 0; y < _imageWriter.getNy(); y += interval)
			for (int x = 0; x < _imageWriter.getNx(); ++x)
				_imageWriter.writePixel(x, y, color);
		
	}
	
	/**
	 * TODO
	 * @param rays
	 * @return
	 */
	private Color getAverageColor(List<Ray> rays) {
		Color c = Color.BLACK;
		
		//TODO
		for (Ray r : rays) {
			c = c.add(_rayTracerBase.traceRay(r));
		}
		
		
		return c.reduce(rays.size());
	}
	
	
}
