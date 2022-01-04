package com.example.fuzzylogic.model;
import com.example.fuzzylogic.model.enums.Load;
import com.example.fuzzylogic.model.enums.Speed;
import com.example.fuzzylogic.model.enums.Temperature;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class ComponentEfficient {
    private int id;
    private Map<LocalDate, Integer> efficientMap;
    private Speed speed;
    private Load load;
    private Temperature temperature;
}
