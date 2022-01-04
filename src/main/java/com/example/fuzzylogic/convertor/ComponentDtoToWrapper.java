package com.example.fuzzylogic.convertor;

import com.example.fuzzylogic.model.ComponentDto;
import com.example.fuzzylogic.model.ComponentWrapper;
import com.example.fuzzylogic.model.enums.Load;
import com.example.fuzzylogic.model.enums.Speed;
import org.springframework.stereotype.Component;

@Component
public class ComponentDtoToWrapper {
    public ComponentWrapper convert(ComponentDto componentDto){
        var componentWrapper = new ComponentWrapper();

        if(componentDto.getLoad() >= 70){
            componentWrapper.setLoad(Load.HIGH);
        }
        else if (componentDto.getLoad() >= 40) {
            componentWrapper.setLoad(Load.MIDDLE);
        }
        else {
            componentWrapper.setLoad(Load.LOW);
        }

        if(componentDto.getWorkSpeed() >= 70){
            componentWrapper.setSpeed(Speed.HIGH);
        }
        else if (componentDto.getWorkSpeed() >= 40) {
            componentWrapper.setSpeed(Speed.NORMAL);
        }
        else {
            componentWrapper.setSpeed(Speed.LOW);
        }
        return componentWrapper;
    }
}
