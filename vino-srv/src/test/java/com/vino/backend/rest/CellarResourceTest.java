package com.vino.backend.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.vino.backend.model.Movement;
import org.junit.Test;
import restx.factory.Factory;

import static org.assertj.core.api.Assertions.assertThat;


public class CellarResourceTest {


    private static final Factory FACTORY = Factory.builder().addFromServiceLoader().build();

    @Test
    public void should_jongo_map_key() throws Exception {

//        ObjectMapper frontObjectMapper = FACTORY.getComponent(Name.of(ObjectMapper.class, "FrontObjectMapper"));
        ObjectMapper frontObjectMapper = new ObjectMapper()
                .registerModule(new JodaModule())
                .registerModule(new GuavaModule())
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        String json = "{\"@class\":\"com.vino.backend.model.Movement\",\"_id\":\"53581a4f036419598a363d94\"," +
                "\"type\":\"OUT\",\"amount\":1,\"record\":" +
                "{\"@class\":\"com.vino.backend.model.WineCellarRecord\",\"_id\":\"53581a4f036419598a363d94\",\"" +
                "domain\":\"5323918203647408e5a3ec3d\",\"vintage\":12,\"quantity\":20}}";

        Movement movement = frontObjectMapper.readValue(json, Movement.class);

        assertThat(movement.getKey()).isNotNull();
        assertThat(movement.getRecord()).isNotNull();
        assertThat(movement.getRecord().getKey()).isNotNull().isNotEmpty();
    }
}
