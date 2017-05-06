package hr.fer.zemris.java.raytracer;

import java.util.List;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * This class represents simple single-threaded implementation of ray-tracer for
 * rendering of 3D scenes.
 * 
 * @author Matteo Miloš
 *
 */
public class RayCaster {

	/** Allowed deviation when calculating with doubles */
	private static final double PRECISION = 1e-12;

	/** Default ambient values */
	private static final short DEFAULT_AMBIENT = 15;

	/** No color values */
	private static final short NO_COLOR = 0;

	/**
	 * Entry point to the program, creates scene with objects and shows them in
	 * GUI.
	 * 
	 * @param args
	 *            unused command line arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Class that creates a ray-tracer producer that is used for tracing rays in
	 * our 3D scene, it is used by a RayTracerViewer to show a scene in GUI.
	 * 
	 * @return implementation of {@link IRayTracerProducer} producer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Zapoèinjem izraèune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D viewUpNorm = viewUp.normalize();
				Point3D eyeView = view.sub(eye).normalize();

				Point3D yAxis = viewUpNorm.sub(eyeView.scalarMultiply(eyeView.scalarProduct(viewUpNorm))).normalize();
				Point3D xAxis = eyeView.vectorProduct(yAxis).normalize();

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();

				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x * horizontal / (width - 1.0)))
								.sub(yAxis.scalarMultiply(y * vertical / (height - 1.0)));

						Ray ray = Ray.fromPoints(eye, screenPoint);

						tracer(scene, ray, rgb);

						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

						offset++;
					}
				}
				System.out.println("Izraèuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}

	/**
	 * Method which traces the image with rays after finding the closest
	 * intersection of given ray with any object in the given scene
	 *
	 * 
	 * @param scene
	 *            the scene with lights and objects
	 * @param ray
	 *            the ray from view to screen
	 * @param rgb
	 *            the data describing intensity of color
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb) {

		RayIntersection closest = findClosestIntersection(scene, ray);

		if (closest == null) {
			setRgbArray(rgb, NO_COLOR);
		} else {
			setRgbArray(rgb, DEFAULT_AMBIENT);
			determineColorFor(closest, ray, scene, rgb);
		}

	}

	/**
	 * Method that sets rgb array values to the given value.
	 * 
	 * @param rgb
	 *            array
	 * @param value
	 *            given value
	 */
	private static void setRgbArray(short[] rgb, short value) {
		rgb[0] = value;
		rgb[1] = value;
		rgb[2] = value;
	}

	/**
	 * Determines color for part of image. Gets the diffusive and reflective
	 * parts after finding the closest intersection of ray created from light
	 * source to S for any object.
	 *
	 * @param scene
	 *            the scene with lights and objects
	 * @param ray
	 *            the ray used for getting intersections
	 * @param rgb
	 *            the data describing color
	 * @param intersection
	 *            the intersection with object
	 */
	private static void determineColorFor(RayIntersection intersection, Ray ray, Scene scene, short[] rgb) {
		List<LightSource> lightSource = scene.getLights();
		for (LightSource ls : lightSource) {

			Ray r = Ray.fromPoints(ls.getPoint(), intersection.getPoint());
			RayIntersection closestIntersection = findClosestIntersection(scene, r);

			if (closestIntersection != null) {
				double d1 = ls.getPoint().sub(intersection.getPoint()).norm();
				double d2 = ls.getPoint().sub(closestIntersection.getPoint()).norm();

				if (d2 >= d1 - PRECISION) {
					addReflectiveComponent(ls, ray, rgb, closestIntersection);
					addDiffuseComponent(ls, rgb, closestIntersection);
				}
			}
		}
	}

	/**
	 * Adds the diffuse component to displayed object
	 * 
	 * @param ls
	 *            light source in scene
	 * @param rgb
	 *            array with red green and blue components representing
	 *            intensity of each color
	 * @param closestIntersection
	 *            an intersection of ray with object
	 */
	private static void addDiffuseComponent(LightSource ls, short[] rgb, RayIntersection closestIntersection) {
		Point3D normal = closestIntersection.getNormal();
		Point3D light = ls.getPoint().sub(closestIntersection.getPoint()).normalize();

		double scalar = light.scalarProduct(normal);

		if (scalar > 0) {
			rgb[0] += (short) (ls.getR() * closestIntersection.getKdr() * scalar);
			rgb[1] += (short) (ls.getG() * closestIntersection.getKdg() * scalar);
			rgb[2] += (short) (ls.getB() * closestIntersection.getKdb() * scalar);
		}
	}

	/**
	 * Adds the reflective component to displayed object
	 * 
	 * @param ls
	 *            light source in scene
	 * @param ray
	 *            traced ray
	 * @param rgb
	 *            array with red green and blue components representing
	 *            intensity of each color
	 * @param closestIntersection
	 *            an intersection of ray with object
	 */
	private static void addReflectiveComponent(LightSource ls, Ray ray, short[] rgb,
			RayIntersection closestIntersection) {

		Point3D normal = closestIntersection.getNormal();
		Point3D light = ls.getPoint().sub(closestIntersection.getPoint()).normalize();

		Point3D r = normal.normalize().scalarMultiply(2 * light.scalarProduct(normal) / normal.norm()).sub(light)
				.normalize();
		Point3D v = ray.start.sub(closestIntersection.getPoint()).normalize();

		double scalar = r.scalarProduct(v);

		if (scalar >= 0) {
			double cos = Math.pow(scalar, closestIntersection.getKrn());
			rgb[0] += (short) (ls.getR() * closestIntersection.getKrr() * cos);
			rgb[1] += (short) (ls.getG() * closestIntersection.getKrg() * cos);
			rgb[2] += (short) (ls.getB() * closestIntersection.getKrb() * cos);
		}
	}

	/**
	 * Finds closest intersection between ray passed as argument and objects
	 * present in the scene.
	 * 
	 * @param scene
	 *            given scene
	 * @param ray
	 *            ray
	 * @return closest intersection between ray and any object in the scene, or
	 *         <tt>null</tt> if there is no such intersection
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection result = null;
		List<GraphicalObject> objects = scene.getObjects();

		for (GraphicalObject go : objects) {
			RayIntersection intersection = go.findClosestRayIntersection(ray);

			if (intersection == null) {
				continue;
			}

			if (result == null || intersection.getDistance() < result.getDistance()) {
				result = intersection;
			}
		}
		return result;
	}

}
