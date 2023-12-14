package com.example.demo.src.category;

import com.example.demo.src.category.model.GetCategoryRes;
import com.example.demo.src.category.model.PostCategoryReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CategoryDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetCategoryRes> getCategories(int page) {
        String getCategoriesQuery = "select * from Category where status = 'ACTIVE' order by categoryID limit 5 offset ?";
        int getCategoriesParams = page;

        return this.jdbcTemplate.query(getCategoriesQuery,
                (rs, rowNum) -> new GetCategoryRes(
                        rs.getLong("categoryID"),
                        rs.getString("categoryName")),
                getCategoriesParams
        );
    }

    public int createCategory(PostCategoryReq postCategoryReq) {
        String createCategoryQuery = "insert into Category(categoryName) values (?)";
        String createCategoryParams = postCategoryReq.getCategoryName();

        this.jdbcTemplate.update(createCategoryQuery, createCategoryParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public int deleteCategory(Long categoryID) {
        String deleteCategoryQuery = "update Category set status = 'INACTIVE' where categoryID = ?";
        Long deleteCategoryParams = categoryID;

        return this.jdbcTemplate.update(deleteCategoryQuery, deleteCategoryParams);
    }
}
