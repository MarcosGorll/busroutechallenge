package br.com.marcosgorll.busroute.service;

import java.util.Comparator;

/**
 * Compares if two bus stops are really the same or not. That means, if it is the same bus stop in the same bus route
 * 
 * @author marcos.gorll
 */
final class RouteComparator implements Comparator<RouteStepEntry> {

	public static final RouteComparator ROUTE_COMPARATOR = new RouteComparator();

	private RouteComparator() {
		// Use only the constant
	}

	/**
	 * If the stops are int the same route, than we compare the position of the step in the route. If is not same route, than we compare only the route id
	 * 
	 * @return -1, 0 or 1 if the step is before, the same or after ir the routes.
	 */
	@Override
	public int compare(RouteStepEntry route1, RouteStepEntry route2) {
		if (route1.getRouteId() != route2.getRouteId()) {
			return route1.getRouteId() < route2.getRouteId() ? -1 : route1.getRouteId() > route2.getRouteId() ? 1 : 0;
		}
		return route1.getBusStopIndex() - route2.getBusStopIndex();
	}

}
