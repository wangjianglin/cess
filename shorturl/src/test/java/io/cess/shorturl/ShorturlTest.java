package io.cess.shorturl;

import io.cess.core.Constants;
import io.cess.test.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(CessRunner.class)
@SpringBootTest(classes = ShortUrlApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        properties = {"spring.cloud.discovery.enabled=false", "spring.cloud.config.enabled=false, spring.profiles.active=test"}
)
@AutoConfigureMockMvc(secure = false)
@CessTest
@ActiveProfiles("test")
@Transactional
@Rollback
@HttpHeader(name = Constants.HTTP_COMM_PROTOCOL,value = "0.2")
@HttpHeader(name = Constants.HTTP_COMM_PROTOCOL_DEBUG,value = "")
public class ShorturlTest {

//    @Autowired
//    private TestRestTemplate testRestTemplate;
//
//    @Test
//    public void get() throws Exception {
//        Map<String,String> multiValueMap = new HashMap<>();
//        multiValueMap.put("username","lake");//传值，但要在url上配置相应的参数
//        String result = testRestTemplate.getForObject("/api/a",String.class);
//        System.out.println("=================== " + result + " =====================");
////        Assert.assertEquals(result.getCode(),0);
//    }

//    @Autowired
//    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

//    private OAuthHelper oAuthHelper;
//    @Before
//    public void setup() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();   //构造MockMvc
//    }

    @Test
    @WithMockUser(roles = "MANAGER",username = "username")
//    @WithUserDetails(value = "user",)
    @ResultType(String.class)
    public void t() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/a")
        )
//                .andExpect(MockMvcResultMatchers.view().name("user/view"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String r = MockMvcs.result(result);
        Assert.assertTrue("ok.username".equals(r));
    }
}
