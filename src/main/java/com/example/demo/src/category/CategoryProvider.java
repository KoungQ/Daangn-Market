package com.example.demo.src.category;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.category.model.GetCategoryRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Slf4j
@Service
public class CategoryProvider {

    private final CategoryDao categoryDao;

    @Autowired
    public CategoryProvider(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Transactional
    public List<GetCategoryRes> getCategories(int page) throws BaseException {
        try {
            List<GetCategoryRes> categoryRes = categoryDao.getCategories(page);
            return categoryRes;
        } catch (Exception exception) {
            log.error(Arrays.toString(exception.getStackTrace()));
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
