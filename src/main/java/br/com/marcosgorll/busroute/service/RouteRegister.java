package br.com.marcosgorll.busroute.service;

import static br.com.marcosgorll.busroute.service.RouteComparator.ROUTE_COMPARATOR;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Scanner;
import java.util.TreeSet;

import org.apache.log4j.Logger;

public class RouteRegister {

	private final static Logger LOG = Logger.getLogger(RouteRegister.class);

	private Map<Integer, NavigableSet<RouteStepEntry>> busStations;

	public Map<Integer, NavigableSet<RouteStepEntry>> getBusStations() {
		return busStations;
	}

	public void mountRoutes(File routeFile) {
		Map<Integer, NavigableSet<RouteStepEntry>> stations = new HashMap<>();
		try (Scanner sc = new Scanner(getStreamFromFile(routeFile))) {
			int routeCount = Integer.parseInt(sc.nextLine());
			for (int i = 0; i < routeCount; i++) {
				String[] busRoute = sc.nextLine().split(" ");
				for (int sidIndex = 1 /* stkip route id */; sidIndex < busRoute.length; sidIndex++) {
					int busRouteId = Integer.parseInt(busRoute[0]);
					int sidId = Integer.parseInt(busRoute[sidIndex]);
					stations.computeIfAbsent(sidId, key -> new TreeSet<>(ROUTE_COMPARATOR)).add(new RouteStepEntry(busRouteId, sidIndex));
				}
			}
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage(), e);
		}
		this.busStations = Collections.unmodifiableMap(stations);
	}

	BufferedInputStream getStreamFromFile(File routeFile) throws FileNotFoundException {
		return new BufferedInputStream(new FileInputStream(routeFile));
	}

}
