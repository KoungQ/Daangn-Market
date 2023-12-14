package com.example.demo.src.badge;

import com.example.demo.src.badge.model.GetBadgeRes;
import com.example.demo.src.badge.model.PostBadgeReq;
import com.example.demo.src.badge.model.PostBadgeRes;
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
                        rs.getString("icon"),
                        rs.getString("badgeInfo")),
                getBadgesParams
        );
    }

    public List<GetBadgeRes> getBadgesByMemberID(int page, Long memberID) {
        String getBadgesByMemberIDQuery = "select * from Badge where status = 'ACTIVE' and Badge.badgeID IN (select MemberBadge.badgeID from MemberBadge where memberID = ?) order by badgeID limit 5 offset ?";
        Object[] getBadgesByMemberIDParams = new Object[] {memberID, page};

        return this.jdbcTemplate.query(getBadgesByMemberIDQuery,
                (rs, rowNum) -> new GetBadgeRes(
                        rs.getLong("badgeID"),
                        rs.getString("badgeName"),
                        rs.getString("icon"),
                        rs.getString("badgeInfo")),
                getBadgesByMemberIDParams
        );
    }

    public List<GetBadgeRes> getBadgeByBadgeID(Long badgeID) {
        String getBadgeByBadgeIDQuery = "select * from Badge where status = 'ACTIVE' and Badge.badgeID IN (select MemberBadge.badgeID from MemberBadge where memberID = ?)";
        Long getBadgeByBadgeIDParams = badgeID;

        return this.jdbcTemplate.query(getBadgeByBadgeIDQuery,
                (rs, rowNum) -> new GetBadgeRes(
                        rs.getLong("badgeID"),
                        rs.getString("badgeName"),
                        rs.getString("icon"),
                        rs.getString("badgeInfo")),
                getBadgeByBadgeIDParams
        );
    }

    public int addBadge(PostBadgeReq postBadgeReq) {
        String addBadgeQuery = "insert into MemberBadge(memberID, badgeID) values (?, ?)";
        Object[] addBadgeParams = new Object[] {postBadgeReq.getMemberID(), postBadgeReq.getBadgeID()};

        this.jdbcTemplate.update(addBadgeQuery, addBadgeParams);

        String lastInsertQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertQuery, int.class);
    }

    public int deleteBadgeByBadgeID(Long memberID) {
        String deleteBadgeByBadgeIDQuery = "update MemberBadge set status = 'INACTIVE' where memberID = ?";
        Object[] deleteBadgeByBadgeIDParams = new Object[] {memberID};

        return this.jdbcTemplate.update(deleteBadgeByBadgeIDQuery, deleteBadgeByBadgeIDParams);
    }
}
