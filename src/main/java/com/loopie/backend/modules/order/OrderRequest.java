package com.loopie.backend.modules.order;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private Double total;
    private List<OrderItemRequest> items;
}
