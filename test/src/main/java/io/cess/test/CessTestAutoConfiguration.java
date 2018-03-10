package io.cess.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.DispatcherServletCustomizer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.List;
import java.util.Map;

@TestConfiguration
//@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
//@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties(WebMvcProperties.class)
public class CessTestAutoConfiguration {
    private final WebApplicationContext context;

    private final WebMvcProperties webMvcProperties;

    @Bean
    public OAuthHelper authHelper(){
        return new OAuthHelper();
    }

    public CessTestAutoConfiguration(WebApplicationContext context,
                             WebMvcProperties webMvcProperties) {
        this.context = context;
        this.webMvcProperties = webMvcProperties;
    }

    @Bean
//    @ConditionalOnMissingBean(MockMvcBuilder.class)
    public DefaultMockMvcBuilder mockMvcBuilder(
            List<MockMvcBuilderCustomizer> customizers) {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");

        requestBuilder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {

                Map<String,String> headers = MockMvcs.getHttpHeader();

                if(headers != null){
                    for(Map.Entry<String,String> header : headers.entrySet()){
                        request.addHeader(header.getKey(),header.getValue());
                    }
                }

                AuthInfo authInfo = MockMvcs.getAuthInfo();
                if(authInfo != null) {
                    request.addHeader("Authorization", authHelper().getAuthorization(authInfo));
                }
                return request;
            }
        });

        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.context);
        builder.addDispatcherServletCustomizer(
                new MockMvcDispatcherServletCustomizer(this.webMvcProperties));
        for (MockMvcBuilderCustomizer customizer : customizers) {
            customizer.customize(builder);
        }
        builder.defaultRequest(requestBuilder);
        return builder;
    }
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.test.mockmvc")
//    public SpringBootMockMvcBuilderCustomizer springBootMockMvcBuilderCustomizer() {
//        return new SpringBootMockMvcBuilderCustomizer(this.context);
//    }

    @Bean
//    @ConditionalOnMissingBean
    public MockMvc mockMvc(MockMvcBuilder builder) {
        MockMvc mockMvc = builder.build();

        return mockMvc;
//        mockMvc.s
    }

    private static class MockMvcDispatcherServletCustomizer
            implements DispatcherServletCustomizer {

        private final WebMvcProperties webMvcProperties;

        MockMvcDispatcherServletCustomizer(WebMvcProperties webMvcProperties) {
            this.webMvcProperties = webMvcProperties;
        }

        @Override
        public void customize(DispatcherServlet dispatcherServlet) {
            dispatcherServlet.setDispatchOptionsRequest(
                    this.webMvcProperties.isDispatchOptionsRequest());
            dispatcherServlet.setDispatchTraceRequest(
                    this.webMvcProperties.isDispatchTraceRequest());
            dispatcherServlet.setThrowExceptionIfNoHandlerFound(
                    this.webMvcProperties.isThrowExceptionIfNoHandlerFound());
        }

    }
}
