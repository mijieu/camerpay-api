package cm.busime.camerpay.api;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationPath("camerpay-api")
@OpenAPIDefinition(
		info= @Info(title= "Camerpay API Service",
					description= "Camerpay Service API handle all backend operation for eah transaction",
					version = "1.0"))

public class CamerpayAPI extends Application {
	
	@Override
	public Set<Class<?>> getClasses() {
	   Set<Class<?>> resources = new java.util.HashSet<>();
	   addRestResourceClasses(resources);
	   return resources;
	}

	private void addRestResourceClasses(Set<Class<?>> resources) {
	   resources.add(cm.busime.camerpay.api.user.UserResource.class);
	   resources.add(cm.busime.camerpay.api.user.ContactResource.class);
	   resources.add(cm.busime.camerpay.api.setting.SystemSettingRessource.class);
	}
}
