package com.hilton.assignment;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hilton.assignment.model.IpData;
import com.hilton.assignment.repository.IpRepo;
import com.hilton.assignment.service.IPService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @Copyright Any portion of this assignment's code are not allowed to use in business or production.
 *
 * Controller and request validation test.
 *
 * @author Anjana
 */

@SpringBootTest
@AutoConfigureMockMvc
class AssignmentApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	IPService ipService;

	@MockBean
	IpRepo ipRepo;

	@Test
	void contextLoads() {
	}

	/**
	 * Controller should return successful mock response
	 *
	 * @throws Exception
	 */
	@Test
	public void shouldReturnValidMockData() throws Exception {
		IpData ipData = new IpData();
		ipData.setQuery("1.2.3.4");
		Mockito.when(ipService.getIpData(ArgumentMatchers.anyString())).thenReturn(ipData);
		this.mockMvc.perform(get("/info?ip=1.2.3.4")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("1.2.3.4")));
	}

	/**
	 * Validation should fail in this case
	 *
	 * @throws Exception
	 */
	@Test
	public void shouldThrowsAnExceptionWithBadRequest() throws Exception {
		IpData ipData = new IpData();
		// Mock return
		Mockito.when(ipService.getIpData(ArgumentMatchers.anyString())).thenReturn(ipData);
		this.mockMvc.perform(get("/info?ip=1111")).andDo(print()).andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("Invalid IP Address")));
	}
}
