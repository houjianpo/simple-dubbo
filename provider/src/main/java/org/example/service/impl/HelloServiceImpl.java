package org.example.service.impl;

import org.example.api.HelloService;

/**
 * provider
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
