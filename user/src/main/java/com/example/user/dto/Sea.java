package com.example.user.dto;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;


@Component
// 1. 设置作用域为 prototype
// 2. 设置代理模式，TARGET_CLASS 表示使用 CGLIB 生成子类代理
@Scope(scopeName = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Sea {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Sea{" +
                "name='" + name + '\'' +
                '}';
    }
}
