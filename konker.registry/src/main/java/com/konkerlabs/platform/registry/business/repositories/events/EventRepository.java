package com.konkerlabs.platform.registry.business.repositories.events;

import com.konkerlabs.platform.registry.business.exceptions.BusinessException;
import com.konkerlabs.platform.registry.business.model.Event;
import com.konkerlabs.platform.registry.business.model.Tenant;

import java.time.Instant;
import java.util.List;

public interface EventRepository {

    enum Validations {
        INCOMING_DEVICE_ID_DOES_NOT_EXIST("repository.events.incoming_device.not_found"),
        OUTGOING_DEVICE_ID_DOES_NOT_EXIST("repository.events.outgoing_device.not_found"),
        INCOMING_DEVICE_GUID_NULL("repository.events.incoming_device.guid.not_null"),
        OUTGOING_DEVICE_GUID_NULL("repository.events.outgoing_device.guid.not_null"),
        EVENT_INCOMING_NULL("repository.events.incoming.not_null"),
        EVENT_INCOMING_CHANNEL_NULL("repository.events.incoming_channel.not_null"),
        EVENT_OUTGOING_NULL("repository.events.outgoing.not_null"),
        EVENT_OUTGOING_CHANNEL_NULL("repository.events.outgoing_channel.not_null"),
        EVENT_TIMESTAMP_NULL("repository.events.timestamp.not_null");

        private String code;

        public String getCode() {
            return code;
        }

        Validations(String code) {
            this.code = code;
        }
    }

    void saveIncoming(Tenant tenant, Event event) throws BusinessException;

    void saveOutgoing(Tenant tenant, Event event) throws BusinessException;

    List<Event> findIncomingBy(Tenant tenant,
                               String deviceGuid,
                               Instant startInstant,
                               Instant endInstant,
                               Integer limit) throws BusinessException;

    List<Event> findOutgoingBy(Tenant tenant,
                               String deviceGuid,
                               Instant startInstant,
                               Instant endInstant,
                               Integer limit) throws BusinessException;
}
