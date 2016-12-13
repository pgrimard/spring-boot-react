package com.patrickgrimard.examples

import org.hamcrest.Matchers.isA
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 *
 *
 * Created on 2016-12-13
 *
 * @author Patrick
 */
@RunWith(SpringRunner::class)
@WebMvcTest
class IndexControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var itemRepository: ItemRepository

    @Before
    fun init() {
        given<List<Item>>(itemRepository.findAll()).willReturn(arrayListOf(Item("RxJS", 1)))
    }

    @Test
    fun testChild() {
        this.mvc.perform(get("/child"))
                .andExpect(status().isOk)
                .andExpect(model().attributeExists("req"))
                .andExpect(model().attribute("req", isA<String>(String::class.java)))
                .andExpect(model().attributeExists("initialState"))
                .andExpect(model().attribute("initialState", isA<String>(String::class.java)))
                .andReturn()

        verify<ItemRepository>(itemRepository).findAll()
    }
}