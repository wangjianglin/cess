package io.cess.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.*;
import java.util.regex.Pattern;

@ConfigurationProperties("zuul")
public class ZuulExtProperties {

    private Map<String, ZuulRoute> routes = new LinkedHashMap<>();

    public Map<String, ZuulRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(Map<String, ZuulRoute> routes) {
        this.routes = routes;
    }

    public static class ZuulRoute{

        private String prefix;

        private String ignorePrefixs;

        private java.util.regex.Pattern[] ignorePatterns;

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getIgnorePrefixs() {
            return ignorePrefixs;
        }

        public void setIgnorePrefixs(String ignorePrefixs) {
            this.ignorePrefixs = ignorePrefixs;
            if(this.ignorePrefixs == null || "".equals(this.ignorePrefixs.trim())){
                ignorePatterns = new Pattern[0];
                return;
            }
            String[] strs = this.ignorePrefixs.split(",");

            List<Pattern> list = new ArrayList<>();

            Pattern pattern = null;
            for(String str : strs){
                pattern = Pattern.compile(str.trim());
                list.add(pattern);
            }

            ignorePatterns = list.toArray(new Pattern[]{});
        }

        public Pattern[] getPatterns() {
            return ignorePatterns;
//            return Arrays.copyOf(this.patterns,this.patterns.length);
        }
    }
}
