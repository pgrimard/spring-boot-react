package com.patrickgrimard.examples

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.test.context.junit4.SpringRunner

/**
 *
 *
 * Created on 2016-12-13
 *
 * @author Patrick
 */
@RunWith(SpringRunner::class)
@JsonTest
class ItemTest {
    lateinit var json: JacksonTester<Item>

    @Before
    fun init() {
        val mapper = ObjectMapper()
        JacksonTester.initFields(this, mapper)
    }

    @Test
    fun testJacksonSerialization() {
        val item = Item("RxJS", 1)

        assertThat(this.json.write(item))
                .extractingJsonPathStringValue("@.name")
                .isEqualTo("RxJS")

        assertThat(this.json.write(item))
                .extractingJsonPathNumberValue("@.quantity")
                .isEqualTo(1)
    }

    @Test
    fun testJacksonDeserialization() {
        val item: String = "{\"name\": \"RxJS\", \"quantity\": 1}"

        assertThat(this.json.read(item.byteInputStream()))
                .isEqualTo(Item("RxJS", 1))
    }
}