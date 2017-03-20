package br.com.marcosgorll.busroute.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.NavigableSet;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test for {@link RouteService}
 * 
 * @author marcos.gorll
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RouteService.class)
public class RouteServiceTest {

	@Autowired
	private ResourceLoader resourceLoader;

	@MockBean
	private RouteRegister routeRegister;

	@InjectMocks
	@Resource
	private RouteService service;

	@Test
	public void testDirectRouteFrom3to8() throws Exception {
		Map<Integer, NavigableSet<RouteStepEntry>> busStations = safeBuildRegisterMock("route1.txt").getBusStations();
		when(routeRegister.getBusStations()).thenReturn(busStations);
		assertTrue(service.hasDirectRoute(3, 8));
	}

	@Test
	public void testDirectRouteFrom2to8() throws Exception {
		Map<Integer, NavigableSet<RouteStepEntry>> busStations = safeBuildRegisterMock("route1.txt").getBusStations();
		when(routeRegister.getBusStations()).thenReturn(busStations);
		assertFalse(service.hasDirectRoute(2, 8));
	}

	@Test
	public void testDirectRouteFrom7to2() throws Exception {
		Map<Integer, NavigableSet<RouteStepEntry>> busStations = safeBuildRegisterMock("route1.txt").getBusStations();
		when(routeRegister.getBusStations()).thenReturn(busStations);
		assertTrue(service.hasDirectRoute(7, 2));
	}

	@Test
	public void testDirectRouteFrom2to7() throws Exception {
		Map<Integer, NavigableSet<RouteStepEntry>> busStations = safeBuildRegisterMock("route1.txt").getBusStations();
		when(routeRegister.getBusStations()).thenReturn(busStations);
		assertTrue(service.hasDirectRoute(2, 7));
	}

	@Test
	public void testDirectRouteFrom99to100() throws Exception {
		Map<Integer, NavigableSet<RouteStepEntry>> busStations = safeBuildRegisterMock("route2.txt").getBusStations();
		when(routeRegister.getBusStations()).thenReturn(busStations);
		assertTrue(service.hasDirectRoute(99, 100));
	}

	@Test
	public void testDirectRouteFrom100to99() throws Exception {
		Map<Integer, NavigableSet<RouteStepEntry>> busStations = safeBuildRegisterMock("route2.txt").getBusStations();
		when(routeRegister.getBusStations()).thenReturn(busStations);
		assertFalse(service.hasDirectRoute(100, 99));
	}

	@Test
	public void testDirectRouteFrom15to5() throws Exception {
		Map<Integer, NavigableSet<RouteStepEntry>> busStations = safeBuildRegisterMock("route2.txt").getBusStations();
		when(routeRegister.getBusStations()).thenReturn(busStations);
		assertTrue(service.hasDirectRoute(15, 5));
	}

	@Test
	public void testDirectRouteFrom5to15() throws Exception {
		Map<Integer, NavigableSet<RouteStepEntry>> busStations = safeBuildRegisterMock("route2.txt").getBusStations();
		when(routeRegister.getBusStations()).thenReturn(busStations);
		assertTrue(service.hasDirectRoute(15, 5));
	}

	@Test
	public void testDirectRouteFrom1to100() throws Exception {
		Map<Integer, NavigableSet<RouteStepEntry>> busStations = safeBuildRegisterMock("route2.txt").getBusStations();
		when(routeRegister.getBusStations()).thenReturn(busStations);
		assertFalse(service.hasDirectRoute(1, 100));
	}

	private RouteRegister safeBuildRegisterMock(String fileName) {
		try {
			org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:" + fileName);
			assertTrue(resource.exists());
			String data = null;
			try (BufferedReader buffer = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
				data = buffer.lines().collect(Collectors.joining("\n"));
			}
			RouteRegister routeRegisterMock = Mockito.spy(RouteRegister.class);
			Mockito.doReturn(new BufferedInputStream(new ByteArrayInputStream(data.getBytes()))).when(routeRegisterMock).getStreamFromFile(null);
			routeRegisterMock.mountRoutes(null);
			return routeRegisterMock;
		} catch (Exception e) {
			fail(e.getMessage());
			return null; // never, java compiler is crazy
		}
	}

}
