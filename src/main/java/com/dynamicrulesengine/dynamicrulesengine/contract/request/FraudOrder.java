package com.dynamicrulesengine.dynamicrulesengine.contract.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FraudOrder {

        private String orderId;
        private Customer customer;
        private FraudOrderDetails order;

    }

