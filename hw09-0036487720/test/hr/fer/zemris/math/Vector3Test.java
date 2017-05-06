package hr.fer.zemris.math;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.math.Vector3;

@SuppressWarnings("javadoc")
public class Vector3Test {
	private static final double DELTA = 0.001;

	private Vector3 first;
	private Vector3 second;

	@Before
	public void init() {
		first = new Vector3(15, -14.2, 12);
		second = new Vector3(-21, 27.4, -14.5);
	}

	@Test
	public void vectorNorm() {
		assertEquals(23.888, first.norm(), DELTA);
		assertEquals(37.443424, second.norm(), DELTA);
	}

	@Test
	public void normalizedVector() throws Exception {
		assertEquals(new Vector3(-0.560846, 0, -0.38725).getX(), second.normalized().getX(), DELTA);
		assertEquals(new Vector3(-0.560846, 0.73177, -0.38725).getY(), second.normalized().getY(), DELTA);
		assertEquals(new Vector3(-0.560846, 0, -0.38725).getZ(), second.normalized().getZ(), DELTA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addNullVector() throws Exception {
		first.add(null);
	}

	@Test
	public void addVectors() throws Exception {
		assertEquals(new Vector3(-6, 13.2, -2.5).getX(), first.add(second).getX(), DELTA);
		assertEquals(new Vector3(-6, 13.2, -2.5).getY(), first.add(second).getY(), DELTA);
		assertEquals(new Vector3(-6, 13.2, -2.5).getZ(), first.add(second).getZ(), DELTA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void subtractNullVector() throws Exception {
		first.sub(null);
	}

	@Test
	public void subVectors() throws Exception {
		assertEquals(new Vector3(36, -41.6, 26.5).getX(), first.sub(second).getX(), DELTA);
		assertEquals(new Vector3(36, -41.6, 26.5).getY(), first.sub(second).getY(), DELTA);
		assertEquals(new Vector3(36, -41.6, 26.5).getZ(), first.sub(second).getZ(), DELTA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void dotProductNullVector() throws Exception {
		first.dot(null);
	}

	@Test
	public void dotProductOfVectors() throws Exception {
		assertEquals(-878.08, first.dot(second), DELTA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void crossNullVector() throws Exception {
		first.cross(null);
	}

	@Test
	public void crossVectors() throws Exception {
		assertEquals(new Vector3(-122.9, -34.5, 112.8).getX(), first.cross(second).getX(), DELTA);
		assertEquals(new Vector3(-122.9, -34.5, 112.8).getY(), first.cross(second).getY(), DELTA);
		assertEquals(new Vector3(-122.9, -34.5, 112.8).getZ(), first.cross(second).getZ(), DELTA);
	}

	@Test
	public void scaleVector() throws Exception {
		assertEquals(new Vector3(30, 10.7252, 282.9).getX(), first.scale(2).getX(), DELTA);
		assertEquals(new Vector3(15.252, 0, 282.9).getY(), first.scale(0).getY(), DELTA);
		assertEquals(new Vector3(15.252, 10.7252, -24).getZ(), first.scale(-2).getZ(), DELTA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void cosAngleNull() throws Exception {
		first.cosAngle(null);
	}

	@Test
	public void cosAngleVectors() throws Exception {
		assertEquals(0 - 0.9816969, first.cosAngle(second), DELTA);
	}
}
