package com.konkerlabs.platform.registry.business.repositories.solr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.konkerlabs.platform.registry.business.exceptions.BusinessException;
import com.konkerlabs.platform.registry.business.model.Device;
import com.konkerlabs.platform.registry.business.model.Event;
import com.konkerlabs.platform.utilities.parsers.json.JsonParsingService;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.Optional;

@Repository
public class EventRepositoryImpl implements EventRepository {

    @Autowired
    private JsonParsingService jsonParsingService;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void push(Device device, Event event) throws BusinessException {
        Optional.ofNullable(device)
                .orElseThrow(() -> new IllegalArgumentException("Device cannot be null"));
        Optional.ofNullable(event)
            .orElseThrow(() -> new IllegalArgumentException("Event cannot be null"));
        Optional.ofNullable(event.getTimestamp())
            .orElseThrow(() -> new IllegalStateException("Event timestamp cannot be null"));

        SolrTemplate template = applicationContext.getBean(SolrTemplate.class);
        template.setSolrCore(MessageFormat.format("{0}-{1}",device.getTenant().getDomainName(),"events"));

        SolrInputDocument toBeSent = new SolrInputDocument();

        try {
            jsonParsingService.toFlatMap(event.getPayload()).forEach((field, value) -> {
                toBeSent.addField(field,value);
            });
        } catch (JsonProcessingException e) {
            throw new BusinessException("Payload processing exception",e);
        }

        toBeSent.remove("ts");
        toBeSent.addField("ts", Instant.now());

        template.saveDocument(toBeSent);
        template.commit();
    }
}