package com.example.fuzzylogic.repo;

import com.example.fuzzylogic.model.ComponentEfficient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ComponentRepo {

    List<ComponentEfficient> componentEfficients = new ArrayList<>();

    public void addComponent(ComponentEfficient componentEfficient){
        componentEfficients.add(componentEfficient);
    }

    public ComponentEfficient getComponent(int id){
        ComponentEfficient component = new ComponentEfficient();

        for (ComponentEfficient com : componentEfficients){
            if(com.getId() == id){
                component = com;
            }
        }
        return component;
    }

    public int sizeList(){
        return componentEfficients.size();
    }
}
