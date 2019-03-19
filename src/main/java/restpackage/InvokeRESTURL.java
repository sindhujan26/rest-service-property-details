package restpackage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class provides REST consumer utilities
 * 
 * @author Sindhu Prasannakumar
 *
 */
public class InvokeRESTURL {
	/**
	 * This method invokes a given REST end point. 
	 * This method assumes the content type to be "application/json".
	 * 
	 * @param urlString REST end point to invoke
	 * @param method HTTP method to be used for the REST call, for example, GET/POST/PUT/etc.
	 * @param body the payload for the REST call
	 * @return	Returns the JSON string output
	 * 
	 * @throws Exception
	 */
	public static synchronized String invokeREST(String urlString, String method, String body) throws Exception {
		try {

			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty( "Content-Type", "application/json");

			// Set the payload for a POST method.
			if ("POST".equals(method)) {
				conn.setInstanceFollowRedirects( false );
				conn.setUseCaches( false );
				conn.setDoOutput( true );
				conn.setRequestMethod(method);
				try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
				   wr.write( body.getBytes() );
				}
			}

			// Throw error if not a desirable response
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			// Parse the response into a JSON 
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output = "";
			String line = "";
			while ((line = br.readLine()) != null) {
				output += line;
			}

			conn.disconnect();
			return output;
		} catch (MalformedURLException e) {

			throw e;
		} catch (IOException e) {

			throw e;
		}
	}

	/**
	 * This method can be used to test the REST end point created in REST Controller.
	 * It uses the InvokeREST utility method defined above.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		// Note this can also be taken as a file input, for now i am considering a string
		String jsonStrng = "[" + 
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

		try {

			System.out.println(invokeREST("http://localhost:8080/getHighestValuedPropertyOwnerName", "POST", jsonStrng));
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
