package hr.fer.zemris.java.raytracer.model;

/**
 * The Class Sphere is a representation of a GraphicalObject that can exist in a
 * scene shaped as a sphere.
 * 
 * @author Matteo Miloš
 */

public class Sphere extends GraphicalObject {

	/** The center. */
	private Point3D center;

	/** The radius. */
	private double radius;

	/** The diffusive red. */
	private double kdr;

	/** The diffusive green. */
	private double kdg;

	/** The diffusive blue. */
	private double kdb;

	/** The reflective red. */
	private double krr;

	/** The reflective green. */
	private double krg;

	/** The reflective blue. */
	private double krb;

	/** The exponent. */
	private double krn;

	/**
	 * Instantiates a new sphere.
	 *
	 * @param center
	 *            the center
	 * @param radius
	 *            the radius
	 * @param kdr
	 *            diffusive red
	 * @param kdg
	 *            the diffusive green
	 * @param kdb
	 *            diffusive blue
	 * @param krr
	 *            the reflective red.
	 * @param krg
	 *            the reflective green
	 * @param krb
	 *            the reflective blue
	 * @param krn
	 *            the Exponent
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D start = ray.start;
		double a = ray.direction.scalarProduct(ray.direction); /*-a = d*d*/
		double b = ray.direction.scalarMultiply(2).scalarProduct(start.sub(center)); /*- b = 2d(o-C)*/
		double c = center.scalarProduct(center) + start.scalarProduct(start) - 2 * start.scalarProduct(center)
				- radius * radius;

		double determinant = b * b - 4 * a * c;

		// If ray can not intersect then stop
		if (determinant < 0) {
			return null;
		}

		// Ray can intersect the sphere, solve the closer hitpoint
		double t = -0.5 * (b + Math.sqrt(determinant)) / a;

		if (t <= 0) {
			return null;
		}

		double distance = Math.sqrt(a) * t;
		Point3D hitPoint = start.add(ray.direction.scalarMultiply(t));
		boolean outer = hitPoint.sub(ray.start).norm() > radius;

		return new RayIntersection(hitPoint, distance, outer) {

			@Override
			public Point3D getNormal() {
				return getPoint().sub(center).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};

	}

}
