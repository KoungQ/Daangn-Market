package com.example.demo.src.wishlist;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.product.ProductProvider;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Target;
import java.util.Objects;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.DELETE_FAIL_WISHLIST;

@Slf4j
@Service
public class WishlistService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WishlistDao wishlistDao;
    private final WishlistProvider wishlistProvider;

    @Autowired
    public WishlistService(WishlistDao wishlistDao, WishlistProvider wishlistProvider) {
        this.wishlistDao = wishlistDao;
        this.wishlistProvider = wishlistProvider;
    }

    @Transactional
    public void deleteWishlistByMemberID(Long memberID) throws BaseException {
        try {
            int result = wishlistDao.deleteWishlistByMemberID(memberID);
            if(result == 0)
                throw new BaseException(DELETE_FAIL_WISHLIST);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteWishlistByProductID(Long productID) throws BaseException {
        try {
            int result = wishlistDao.deleteWishlistByProductID(productID);
            if(result == 0)
                throw new BaseException(DELETE_FAIL_WISHLIST);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
