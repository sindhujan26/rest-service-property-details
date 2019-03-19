package restpackage;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RESTControllerTests {

    @Autowired
    private MockMvc mockMvc;

	//Test if the REST end point is reachable, and the response is as expected.
    @Test
    public void restInvokeTest() throws Exception {
    	String jsonString = "[" + 
				"    \"home_482\"," + 
				"    \"home_d50\"," + 
				"    \"home_1c9\"," + 
				"    \"home_1cd\"," + 
				"    \"home_7d1\"," + 
				"    \"home_554\"," + 
				"    \"home_c8d\"," + 
				"    \"home_f5e\"," + 
				"    \"home_ae4\"," + 
				"    \"home_c42\"" + 
				"]";

    	MvcResult result = this.mockMvc.perform(post("/getHighestValuedPropertyOwnerName")
        		.contentType(MediaType.APPLICATION_JSON).content(jsonString))
        .andDo(print()).andExpect(status().isOk()).andReturn();
    	assertEquals("Michele Marshall", result.getResponse().getContentAsString());
    }

	// Add more tests
}
