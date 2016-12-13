package com.patrickgrimard.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 2016-12-12
 *
 * @author Patrick
 */
@RunWith(SpringRunner.class)
@JsonTest
public class ItemTest {

    private JacksonTester<Item> json;

    @Before
    public void init() {
        ObjectMapper mapper = new ObjectMapper();
        JacksonTester.initFields(this, mapper);
    }

    @Test
    public void testJacksonSerialization() throws IOException {
        Item item = new Item("RxJS", 1);

        assertThat(this.json.write(item))
                .extractingJsonPathNumberValue("@.quantity")
                .isEqualTo(1);

        assertThat(this.json.write(item))
                .extractingJsonPathStringValue("@.name")
                .isEqualTo("RxJS");
    }

    @Test
    public void testJacksonDeserialization() throws IOException {
        String item = "{\"name\": \"RxJS\", \"quantity\": 1}";
        InputStream inputStream = new ByteArrayInputStream(item.getBytes());

        assertThat(this.json.read(inputStream))
                .isEqualTo(new Item("RxJS", 1));
    }
}
