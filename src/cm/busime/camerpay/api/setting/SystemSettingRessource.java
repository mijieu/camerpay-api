package cm.busime.camerpay.api.setting;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cm.busime.camerpay.api.ResponseBuilder;
import cm.busime.camerpay.api.entity.SystemSetting;

@Path("/admin/system-settings")
public class SystemSettingRessource {
	
	@Inject
	SystemSettingsFacade systemSettingsFacade;
	
	@Inject
	private ResponseBuilder responseBuilder;

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveSystemSettings(){
		return responseBuilder
	        .statusOk()
	        .cacheControlNoCache()
	        .entity(systemSettingsFacade.retrieveSystemSettings())
	        .build();
	}
	
	@GET
	@Path("/get-single/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveSystemSettings(@PathParam("key") final String key){
		return responseBuilder
	        .statusOk()
	        .cacheControlNoCache()
	        .entity(systemSettingsFacade.retrieveSingleSystemSettings(key))
	        .build();
	}
	
	@PUT
	@Path("/insert-all")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveSystemSettings(final JsonObject jsonObject){
		systemSettingsFacade.saveSystemSettigs(jsonObject);
		return responseBuilder
            .statusCreated()
            .build();
	}
	
	@PUT
	@Path("/insert-single")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveSystemSettings(final SystemSetting systemSetting){
		systemSettingsFacade.saveSingleSystemSettings(systemSetting);
		return responseBuilder
            .statusCreated()
            .build();
	}
}
