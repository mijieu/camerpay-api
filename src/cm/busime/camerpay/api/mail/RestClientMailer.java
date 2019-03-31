package cm.busime.camerpay.api.mail;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class RestClientMailer {
	
	public static ClientResponse SendSimpleMessage() {
	    Client client = Client.create();
	    client.addFilter(new HTTPBasicAuthFilter(
	        "api","key-3ax6xnjp29jd6fds4gc373sgvjxteol0"));
	    WebResource webResource = client.resource(
	        "https://api.mailgun.net/v3/samples.mailgun.org/messages");
	    MultivaluedMapImpl formData = new MultivaluedMapImpl();
	    formData.add("from", "Excited User <excited@samples.mailgun.org>");
	    formData.add("to", "achillle2000@yahoo.fr");
	    formData.add("subject", "Hello");
	    formData.add("text", "Testing some Mailgun awesomeness!");
	    return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
	        post(ClientResponse.class, formData);
	}
	
	public static void main(String[] arg) {
		RestClientMailer.SendSimpleMessage();
	}
}
