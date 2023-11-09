package com.dynamicrulesengine.dynamicrulesengine.contract.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private String productId;
    private int stockLevel;
}
