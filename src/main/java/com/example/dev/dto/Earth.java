package com.example.dev.dto;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;


@Component
public class Earth {
    private String area;

    @Resource
    private Sea sea;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Sea getSea() {
        return sea;
    }

    @Override
    public String toString() {
        return "Earth{" +
                "area='" + area + '\'' +
                ", sea=" + sea +
                '}';
    }
}
