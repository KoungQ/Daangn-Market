package com.example.demo.src.category;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.category.model.PostCategoryReq;
import com.example.demo.src.category.model.PostCategoryRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.DELETE_FAIL_CATEGORY;

@Service
public class CategoryService {

    private final CategoryDao categoryDao;

    @Autowired
    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Transactional
    public PostCategoryRes createCategory(PostCategoryReq postCategoryReq) throws BaseException {
        try {
            int categoryID = categoryDao.createCategory(postCategoryReq);
            PostCategoryRes postCategoryRes = new PostCategoryRes(categoryID);
            return postCategoryRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteCategory(Long categoryID) throws BaseException {
        try {
            int result = categoryDao.deleteCategory(categoryID);
            if(result == 0) {
                throw new BaseException(DELETE_FAIL_CATEGORY);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
