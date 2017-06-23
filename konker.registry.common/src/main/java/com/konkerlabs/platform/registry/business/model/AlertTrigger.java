package com.konkerlabs.platform.registry.business.model;

import com.konkerlabs.platform.registry.business.model.HealthAlert.HealthAlertType;
import com.konkerlabs.platform.registry.business.model.behaviors.URIDealer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document(collection = "alertTriggers")
public abstract class AlertTrigger implements URIDealer {

    @Id
    private String id;
    protected String guid;
    @DBRef
    private Tenant tenant;
    @DBRef
    private Application application;
    @DBRef
    private DeviceModel deviceModel;
    @DBRef
    private Location location;
    protected HealthAlertType type;

    @Transient
    private Set<Location> mappedLocations;

}