package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * This class represents Newton-Rhapson iteration used to extended to complex
 * functions and to systems of equations.
 * 
 * In numerical analysis, Newton's method (also known as the Newton�Raphson
 * method), named after Isaac Newton and Joseph Raphson, is a method for finding
 * successively better approximations to the roots (or zeroes) of a real-valued
 * function.
 * 
 * <pre>
 *  x : f(x) = 0
 * </pre>
 * 
 * The Newton�Raphson method in one variable is implemented as follows:
 * 
 * The method starts with a function f defined over the real numbers x, the
 * function's derivative f', and an initial guess x0 for a root of the function
 * f. If the function satisfies the assumptions made in the derivation of the
 * formula and the initial guess is close, then a better approximation x1 is
 * 
 * <pre>
 *  x<sub>1</sub> = x<sub>0</sub> - (f(x<sub>0</sub>)/f'(x<sub>0</sub>))
 * </pre>
 * 
 * Geometrically, (x<sub>1</sub>, 0) is the intersection of the x-axis and the
 * tangent of the graph of f at (x<sub>0</sub>, f(x<sub>0</sub>)).
 * 
 * The process is repeated as
 * 
 * <pre>
 * x<sub>n+1</sub> = x<sub>n</sub> - f(x<sub>n</sub>)/f'(x<sub>n</sub>)
 * </pre>
 * 
 * until a sufficiently accurate value is reached.
 * 
 * This algorithm is first in the class of Householder's methods, succeeded by
 * Halley's method. The method can also be extended to complex functions and to
 * systems of equations.
 * 
 * @author Matteo Miloš
 *
 */
public class Newton {

	/**
	 * Pattern used for recognition of complex numbers.
	 */
	private static final Pattern COMPLEX_NUMBER = Pattern.compile("(-?.+)([+|-]i.*)");

	/**
	 * The main method at the start of the program.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		int i = 1;
		List<Complex> rootList = new ArrayList<>();

		while (true) {
			System.out.printf("Root %d>", i++);

			String line = sc.nextLine().trim();
			if (line.toLowerCase().equals("done")) {
				break;
			}
			try {
				line = line.replaceAll("\\s", "");
				Complex root = getNextRoot(line, COMPLEX_NUMBER);
				rootList.add(root);
			} catch (NumberFormatException exc) {
				System.out.println("Invalid root given.");
			}

		}
		sc.close();

		System.out.println("Image of fractal will appear shortly. Thank you.");

		ComplexRootedPolynomial roots = new ComplexRootedPolynomial(rootList.toArray(new Complex[rootList.size()]));

		FractalViewer.show(new FractalProducerImpl(roots));
	}

	/**
	 * Private method used for calculating imaginary part of complex number
	 * 
	 * @param string
	 *            complex number in form of string
	 * @return imaginary part
	 * @throws NumberFormatException
	 *             if complex number is invalid
	 */
	private static double getImaginary(String string) throws NumberFormatException {

		string = string.replace("i", "");
		double imaginary;

		if (string.equals("") || string.equals("+")) {
			imaginary = 1;
		} else if (string.equals("-")) {
			imaginary = -1;
		} else {
			imaginary = Double.parseDouble(string);
		}

		return imaginary;
	}

	/**
	 * Private method used for recognizing complex number from given string
	 * 
	 * @param line
	 *            string representation of complex number
	 * @param pattern
	 *            pattern used for recognizing
	 * @return recognized complex number
	 */
	private static Complex getNextRoot(String line, Pattern pattern) {
		Matcher normal = pattern.matcher(line);

		double real = 0;
		double imaginary = 0;

		if (normal.matches()) {
			real = Double.parseDouble(normal.group(1));
			line = normal.group(2).replace("+", "");
			imaginary = getImaginary(line);
		} else if (line.contains("i")) {
			imaginary = getImaginary(line);
		} else {
			real = Double.parseDouble(line);
		}
		return new Complex(real, imaginary);
	}

	/**
	 * The Class FractalProducerImpl is a representation of IFractalProducer
	 * which is used to produce fractals using Newton - Raphson iteration.
	 */
	public static class FractalProducerImpl implements IFractalProducer {

		/** The roots. */
		private ComplexRootedPolynomial rootedPolynom;

		/** The polynomial. */
		private ComplexPolynomial polynom;

		/**
		 * Instantiates a new instance of fractal producer.
		 *
		 * @param rootedPolynom
		 *            the roots
		 */
		public FractalProducerImpl(ComplexRootedPolynomial rootedPolynom) {
			if (rootedPolynom == null) {
				throw new IllegalArgumentException("Given roots can't be null");
			}
			this.rootedPolynom = rootedPolynom;

			this.polynom = rootedPolynom.toComplexPolynom();

		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {

			short[] data = new short[width * height];
			final int rangeOfYs = 8 * Runtime.getRuntime().availableProcessors();
			final int numOfYPerTrack = height / rangeOfYs;

			ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), (p) -> {
				Thread t = new Thread(p);
				t.setDaemon(true);
				return t;
			});

			List<Future<Void>> results = new ArrayList<>();

			for (int i = 0; i < rangeOfYs; i++) {
				int yMin = i * numOfYPerTrack;
				int yMax = (i + 1) * numOfYPerTrack - 1;
				if (i == rangeOfYs - 1) {
					yMax = height - 1;
				}

				CalculateJob job = new CalculateJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data,
						polynom, rootedPolynom, yMin * width);

				results.add(pool.submit(job));
			}
			for (Future<Void> job : results) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException ignorable) {

				}
			}
			pool.shutdown();
			observer.acceptResult(data, (short) (polynom.order() + 1), requestNo);

		}

		/**
		 * The CalculateJob Job is an implementation of {@link Callable} which
		 * is used to determine color pixels for specific heights.
		 */
		public static class CalculateJob implements Callable<Void> {

			/** The Constant convergence threshold. */
			private static final double CONVERGENCE_TRESHOLD = 1E-3;

			/** Maximum number of iterations */
			private static final int MAX_ITER = 16 * 16;

			/** The Constant root distance. */
			private static final double ROOT_TRESHOLD = 2E-3;

			/** The real minimum part. */
			private double reMin;

			/** The real maximum part. */
			private double reMax;

			/** The imaginary minimum part. */
			private double imMin;

			/** The imaginary maximum of complex number. */
			private double imMax;

			/** The width. */
			private int width;

			/** The height. */
			private int height;

			/** starting line of raster to generate */
			private int yMin;

			/** ending line of raster to generate */
			private int yMax;

			/** array containing color indexes */
			private short[] data;

			/** The polynom. */
			private ComplexPolynomial polynom;

			/** The roots of polynom. */
			private ComplexRootedPolynomial rootedPolynom;

			/** The offset . */
			private int offset;

			/**
			 * Creates a new iterative computation job.
			 * 
			 * @param reMin
			 *            minimum real part of complex number
			 * @param reMax
			 *            maximum real part of complex number
			 * @param imMin
			 *            minimum imaginary part of complex number
			 * @param imMax
			 *            maximum imaginary part of complex number
			 * @param width
			 *            width of raster
			 * @param height
			 *            height of raster
			 * @param yMin
			 *            starting line of raster to generate
			 * @param yMax
			 *            ending line of raster to generate
			 * @param data
			 *            data of color indexes
			 * @param rootedPolynom
			 *            entered complex polynom roots
			 * @param polynom
			 *            complex polynomial with provided roots
			 * @param offset
			 *            the offset
			 */
			public CalculateJob(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
					int yMax, short[] data, ComplexPolynomial polynom, ComplexRootedPolynomial rootedPolynom,
					int offset) {
				this.reMin = reMin;
				this.reMax = reMax;
				this.imMin = imMin;
				this.imMax = imMax;
				this.width = width;
				this.height = height;
				this.yMin = yMin;
				this.yMax = yMax;
				this.data = data;
				this.polynom = polynom;
				this.rootedPolynom = rootedPolynom;
				this.offset = offset;
			}

			@Override
			public Void call() throws Exception {

				for (int y = yMin; y <= yMax; y++) {
					for (int x = 0; x < width; x++) {
						double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
						double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
						Complex zn = new Complex(cre, cim);
						int iter = 0;
						double module;
						Complex zn1;
						do {
							zn1 = zn.sub(polynom.apply(zn).divide(polynom.derive().apply(zn)));
							module = zn1.sub(zn).module();
							zn = zn1;
							iter++;
						} while (module > CONVERGENCE_TRESHOLD && iter < MAX_ITER);
						int index = rootedPolynom.indexOfClosestRootFor(zn1, ROOT_TRESHOLD);
						if (index == -1) {
							data[offset++] = 0;
						} else {
							data[offset++] = (short) index;
						}
					}
				}
				return null;
			}

		}

	}

}
