package br.com.marcosgorll.busroute.service;

import static br.com.marcosgorll.busroute.service.RouteComparator.ROUTE_COMPARATOR;

import java.util.Iterator;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Main service for the microservice. Verify if there is or there is not direct routes between two bus stops
 * 
 * @author marcos.gorll
 */
@Service
public class RouteService {

	private final NavigableSet<RouteStepEntry> EMPTY_ROUTE = new TreeSet<>(ROUTE_COMPARATOR);

	@Autowired
	private RouteRegister routeRegister;

	/**
	 * Verify if two bus steps are in the same route, and if the <code>pickUpId</code> is before or same of <code>dropOffId</code>
	 * 
	 * @param pickUpId
	 *            The start bus stop
	 * @param dropOffId
	 *            The target bus stop
	 * @return <code>true</code> if is direct route between the two bus stops and <code>false</code> if is not direct route
	 */
	public boolean hasDirectRoute(int pickUpId, int dropOffId) {
		if (dropOffId == pickUpId) {
			return true;
		}

		Map<Integer, NavigableSet<RouteStepEntry>> busStations = routeRegister.getBusStations();

		NavigableSet<RouteStepEntry> departureStationRoutes = busStations.getOrDefault(pickUpId, EMPTY_ROUTE);
		if (departureStationRoutes.isEmpty()) {
			return false;
		}

		NavigableSet<RouteStepEntry> arrivalStationRoutes = busStations.getOrDefault(dropOffId, EMPTY_ROUTE);
		if (arrivalStationRoutes.isEmpty()) {
			return false;
		}

		Iterator<RouteStepEntry> depIterator = departureStationRoutes.iterator();

		RouteStepEntry fromEntry = depIterator.next();
		RouteStepEntry toEntry = null;
		while (fromEntry != null) {
			// Here is some kind of magic. With this we do not to verify if the target sid is before of the departure sid
			toEntry = arrivalStationRoutes.ceiling(fromEntry);
			if (toEntry == null) {
				fromEntry = safeNext(depIterator);
			} else if (fromEntry.getRouteId() != toEntry.getRouteId()) {
				fromEntry = safeNext(depIterator);
			} else {
				return true;
			}
		}
		return false;
	}

	private static RouteStepEntry safeNext(Iterator<RouteStepEntry> iterator) {
		return iterator.hasNext() ? iterator.next() : null;
	}

}
