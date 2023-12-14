package com.example.demo.src.wishlist;

import com.example.demo.src.wishlist.model.GetWishlistRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class WishlistDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetWishlistRes> getWishlists(int page, Long memberID) {
        String getWishlistsQuery = "select * from InterestProduct where status = 'ACTIVE' and memberID = ? order by interestID limit 5 offset ?";
        Long getWishlistsParams = memberID;

        return this.jdbcTemplate.query(getWishlistsQuery,
                (rs, rowNum) -> new GetWishlistRes(rs.getLong("productID"),
                                                    rs.getLong("memberID")),
                        getWishlistsParams
        );

    }

    public String findByMemberID(Long memberID) {
        String findByMemberIDQuery = "select status from InterestProduct where status = 'ACTIVE' and memberID = ?";
        Long findByMemberIDParams = memberID;

        return this.jdbcTemplate.queryForObject(findByMemberIDQuery,
                (rs, rowNum) -> new String(rs.getString("status")),
                findByMemberIDParams
        );
    }

    public int deleteWishlistByMemberID(Long memberID) {
        String deleteWishlistByMemberIDQuery = "update InterestProduct set status = 'INACTIVE' where memberID = ?";
        Long deleteWishlistByMemberIDParams = memberID;

        return this.jdbcTemplate.update(deleteWishlistByMemberIDQuery, deleteWishlistByMemberIDParams);
    }

    public int deleteWishlistByProductID(Long productID) {
        String deleteWishlistByProductIDQuery = "update InterestProduct set status = 'INACTIVE' where productID = ?";
        Long deleteWishlistByProductIDParams = productID;

        return this.jdbcTemplate.update(deleteWishlistByProductIDQuery, deleteWishlistByProductIDParams);
    }
}
