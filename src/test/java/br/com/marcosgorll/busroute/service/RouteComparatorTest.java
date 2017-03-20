package br.com.marcosgorll.busroute.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test for {@link RouteComparator}
 * 
 * @author marcos.gorll
 */
public class RouteComparatorTest {

	@Test
	public void testCompareSmallerRouteId() {
		int result = RouteComparator.ROUTE_COMPARATOR.compare(new RouteStepEntry(1, 1), new RouteStepEntry(2, 1));
		assertEquals(-1, result);
	}

	@Test
	public void testCompareBiggerRouteId() {
		int result = RouteComparator.ROUTE_COMPARATOR.compare(new RouteStepEntry(3, 1), new RouteStepEntry(2, 1));
		assertEquals(1, result);
	}

	@Test
	public void testCompareEqualsIdSmallerIndex() {
		int result = RouteComparator.ROUTE_COMPARATOR.compare(new RouteStepEntry(1, 1), new RouteStepEntry(1, 2));
		assertEquals(-1, result);
	}

	@Test
	public void testCompareEqualsIdBiggerIndex() {
		int result = RouteComparator.ROUTE_COMPARATOR.compare(new RouteStepEntry(1, 3), new RouteStepEntry(1, 2));
		assertEquals(1, result);
	}

	@Test
	public void testCompareEqualsIdEqualsIndex() {
		int result = RouteComparator.ROUTE_COMPARATOR.compare(new RouteStepEntry(1, 1), new RouteStepEntry(1, 1));
		assertEquals(0, result);
	}

}
