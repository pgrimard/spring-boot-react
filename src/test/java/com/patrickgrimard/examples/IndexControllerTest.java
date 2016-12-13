package com.patrickgrimard.examples;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Created on 2016-12-13
 *
 * @author Patrick
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ItemRepository itemRepository;

    @Before
    public void init() {
        given(itemRepository.findAll()).willReturn(Arrays.asList(new Item("RxJS", 1)));
    }

    @Test
    public void testIndex() throws Exception {
        String response = this.restTemplate.getForObject("/", String.class);

        verify(itemRepository).findAll();

        assertThat(response).isEqualTo("<!doctype html><html lang=\"en\"><head><meta charset=\"utf-8\"><title></title><meta name=\"description\" content=\"\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"><link href=\"/client.css?157a44a13cdc1fccc64e\" rel=\"stylesheet\"></head><body><!--[if lt IE 7]>\n" +
                "<p class=\"browsehappy\">You are using an <strong>outdated</strong> browser. Please <a href=\"http://browsehappy.com/\">upgrade your browser</a> to improve your experience.</p>\n" +
                "<![endif]--><div id=\"app\"><div data-reactroot=\"\" data-reactid=\"1\" data-react-checksum=\"1818423168\"><h1 data-reactid=\"2\">This is the Title!</h1><ul data-reactid=\"3\"><li data-reactid=\"4\"><a href=\"/\" class=\"\" data-reactid=\"5\">Home</a></li><li data-reactid=\"6\"><a href=\"/child\" class=\"\" data-reactid=\"7\">Child</a></li></ul><div data-reactid=\"8\"><h2 data-reactid=\"9\">Here, have some items!</h2><div data-reactid=\"10\"><form data-reactid=\"11\"><input type=\"text\" name=\"name\" data-reactid=\"12\"/><button type=\"submit\" data-reactid=\"13\">Add</button></form><ul class=\"item-list\" data-reactid=\"14\"><li data-reactid=\"15\"><!-- react-text: 16 -->RxJS<!-- /react-text --><!-- react-text: 17 --> <!-- /react-text --><a class=\"delete\" data-reactid=\"18\">×</a></li></ul></div></div><!-- react-empty: 19 --><!-- react-empty: 20 --></div></div><script>window.__PRELOADED_STATE__ = {\"items\":[{\"name\":\"RxJS\",\"quantity\":1}],\"messages\":{\"title\":\"This is the Title!\"}};</script><script type=\"text/javascript\" src=\"/client.js?157a44a13cdc1fccc64e\"></script></body></html>");
    }

    @Test
    public void testChild() throws Exception {
        String response = this.restTemplate.getForObject("/child", String.class);

        verify(itemRepository).findAll();

        assertThat(response).isEqualTo("<!doctype html><html lang=\"en\"><head><meta charset=\"utf-8\"><title></title><meta name=\"description\" content=\"\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"><link href=\"/client.css?157a44a13cdc1fccc64e\" rel=\"stylesheet\"></head><body><!--[if lt IE 7]>\n" +
                "<p class=\"browsehappy\">You are using an <strong>outdated</strong> browser. Please <a href=\"http://browsehappy.com/\">upgrade your browser</a> to improve your experience.</p>\n" +
                "<![endif]--><div id=\"app\"><div data-reactroot=\"\" data-reactid=\"1\" data-react-checksum=\"-1266124420\"><h1 data-reactid=\"2\">This is the Title!</h1><ul data-reactid=\"3\"><li data-reactid=\"4\"><a href=\"/\" class=\"\" data-reactid=\"5\">Home</a></li><li data-reactid=\"6\"><a href=\"/child\" class=\"\" data-reactid=\"7\">Child</a></li></ul><!-- react-empty: 8 --><div data-reactid=\"9\"><h2 data-reactid=\"10\">I am a child route!</h2></div><!-- react-empty: 11 --></div></div><script>window.__PRELOADED_STATE__ = {\"items\":[{\"name\":\"RxJS\",\"quantity\":1}],\"messages\":{\"title\":\"This is the Title!\"}};</script><script type=\"text/javascript\" src=\"/client.js?157a44a13cdc1fccc64e\"></script></body></html>");
    }
}
