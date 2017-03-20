package br.com.marcosgorll.busroute.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableSet;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test for {@link RouteRegister}
 * 
 * @author marcos.gorll
 */
public class RouteRegisterTest {

	@Test
	public void testRegister() {
		try {
			String file = //
			/*		*/"3\n" + //
			/*		*/"1001 3 8 2\n" + //
			/*		*/"1002 2 3 5 7\n" + //
			/*		*/"1003 7 8 3 2";

			RouteRegister routeRegisterMock = Mockito.spy(RouteRegister.class);
			Mockito.doReturn(new BufferedInputStream(new ByteArrayInputStream(file.getBytes()))).when(routeRegisterMock).getStreamFromFile(null);
			routeRegisterMock.mountRoutes(null);
			Map<Integer, NavigableSet<RouteStepEntry>> busStations = routeRegisterMock.getBusStations();
			assertNotNull(busStations);
			assertEquals(5, busStations.size());
			assertRouteStepEntry(busStations.get(3), new RouteStepEntry(1001, 1), new RouteStepEntry(1002, 2), new RouteStepEntry(1003, 3));
			assertRouteStepEntry(busStations.get(8), new RouteStepEntry(1001, 2), new RouteStepEntry(1003, 2));
			assertRouteStepEntry(busStations.get(2), new RouteStepEntry(1001, 3), new RouteStepEntry(1002, 1), new RouteStepEntry(1003, 4));
			assertRouteStepEntry(busStations.get(5), new RouteStepEntry(1002, 3));
			assertRouteStepEntry(busStations.get(7), new RouteStepEntry(1002, 4), new RouteStepEntry(1003, 1));
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		}
	}

	private static void assertRouteStepEntry(NavigableSet<RouteStepEntry> routes, RouteStepEntry... compare) {
		assertNotNull(routes);
		assertEquals(compare.length, routes.size());
		Iterator<RouteStepEntry> it = routes.iterator();
		while (it.hasNext()) {
			RouteStepEntry step = it.next();
			int routeId = step.getRouteId();
			boolean found = false;
			for (RouteStepEntry routeStepEntry : compare) {
				if (routeStepEntry.getRouteId() == routeId) {
					assertEquals(routeStepEntry.getBusStopIndex(), step.getBusStopIndex());
					found = true;
					break;
				}
			}
			if (!found) {
				fail("Step not found.");
			}
		}
	}

}
