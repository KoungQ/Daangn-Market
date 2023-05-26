package com.example.demo.src.product.Model;

import com.example.demo.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostProductReq extends BaseTimeEntity {
    private Long productID;
    private Long sellerID;
    private String postTitle;
    private Long price;
    private Long discountPrice;
    private String postContents;
    private Long categoryID;
    private Long dealStatusID;
    private String productImage;
    private String status;
    private boolean canBargain;


}
