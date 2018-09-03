package com.konkerlabs.platform.registry.business.services;

import com.konkerlabs.platform.registry.business.model.*;
import com.konkerlabs.platform.registry.business.model.enumerations.FirmwareUpdateStatus;
import com.konkerlabs.platform.registry.business.model.validation.CommonValidations;
import com.konkerlabs.platform.registry.business.repositories.DeviceFirmwareRepository;
import com.konkerlabs.platform.registry.business.repositories.DeviceFirmwareUpdateRepository;
import com.konkerlabs.platform.registry.business.services.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DeviceFirmwareUpdateServiceImpl implements DeviceFirmwareUpdateService {

    private Logger LOGGER = LoggerFactory.getLogger(DeviceFirmwareUpdateServiceImpl.class);

    @Autowired
    private DeviceFirmwareUpdateRepository deviceFirmwareUpdateRepository;


    @Override
    public ServiceResponse<DeviceFwUpdate> update(Tenant tenant, Application application, Device device, FirmwareUpdateStatus firmwareUpdateStatus){
        ServiceResponse<DeviceFwUpdate> validationsResponse = validate(tenant, application, device);
        if (validationsResponse != null && !validationsResponse.isOk()) {
            return validationsResponse;
        }


        DeviceFwUpdate deviceFwUpdate = deviceFirmwareUpdateRepository.findUnique(tenant.getId(), application.getName(), device.getId(), FirmwareUpdateStatus.PENDING);

        if (!Optional.ofNullable(deviceFwUpdate).isPresent()) {
            return ServiceResponseBuilder.<DeviceFwUpdate>error()
                    .withMessage(Validations.FIRMWARE_UPDATE_PENDING_STATUS_DOES_NOT_EXIST.getCode())
                    .build();
        }

        deviceFwUpdate.setStatus(FirmwareUpdateStatus.UPDATED);
        deviceFwUpdate.setLastChange(Instant.now());

        Optional<Map<String, Object[]>> validations = deviceFwUpdate.applyValidations();
        if (validations.isPresent()) {
            return ServiceResponseBuilder.<DeviceFwUpdate>error()
                    .withMessages(validations.get())
                    .build();
        }

        DeviceFwUpdate deviceFwUpdated = deviceFirmwareUpdateRepository.save(deviceFwUpdate);

        return ServiceResponseBuilder.<DeviceFwUpdate>ok().withResult(deviceFwUpdated).build();
    }

    @Override
    public ServiceResponse<DeviceFwUpdate> findPendingFwUpdateByDevice(Tenant tenant, Application application, Device device)  {

        ServiceResponse<DeviceFwUpdate> validationResponse = validate(tenant, application, device);
        if (!validationResponse.isOk()) {
            return validationResponse;
        }

        DeviceFwUpdate deviceFwUpdate = deviceFirmwareUpdateRepository.findUnique(tenant.getId(), application.getName(), device.getId(), FirmwareUpdateStatus.PENDING);

        if (!Optional.ofNullable(deviceFwUpdate).isPresent()) {
            return ServiceResponseBuilder.<DeviceFwUpdate> error()
                    .withMessage(Validations.FIRMWARE_UPDATE_PENDING_STATUS_DOES_NOT_EXIST.getCode()).build();
        }

        return ServiceResponseBuilder.<DeviceFwUpdate>ok()
                .withResult(deviceFwUpdate)
                .build();
    }


    @Override
    public ServiceResponse<DeviceFwUpdate> confirmFwUpdateByDevice(Tenant tenant, Application application, Device device)  {

        ServiceResponse<DeviceFwUpdate> validationResponse = validate(tenant, application, device);
        if (!validationResponse.isOk()) {
            return validationResponse;
        }

        DeviceFwUpdate deviceFwUpdate = deviceFirmwareUpdateRepository.findUnique(tenant.getId(), application.getName(), device.getId(), FirmwareUpdateStatus.PENDING);

        if (!Optional.ofNullable(deviceFwUpdate).isPresent()) {
            return ServiceResponseBuilder.<DeviceFwUpdate> error()
                    .withMessage(Validations.FIRMWARE_UPDATE_PENDING_STATUS_DOES_NOT_EXIST.getCode()).build();
        }


        //TODO ->CHANGE the FirmwareUpdateStatus.PENDING to FirmwareUpdateStatus.UPDATED


        return ServiceResponseBuilder.<DeviceFwUpdate>ok()
                .withResult(deviceFwUpdate)
                .build();
    }





    private <T> ServiceResponse<T> validate(Tenant tenant, Application application, Device device) {

        if (!Optional.ofNullable(tenant).isPresent()) {
            return ServiceResponseBuilder.<T>error()
                    .withMessage(CommonValidations.TENANT_NULL.getCode()).build();
        }

        if (!Optional.ofNullable(application).isPresent()) {
            return ServiceResponseBuilder.<T>error()
                    .withMessage(ApplicationService.Validations.APPLICATION_NULL.getCode()).build();
        }

        if (!Optional.ofNullable(device).isPresent() || !Optional.ofNullable(device.getGuid()).isPresent()) {
            return ServiceResponseBuilder.<T>error()
                    .withMessage(DeviceModelService.Validations.DEVICE_MODEL_NULL.getCode()).build();
        }


        return null;
    }

}
