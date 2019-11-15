package io.pockid.backend.usermanagement.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.support.PublisherFactory;
import org.springframework.cloud.gcp.pubsub.support.SubscriberFactory;
import org.springframework.cloud.gcp.pubsub.support.converter.JacksonPubSubMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PubSubConf {

    @Bean
    public PubSubTemplate pubSubTemplate(PublisherFactory publisherFactory,
                                         SubscriberFactory subscriberFactory,
                                         ObjectMapper objectMapper) {
        PubSubTemplate pubSubTemplate = new PubSubTemplate(publisherFactory, subscriberFactory);
        pubSubTemplate.setMessageConverter(new JacksonPubSubMessageConverter(objectMapper));
        return pubSubTemplate;
    }
}
