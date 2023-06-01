package com.example.demo.src.product.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductRes {
    private Long productID;
    private Long sellerID;
    private String postTitle;
    private Long price;
    private Long discountPrice;
    private String postContents;
    private Long categoryID;
    private Long dealStatusID;
    private String productImage;

}
