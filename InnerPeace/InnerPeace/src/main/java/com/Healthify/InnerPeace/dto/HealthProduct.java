package com.Healthify.InnerPeace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HealthProduct {



    private int productId;
    private String name;
    private int qty;
    private double price;
}
