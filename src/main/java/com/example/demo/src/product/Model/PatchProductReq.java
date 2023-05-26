package com.example.demo.src.product.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchProductReq {
    private final Long productID;
    private final Long price;
}
