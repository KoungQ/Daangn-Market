package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.product.Model.GetProductRes;
import com.example.demo.src.product.Model.PatchProductReq;
import com.example.demo.src.product.Model.PostProductReq;
import com.example.demo.src.product.Model.PostProductRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@Profile("prod")
@RequestMapping("/app/products")
public class ProductController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ProductProvider productProvider;
    @Autowired
    private final ProductService productService;


    public ProductController(ProductProvider productProvider, ProductService productService) {
        this.productProvider = productProvider;
        this.productService = productService;
    }

    /**
     * 게시글 조회 API
     * [GET] /products
     * 게시글 제목으로 조회 API
     * [GET] /product? postTitle=
     * @return BaseResponse<List<GetProductRes>>
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetProductRes>> getProducts(@RequestParam(value="page", defaultValue = "0") int page, @RequestParam(required = false) String postTitle) {
        try {
            if(postTitle == null) {
                List<GetProductRes> getProductRes = productProvider.getProducts(page);
                return new BaseResponse<>(getProductRes);
            }
            List<GetProductRes> getProductRes = productProvider.getProductsByTitle(postTitle, page);
            return new BaseResponse<>(getProductRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 회원ID로 게시글 조회 API
     * [GET] /products/:userIdx
     * @return BaseResponse<GetProductRes>
     */
    @ResponseBody
    @GetMapping("/{sellerID}")
    public BaseResponse<List<GetProductRes>> getProducts(@RequestParam(value="page", defaultValue = "0") int page, @PathVariable("sellerID") Long sellerID) {
        if(sellerID == null) {
            return new BaseResponse<>(USERS_EMPTY_USER_ID);
        }
        try {
            List<GetProductRes> getProductRes = productProvider.getProducts(sellerID, page);
            return new BaseResponse<>(getProductRes);
        } catch(BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 게시글 작성 API
     * [POST] /products
     * @return BaseResponse<PostProductRes>
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostProductRes> createProduct(@RequestBody PostProductReq postProductReq) {
        if(postProductReq.getPostTitle() == null) {
            return new BaseResponse<>(PRODUCTS_EMPTY_TITLE);
        }
        if(postProductReq.getPrice() == null) {
            return new BaseResponse<>(PRODUCTS_EMPTY_PRICE);
        }
        if(postProductReq.getPostContents() == null) {
            return new BaseResponse<>(PRODUCTS_EMPTY_CONTENTS);
        }
        if(postProductReq.getCategoryID() == null) {
            return new BaseResponse<>(PRODUCTS_EMPTY_CATEGORY);
        }

        try {
            PostProductRes postProductRes = productService.createProduct(postProductReq);
            return new BaseResponse<>(postProductRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/{productID}")
    public BaseResponse<String> modifyPrice(@PathVariable("productID") Long productID, @RequestParam Long price) {
        if(price == null) {
            return new BaseResponse<>(PRODUCTS_EMPTY_PRICE);
        }

        try {
            PatchProductReq patchProductReq = new PatchProductReq(productID, price);
            productService.modifyPrice(patchProductReq);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/delete/{productID}")
    public BaseResponse<String> deleteProduct(@PathVariable("productID") Long productID) {
        try {
            // 삭제하려는 글이 유효한지 확인
            if(productProvider.findByProductID(productID)) {
                return new BaseResponse<>(DELETE_INVALID_PRODUCT);
            }
            // 삭제
            productService.deleteProduct(productID);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
