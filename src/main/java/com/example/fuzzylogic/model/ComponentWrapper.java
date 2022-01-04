package com.example.fuzzylogic.model;

import com.example.fuzzylogic.model.enums.Load;
import com.example.fuzzylogic.model.enums.Speed;
import com.example.fuzzylogic.model.enums.Temperature;
import lombok.Data;

@Data
public class ComponentWrapper {
    private Load load;

    private Speed speed;

    private Temperature temperature;

    private int efficient;
}
