package restpackage;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * This class exposes one RESTful web endpoint which takes a list of home_ids 
 * as input in the form of a JSON array 
 * and returns the name of the property owner who owns the highest valued 
 * property in the state of Virginia, amongst the provided home ids.
 * The service internally makes use of the following end point: "http://webservice-takehome-1.spookle.xyz/property".
 * 
 * @author Sindhu Prasannakumar
 *
 */
@RestController
public class RESTController {

	public static final String STATE_VA = "VA";
	public static final String REST_ENDPOINT_URL_1 = "http://webservice-takehome-1.spookle.xyz/property?property_id=";
	public static final String REST_ENDPOINT_URL_2 = "http://webservice-takehome-1.spookle.xyz/property?property_id=";

	/**
	 * Using the REST_ENDPOINT_URL mentioned above
	 * 
	 * @param propertyDetails JSON String Array - List of home ids
	 * @return Name of the owner of the property with highest value in the state of Virginia, amongst the given home ids.
	 */
	@RequestMapping(method = RequestMethod.POST, value= "/getHighestValuedPropertyOwnerName", consumes="application/json")
	public String getHighestValuedPropertyOwnerName(@RequestBody List<String> propertyDetails) {

		// Owner name to return
		String ownerName = null;

		// Highest value
		long value = 0;
		ObjectMapper mapper = new ObjectMapper();
		try {

			// Loop through the given home id array
			for (String pd : propertyDetails) {
				String str = "";
				try {

					// Invoke the first URL and fetch the property details
					str = InvokeRESTURL.invokeREST(REST_ENDPOINT_URL_1 + pd, "GET", null);
				} catch (Exception e ) {

					//If the first URL is not accessible, try to fetch the property details using the alternate URL
					str = InvokeRESTURL.invokeREST(REST_ENDPOINT_URL_2 + pd, "GET", null);
				}

				// Parse the response string into the PropertyDetail bean
				PropertyDetail propertyDetail = mapper.readValue(str, PropertyDetail.class);

				// Check if the property belongs to the state of Virginia
				if (propertyDetail.getAddress() != null && propertyDetail.getAddress().getState() != null && propertyDetail.getAddress().getState().equals(STATE_VA)) {
					if (ownerName != null) {

						// Assign the highest owner name and value
						if (value < propertyDetail.getValue()) {
							ownerName = propertyDetail.getOwner();
							value = propertyDetail.getValue();
						}
					} else {

						// Set the default owner name and value
						ownerName = propertyDetail.getOwner();
						value = propertyDetail.getValue();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ownerName;
	}
}
