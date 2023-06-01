package com.example.demo.src.wishlist;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.wishlist.model.GetWishlistRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DELETE_INVALID_WISHLIST;
import static com.example.demo.config.BaseResponseStatus.USERS_EMPTY_USER_ID;

@RestController
@Profile("prod")
@RequestMapping("/app/wishlists")
public class WishlistController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final WishlistProvider wishListProvider;
    @Autowired
    private final WishlistService wishListService;

    public WishlistController(WishlistProvider wishListProvider, WishlistService wishListService) {
        this.wishListProvider = wishListProvider;
        this.wishListService = wishListService;
    }

    @ResponseBody
    @GetMapping("{memberID}")
    public BaseResponse<List<GetWishlistRes>> getWishlists(@PathVariable("memberID") Long memberID) {
        if(memberID == null) {
            return new BaseResponse<>(USERS_EMPTY_USER_ID);
        }

        try {
            List<GetWishlistRes> getWishlistRes = wishListProvider.getWishlists(memberID);
            return new BaseResponse<>(getWishlistRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/delete")
    public BaseResponse<String> deleteWishlistByMemberID(@RequestParam Long memberID) {
        if(memberID == null) {
            return new BaseResponse<>(USERS_EMPTY_USER_ID);
        }

        try {
            if(wishListProvider.findByMemberID(memberID)) {
                return new BaseResponse<>(DELETE_INVALID_WISHLIST);
            }

            wishListService.deleteWishlistByMemberID(memberID);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
