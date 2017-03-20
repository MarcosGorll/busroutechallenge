package br.com.marcosgorll.busroute.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcosgorll.busroute.service.RouteService;

/**
 * API to microservice.
 * 
 * @author marcos.gorll
 */
@RestController
@RequestMapping("/api")
public class RouteAPI {

	@Autowired
	private RouteService busRouteService;

	/**
	 * Exposes /api/direct?pickup_id={id}&dropoff_id={id}
	 * 
	 * @param pickup_id
	 *            Id from the start of the route
	 * @param dropoff_id
	 *            Id from the target of the route
	 * @return Start and target routes, with <code>boolean</code> indicating if ther is a direct route between start and target
	 */
	@RequestMapping(value = "/direct", method = { RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public RouteResponse hasDirectRoute(@RequestParam(name = "pickup_id", required = true) Integer pickup_id, @RequestParam(name = "dropoff_id", required = true) Integer dropoff_id) {
		boolean hasDirectRoute = busRouteService.hasDirectRoute(pickup_id, dropoff_id);
		return new RouteResponse(pickup_id, dropoff_id, hasDirectRoute);
	}

}
