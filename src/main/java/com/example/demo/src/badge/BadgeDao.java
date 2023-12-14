package com.example.demo.src.badge;

import com.example.demo.src.badge.model.GetBadgeRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BadgeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetBadgeRes> getBadges(int page) {
        String getBadgesQuery = "select * from Badge where status = 'ACTIVE' order by badgeID limit 5 offset ?";
        int getBadgesParams = page;

        return this.jdbcTemplate.query(getBadgesQuery,
                (rs, rowNum) -> new GetBadgeRes(
                        rs.getLong("badgeID"),
                        rs.getString("badgeName"),
                        rs.getBlob("icon"),
                        rs.getString("badgeInfo")),
                getBadgesParams
        );
    }

    public List<GetBadgeRes> getBadgesByMemberID(int page, Long memberID) {
        String getBadgesByMemberIDQuery = "";
        Object[] getBadgesByMemberIDParams = new Object[] {page, memberID};

        return this.jdbcTemplate.query(getBadgesByMemberIDQuery,
                (rs, rowNum) -> new GetBadgeRes(
                        rs.getLong("badgeID"),
                        rs.getString("badgeName"),
                        rs.getBlob("icon"),
                        rs.getString("badgeInfo")),
                getBadgesByMemberIDParams
        );
    }
}
