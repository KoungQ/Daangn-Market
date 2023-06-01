package com.example.demo.src.wishlist;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.wishlist.model.GetWishlistRes;
import jdk.internal.net.http.common.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Slf4j
public class WishlistProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WishlistDao wishlistDao;

    @Autowired
    public WishlistProvider(WishlistDao wishlistDao) {
        this.wishlistDao = wishlistDao;
    }

    @Transactional
    public List<GetWishlistRes> getWishlists(Long memberID) throws BaseException {
        try {
            List<GetWishlistRes> getWishlistRes = wishlistDao.getWishlists(memberID);
            return getWishlistRes;
        } catch(Exception exception) {
            log.error(exception.getMessage());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public boolean findByMemberID(Long memberID) throws BaseException {
        try {
            String isExist = wishlistDao.findByMemberID(memberID);
            System.out.println(isExist);
            if(Objects.equals(isExist, "INACTIVE"))
                return true;
            else
                return false;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
