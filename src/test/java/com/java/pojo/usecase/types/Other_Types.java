package com.java.pojo.usecase.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Other_Types {

    private UUID uuid;
    private MyEnum enum_; // Use a specific enum type
    private String string;

    public enum MyEnum {
        VALUE_ONE,
        VALUE_TWO,
        VALUE_THREE
    }
}
