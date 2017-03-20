package br.com.marcosgorll.busroute.api;

/**
 * Response if has or has not direct route between two locations
 * 
 * @author marcos.gorll
 */
public class RouteResponse {

	private final int pickup_id;
	private final int dropoff_id;
	private final boolean has_direct_route;

	public RouteResponse(int pickup_id, int dropoff_id, boolean has_direct_route) {
		this.pickup_id = pickup_id;
		this.dropoff_id = dropoff_id;
		this.has_direct_route = has_direct_route;
	}

	public int getPickup_id() {
		return pickup_id;
	}

	public int getDropoff_id() {
		return dropoff_id;
	}

	public boolean getHas_direct_route() {
		return has_direct_route;
	}

	@Override
	public String toString() {
		return String.format("From %s to %s, has direct route? %s", getPickup_id(), getDropoff_id(), getHas_direct_route());
	}

}
