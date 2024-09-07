package com.aline.aline.CommonEntitiesObjects;

import com.aline.aline.enums.Currencies;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    private Currencies currency;
    private double price;
}
