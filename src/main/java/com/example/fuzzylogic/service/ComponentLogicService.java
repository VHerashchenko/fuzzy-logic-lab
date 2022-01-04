package com.example.fuzzylogic.service;

import com.example.fuzzylogic.convertor.ComponentDtoToWrapper;
import com.example.fuzzylogic.model.ComponentDto;
import com.example.fuzzylogic.model.ComponentEfficient;
import com.example.fuzzylogic.model.ComponentWrapper;
import com.example.fuzzylogic.model.enums.Load;
import com.example.fuzzylogic.model.enums.Speed;
import com.example.fuzzylogic.model.enums.Temperature;
import com.example.fuzzylogic.repo.ComponentRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ComponentLogicService {

    private final ComponentDtoToWrapper componentDtoToWrapper;

    private final ModelMapper modelMapper;

    private final ComponentRepo componentRepo;

    public ComponentEfficient process(ComponentDto componentDto) {

        var componentWrapper = componentDtoToWrapper.convert(componentDto);

        if (componentWrapper.getSpeed().equals(Speed.HIGH)) {
            if (componentWrapper.getLoad().equals(Load.HIGH)) {
                componentWrapper.setTemperature(Temperature.CRITICAL);
            } else if (componentWrapper.getLoad().equals(Load.MIDDLE)) {
                componentWrapper.setTemperature(Temperature.HIGH);
            } else {
                componentWrapper.setTemperature(Temperature.MIDDLE);
            }
        } else if (componentWrapper.getSpeed().equals(Speed.NORMAL)) {
            if (componentWrapper.getLoad().equals(Load.HIGH)) {
                componentWrapper.setTemperature(Temperature.HIGH);
            } else if (componentWrapper.getLoad().equals(Load.MIDDLE)) {
                componentWrapper.setTemperature(Temperature.MIDDLE);
            } else {
                componentWrapper.setTemperature(Temperature.LOW);
            }
        } else {
            if (componentWrapper.getLoad().equals(Load.HIGH)) {
                componentWrapper.setTemperature(Temperature.MIDDLE);
            } else if (componentWrapper.getLoad().equals(Load.MIDDLE)) {
                componentWrapper.setTemperature(Temperature.LOW);
            } else {
                componentWrapper.setTemperature(Temperature.LOW);
            }
        }

        return calculate(componentWrapper);
    }

    private ComponentEfficient calculate(ComponentWrapper componentWrapper) {
        int efficient;
        int defaultDec = 8;
        int cycleTimes = 40;
        double temperatureCoefficient;
        int counter = 0;

        if (componentWrapper.getTemperature().equals(Temperature.LOW)) {
            temperatureCoefficient = 0.7;
        } else if (componentWrapper.getTemperature().equals(Temperature.MIDDLE)) {
            temperatureCoefficient = 1;
        } else if (componentWrapper.getTemperature().equals(Temperature.HIGH)) {
            temperatureCoefficient = 1.2;
        } else {
            temperatureCoefficient = 1.6;
        }

        if (componentWrapper.getLoad().equals(Load.HIGH)) {
            efficient = 100;
        } else if (componentWrapper.getLoad().equals(Load.MIDDLE)) {
            efficient = 90;
        } else {
            efficient = 80;
        }

        Map<LocalDate, Integer> efficientMap = new LinkedHashMap<>();

        var localDate = LocalDate.now();
        for (int i = 1; i <= cycleTimes; ++i) {
            efficientMap.put(localDate, efficient);
            if (efficient > 50) {
                localDate = localDate.plusWeeks(1);
                efficient = efficient - (int) (defaultDec * temperatureCoefficient);
            } else {
                ++counter;
                efficientMap.put(localDate.plusDays(1), 0);
                efficient = 80 - counter;
                localDate = localDate.plusDays(2);
            }
        }

        var componentEfficient = modelMapper.map(componentWrapper, ComponentEfficient.class);
        componentEfficient.setEfficientMap(efficientMap);

        return componentEfficient;
    }

    public ComponentEfficient addComponent(ComponentEfficient componentEfficient) {
        componentEfficient.setId(componentRepo.sizeList() + 1);
        componentRepo.addComponent(componentEfficient);
        return componentEfficient;
    }

    public ComponentEfficient getComponent(int index){
        return componentRepo.getComponent(index);
    }
}
