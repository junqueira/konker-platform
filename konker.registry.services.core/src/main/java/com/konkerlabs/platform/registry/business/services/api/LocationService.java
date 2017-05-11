package com.konkerlabs.platform.registry.business.services.api;

import java.util.List;

import com.konkerlabs.platform.registry.business.model.Application;
import com.konkerlabs.platform.registry.business.model.Device;
import com.konkerlabs.platform.registry.business.model.Location;
import com.konkerlabs.platform.registry.business.model.Tenant;

public interface LocationService {

	enum Validations {
	    LOCATION_GUID_NULL("service.location.guid_null"),
	    LOCATION_GUID_DOES_NOT_EXIST("service.location.guid_does_not_exist"),
		LOCATION_NAME_ALREADY_REGISTERED("service.location.name_already_registered"),
		LOCATION_PARENT_NULL("service.location.parent_null"),
		LOCATION_PARENT_NOT_FOUND("service.location.parent_not_found"),
		LOCATION_HAVE_DEVICES("service.location.have_devices"),
		LOCATION_HAVE_CHILDRENS("service.location.have_childrens")
		;

		public String getCode() {
			return code;
		}

		private String code;

		Validations(String code) {
			this.code = code;
		}
	}

    enum Messages {
        LOCATION_REGISTERED_SUCCESSFULLY("service.location.registered_success"),
        LOCATION_REMOVED_SUCCESSFULLY("service.location.removed_succesfully"),
        LOCATION_NOT_FOUND("service.location.not_found"),
        LOCATION_ROOT_NOT_FOUND("service.location.root_not_found")
        ;

        public String getCode() {
            return code;
        }

        private String code;

        Messages(String code) {
            this.code = code;
        }

    }

    ServiceResponse<Location> findRoot(Tenant tenant, Application application);
    ServiceResponse<Location> findDefault(Tenant tenant, Application application);
    ServiceResponse<Location> findByName(Tenant tenant, Application application, String locationName, boolean loadTree);
    ServiceResponse<Location> findByGuid(Tenant tenant, Application application, String locationName);

    ServiceResponse<Location> save(Tenant tenant, Application application, Location location);
	ServiceResponse<Location> update(Tenant tenant, Application application, String guid, Location location);
	ServiceResponse<Location> remove(Tenant tenant, Application application, String guid);
	ServiceResponse<List<Location>> findAll(Tenant tenant, Application application);

    ServiceResponse<List<Device>> listDevicesByLocationName(Tenant tenant, Application application, String locationName);

}