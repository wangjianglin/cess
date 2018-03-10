package io.cess.test;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sound.sampled.CompoundControl;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


public class CessRunner extends SpringJUnit4ClassRunner {
    /**
     * Construct a new {@code SpringRunner} and initialize a
     * {@link TestContextManager TestContextManager}
     * to provide Spring testing functionality to standard JUnit 4 tests.
     *
     * @param clazz the test class to be run
     * @see #createTestContextManager(Class)
     */
    public CessRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    protected Statement withBefores(FrameworkMethod frameworkMethod, Object testInstance, Statement statement) {

        MockMvcs.setAuthInfo(getAuthInfo(frameworkMethod));

        MockMvcs.setHttpHeader(getHttpHeaders(frameworkMethod));

        MockMvcs.setResultType(getResultType(frameworkMethod));

        MockMvcs.setErrors(getErrors(frameworkMethod));

        statement = super.withBefores(frameworkMethod, testInstance, statement);


        return statement;
    }

    @Override
    public void run(RunNotifier notifier) {
        super.run(notifier);
        MockMvcs.setAuthInfo(null);
        MockMvcs.setHttpHeader(null);
        MockMvcs.setErrors(null);
        MockMvcs.setResultType(null);
    }

    public int[] getErrors(FrameworkMethod frameworkMethod){
        ErrorCode errorCode = frameworkMethod.getAnnotation(ErrorCode.class);
        if(errorCode == null){
            return null;
        }
        return errorCode.value();
    }

    public Type getResultType(FrameworkMethod frameworkMethod) {

        ResultType resultType = frameworkMethod.getAnnotation(ResultType.class);

        if (resultType == null) {
            return null;
        }

        final Class<?>[] ptypes = resultType.parameterizedType();
        if (ptypes == null || ptypes.length == 0) {
            return resultType.value();
        }
        return new ParameterizedType() {

            @Override
            public Type[] getActualTypeArguments() {
                return ptypes;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }

            @Override
            public Type getRawType() {
                return resultType.value();
            }
        };
    }

    private Map<String,String> getHttpHeaders(FrameworkMethod frameworkMethod){

        HttpHeaders httpHeaders = frameworkMethod.getAnnotation(HttpHeaders.class);

        Map<String,String> map = toHttpHeaders(httpHeaders);

        map.putAll(toHttpHeaders(this.getTestClass().getAnnotation(HttpHeaders.class)));

        return map;
    }

    private Map<String,String> toHttpHeaders(HttpHeaders httpHeaders){
        Map<String,String> map = new HashMap<>();

        if(httpHeaders == null || httpHeaders.value() == null){
            return map;
        }

        for(HttpHeader header : httpHeaders.value()){
            map.put(header.name(),header.value());
        }

        return map;
    }




    private AuthInfo getAuthInfo(FrameworkMethod frameworkMethod){
        WithMockUser withMockUser = frameworkMethod.getAnnotation(WithMockUser.class);

        if(withMockUser == null){
            withMockUser = this.getTestClass().getAnnotation(WithMockUser.class);
        }

        AuthInfo authInfo = toAuthInfo(withMockUser);

        if(authInfo != null){
            return authInfo;
        }

        WithAnonymousUser withAnonymousUser = frameworkMethod.getAnnotation(WithAnonymousUser.class);
        if(withAnonymousUser == null){
            withAnonymousUser = this.getTestClass().getAnnotation(WithAnonymousUser.class);
        }

        if(withAnonymousUser != null){
            authInfo = new AuthInfo();
        }

        return authInfo;
    }

    public AuthInfo toAuthInfo(WithMockUser withMockUser){
        if(withMockUser == null){
            return  null;
        }

        AuthInfo authInfo = new AuthInfo();

        if(withMockUser.username() != null && !"".equals(withMockUser.username().trim())) {
            authInfo.setUsername(withMockUser.username());
        }else{
            authInfo.setUsername(withMockUser.value());
        }

        authInfo.setAuthorities(withMockUser.authorities());

        authInfo.setRoles(withMockUser.roles());

        return authInfo;
    }
}
