package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.product.Model.PatchProductReq;
import com.example.demo.src.product.Model.PostProductReq;
import com.example.demo.src.product.Model.PostProductRes;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static com.example.demo.config.BaseResponseStatus.*;

@Slf4j
@Service
public class ProductService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductDao productDao;
    private final ProductProvider productProvider;


    @Autowired
    public ProductService(ProductDao productDao, ProductProvider productProvider) {
        this.productDao = productDao;
        this.productProvider = productProvider;
    }
    // DB에 생성은 잘 되지만 productID sout으로 로그 찍어봐도 안뜨고, 500 db연결에러 뜸
    public PostProductRes createProduct(PostProductReq postProductReq) throws BaseException {
        try {
            int productID = productDao.createProduct(postProductReq);
            PostProductRes postProductRes = new PostProductRes(productID);
            return postProductRes;
        } catch (Exception exception) {
            log.error(Arrays.toString(exception.getStackTrace()));
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyPrice(PatchProductReq patchProductReq) throws BaseException {
        try {
            int result = productDao.modifyPrice(patchProductReq);
            if(result == 0) {
                throw new BaseException(MODIFY_FAIL_PRICE);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteProduct(Long productID) throws BaseException {
        try {
            int result = productDao.deleteProduct(productID);
            if (result == 0) {
                throw new BaseException(DELETE_FAIL_PRODUCT);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
