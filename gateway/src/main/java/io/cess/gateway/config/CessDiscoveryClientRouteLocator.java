package io.cess.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;

import java.util.regex.Pattern;

public class CessDiscoveryClientRouteLocator extends DiscoveryClientRouteLocator {
    public CessDiscoveryClientRouteLocator(String servletPath, DiscoveryClient discovery, ZuulProperties properties, ServiceInstance localServiceInstance) {
        super(servletPath, discovery, properties, localServiceInstance);
    }

    public CessDiscoveryClientRouteLocator(String servletPath, DiscoveryClient discovery, ZuulProperties properties, ServiceRouteMapper serviceRouteMapper, ServiceInstance localServiceInstance) {
        super(servletPath, discovery, properties, serviceRouteMapper, localServiceInstance);
    }

    @Autowired
    private ZuulExtProperties properties;

    @Override
    public Route getMatchingRoute(String path) {
        Route route = super.getMatchingRoute(path);

//        if ("/v2/api-docs".equals(route.getPath())) {
//            return route;
//        }

        if (isPrefix(route.getId(),route.getPath())) {
            ZuulExtProperties.ZuulRoute zuulRoute = properties.getRoutes().get(route.getId());
            route.setPath(("/" + zuulRoute.getPrefix().trim() + "/" + route.getPath()).replaceAll("//", "/"));
        }

        return route;
    }

    private boolean isPrefix(String id,String path){
        ZuulExtProperties.ZuulRoute zuulRoute = properties.getRoutes().get(id);

        if (zuulRoute.getPrefix() != null && !"".equals(zuulRoute.getPrefix().trim())) {

            if(zuulRoute.getPatterns() != null) {
                for (Pattern pattern : zuulRoute.getPatterns()) {
                    if (pattern.matcher(path).matches()) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

}
