package br.com.marcosgorll.busroute.service;

final class RouteStepEntry {

	private final int routeId;
	private final int busStopIndex;

	public RouteStepEntry(int routeId, int busStopIndex) {
		this.routeId = routeId;
		this.busStopIndex = busStopIndex;
	}

	public int getRouteId() {
		return routeId;
	}

	public int getBusStopIndex() {
		return busStopIndex;
	}

	@Override
	public String toString() {
		return String.format("RouteId: %s, busStopIndex: %s", getRouteId(), getBusStopIndex());
	}

}
