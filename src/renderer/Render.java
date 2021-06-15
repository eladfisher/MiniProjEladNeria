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
	
	/**
	 * the image writer of this renderer
	 */
	ImageWriter _imageWriter;
	
	/**
	 * the camera to render the scene with
	 */
	Camera _camera;
	
	/**
	 * the ray tracer that we use to render this scene
	 */
	RayTracerBase _rayTracerBase;
	
	//region Setters & Getters
	/**
	 * setter for scene
	 * @param scene the Scene that contains every thing end wo want to render it.
	 * @return this
	 */
	public Render setScene(Scene scene) {
		return this;
	}

	/**
	 * setter for Camera.
	 * @param camera the camera that through it we see the picture
	 * @return this
	 */
	public Render setCamera(Camera camera) {
		_camera = camera;
		return this;
	}

	/**
	 * setter for the ray tracer, because there is some kinds of ray tracer, and we want to get which one to use.
	 * @param rayTracerBase the ray tracer.
	 * @return this
	 */
	public Render setRayTracer(RayTracerBase rayTracerBase) {
		_rayTracerBase = rayTracerBase;
		
		return this;
	}

	/**
	 * setter for image writer
	 * @param imageWriter the image writer for write the image to it
	 * @return this
	 */
	public Render setImageWriter(ImageWriter imageWriter) {
		_imageWriter = imageWriter;
		
		return this;
	}
	//endregion

	//region actions
	/**
	 * helper function that calculate the average color that the rays return.
	 * @param rays list of rays that the func trace and check the color of each ray and calculate the average
	 * @return the average color
	 */
	private Color getAverageColor(List<Ray> rays) {
		Color c = Color.BLACK;

		// for each ray in rays we check the color and add to c.
		for (Ray r : rays) {
			c = c.add(_rayTracerBase.traceRay(r));
		}

		return c.reduce(rays.size());
	}

	/**
	 * for now the method checks that all the fields are initialized
	 */
	public void renderImage() {
		if (_imageWriter == null || _camera == null || _rayTracerBase == null)
			throw new MissingResourceException("one of the fields isn't proper initialized", "Render", "0");
		
		
		int nX = _imageWriter.getNx();
		int nY = _imageWriter.getNy();


		/* for each pixel in the image we need to get is color,
		 * so we going over each pixel and trace the rays we need to trace (according to what we need for DoF and SS),
		 * and then we calculate (in another function) the color that each ray return,
		 * and calculate the average color for the pixel.
		 */
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
	//endregion
}
