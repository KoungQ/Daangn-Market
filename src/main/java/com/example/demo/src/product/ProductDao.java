package com.example.demo.src.product;

import com.example.demo.src.product.Model.GetProductRes;
import com.example.demo.src.product.Model.PatchProductReq;
import com.example.demo.src.product.Model.PostProductReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetProductRes> getProducts(int page) {
        String getProductsQuery = "select * from Product where Product.status = 'ACTIVE' order by productID limit 5 offset ?";
        int getProductsParams = page * 5;
        return this.jdbcTemplate.query(getProductsQuery,
                (rs, rowNum) -> new GetProductRes(
                        rs.getLong("productID"),
                        rs.getLong("sellerID"),
                        rs.getString("postTitle"),
                        rs.getLong("price"),
                        rs.getLong("discountPrice"),
                        rs.getString("postContents"),
                        rs.getLong("categoryID"),
                        rs.getLong("dealStatusID"),
                        rs.getString("productImage")),
                getProductsParams
        );
    }

    public List<GetProductRes> getProductsByTitle(String postTitle, int page) {
        String getProductsByTitleQuery = "select * from Product where postTitle = ? and Product.status = 'ACTIVE' order by productID limit 5 offset ?";
        Object[] getProductsByTitleParams = new Object[] {postTitle, page * 5};
        return this.jdbcTemplate.query(getProductsByTitleQuery,
                (rs, rowNum) -> new GetProductRes(
                        rs.getLong("productID"),
                        rs.getLong("sellerID"),
                        rs.getString("postTitle"),
                        rs.getLong("price"),
                        rs.getLong("discountPrice"),
                        rs.getString("postContents"),
                        rs.getLong("categoryID"),
                        rs.getLong("dealStatusID"),
                        rs.getString("productImage")),
                getProductsByTitleParams);
    }

    public List<GetProductRes> getProducts(Long sellerID, int page) {
        String getProductQuery = "select * from Product where sellerID = ? and Product.status = 'ACTIVE' order by productID limit 5 offset ?";
        Object[] getProductParams = new Object[] {sellerID, page * 5};
        return this.jdbcTemplate.query(getProductQuery,
                (rs, rowNum) -> new GetProductRes(
                        rs.getLong("productID"),
                        rs.getLong("sellerID"),
                        rs.getString("postTitle"),
                        rs.getLong("price"),
                        rs.getLong("discountPrice"),
                        rs.getString("postContents"),
                        rs.getLong("categoryID"),
                        rs.getLong("dealStatusID"),
                        rs.getString("productImage")),
                getProductParams);
    }

    public int createProduct(PostProductReq postProductReq) {
        String createProductQuery;
        Object[] createProductParams;
        // 할인 금액이 명시되어 있지 않을 경우, discountPrice = 0, canBargain = 1(F)
        createProductQuery = "insert into Product(productID, sellerID," +
        "postTitle, price, discountPrice, postContents, categoryID," +
        "dealStatusID, productImage, views, canBargain) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        createProductParams = new Object[] {
                postProductReq.getProductID(),
                postProductReq.getSellerID(),
                postProductReq.getPostTitle(),
                postProductReq.getPrice(),
                postProductReq.getDiscountPrice(),
                postProductReq.getPostContents(),
                postProductReq.getCategoryID(),
                postProductReq.getDealStatusID(),
                postProductReq.getProductImage(),
                0,
                postProductReq.isCanBargain()
        };

        this.jdbcTemplate.update(createProductQuery, createProductParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int modifyPrice(PatchProductReq patchProductReq) {
        String modifyPriceQuery = "update Product set price = ? where productID = ?";
        Object[] modifyPriceParams = new Object[] {patchProductReq.getPrice(), patchProductReq.getProductID()};

        return this.jdbcTemplate.update(modifyPriceQuery, modifyPriceParams);
    }

    public String findByProductID(Long productID) {
        String findByProductIDQuery = "select status from Product where productID = ?";
        Long findByProductIDParams = productID;
        return this.jdbcTemplate.queryForObject(findByProductIDQuery,
                (rs, rowNum) -> new String(rs.getString("status")), findByProductIDParams);
    }

    public int deleteProduct(Long productID) {
        String deleteProductQuery = "update Product set status = 'INACTIVE' where productID = ?";
        Object[] deleteProductParams = new Object[] {productID};

        return this.jdbcTemplate.update(deleteProductQuery, deleteProductParams);
    }
}
