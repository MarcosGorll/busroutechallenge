package br.com.marcosgorll.busroute.api;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.marcosgorll.busroute.service.RouteService;

/**
 * Tests form {@link RouteAPI}
 * 
 * @author marcos.gorll
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RouteAPI.class)
public class RouteAPITest {

	@MockBean
	private RouteService service;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testAPIResultTrue() throws Exception {
		when(service.hasDirectRoute(2, 8)).thenReturn(true);
		this.mockMvc.perform(get("/api/direct?pickup_id=2&dropoff_id=8")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("{\"pickup_id\":2,\"dropoff_id\":8,\"has_direct_route\":true}")));
	}

	@Test
	public void testAPIResultFalse() throws Exception {
		when(service.hasDirectRoute(1, 9)).thenReturn(false);
		this.mockMvc.perform(get("/api/direct?pickup_id=1&dropoff_id=9")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("{\"pickup_id\":1,\"dropoff_id\":9,\"has_direct_route\":false}")));
	}
}
