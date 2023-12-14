package com.example.demo.src.category;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.category.model.GetCategoryRes;
import com.example.demo.src.category.model.PostCategoryReq;
import com.example.demo.src.category.model.PostCategoryRes;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.POST_CATEGORY_EMPTY_CATEGORYNAME;

@RestController
@Profile("prod")
@RequestMapping("/app/category")
public class CategoryController {

    @Autowired
    private final CategoryProvider categoryProvider;
    @Autowired
    private final CategoryService categoryService;

    public CategoryController(CategoryProvider categoryProvider, CategoryService categoryService) {
        this.categoryProvider = categoryProvider;
        this.categoryService = categoryService;
    }

    // 카테고리 목록 조회
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetCategoryRes>> getCategories(@RequestParam(value = "page", defaultValue = "0") int page) {
        try {
            List<GetCategoryRes> categoryRes = categoryProvider.getCategories(page);
            return new BaseResponse<>(categoryRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 카테고리 생성
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostCategoryRes> createCategory(@RequestBody PostCategoryReq postCategoryReq) {
        if(postCategoryReq.getCategoryName() == null) {
            return new BaseResponse<>(POST_CATEGORY_EMPTY_CATEGORYNAME);
        }
        try {
            PostCategoryRes postCategoryRes = categoryService.createCategory(postCategoryReq);
            return new BaseResponse<>(postCategoryRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 카테고리 삭제
    @ResponseBody
    @PatchMapping("")
    public BaseResponse<String> deleteCategory(@RequestParam Long categoryID) {
        try {
            categoryService.deleteCategory(categoryID);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
