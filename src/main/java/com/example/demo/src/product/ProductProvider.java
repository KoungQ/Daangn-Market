package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.product.Model.GetProductRes;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.demo.config.BaseResponseStatus.*;

@Slf4j
@Service
public class ProductProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductDao productDao;

    @Autowired
    public ProductProvider(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public List<GetProductRes> getProducts(int page) throws BaseException {
        try {
            List<GetProductRes> getProductRes = productDao.getProducts(page);
            return getProductRes;
        } catch (Exception exception) {
            logger.error("Error!", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public List<GetProductRes> getProductsByTitle(String postTitle, int page) throws BaseException {
        try {
            List<GetProductRes> getProductRes = productDao.getProductsByTitle(postTitle, page);
            return getProductRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public List<GetProductRes> getProducts(Long sellerID, int page) throws BaseException {
        try {
            List<GetProductRes> getProductRes = productDao.getProducts(sellerID, page);
            return getProductRes;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public boolean findByProductID(Long productID) throws BaseException {
        try {
            String isExist = productDao.findByProductID(productID);
            if(Objects.equals(isExist, "INACTIVE"))
                return true;
            else
                return false;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
