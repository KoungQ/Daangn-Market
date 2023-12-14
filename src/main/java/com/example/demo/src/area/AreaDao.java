package com.example.demo.src.area;

import com.example.demo.src.area.model.GetAreaRes;
import com.example.demo.src.area.model.PatchAreaReq;
import com.example.demo.src.area.model.PostAreaReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AreaDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetAreaRes> getAreas(int page) {
        String getAreasQuery = "select * from Area where status = 'ACTIVE' order by areaID limit 5 offset ?";
        int getAreasParams = page * 5;

        return this.jdbcTemplate.query(getAreasQuery,
                (rs, rowNum) -> new GetAreaRes(
                        rs.getLong("areaID"),
                        rs.getString("areaName")),
                getAreasParams
        );
    }

    public List<GetAreaRes> getAreaByMemberID (int page, Long memberID) {
        String getAreaByMemberIDQuery = "select * from Area where areaID in (select areaID from MemberArea where memberID = ?) and status = 'ACTIVE' order by areaID limit 5 offset ?";
        Object[] getAreasParams = new Object[] {memberID, page * 5};

        return this.jdbcTemplate.query(getAreaByMemberIDQuery,
                (rs, rowNum) -> new GetAreaRes(
                        rs.getLong("areaID"),
                        rs.getString("areaName")),
                getAreasParams
        );
    }

    public int modifyArea(PatchAreaReq patchAreaReq) {
        String modifyAreaQuery = "update MemberArea set areaID = ? where memberID = ?";
        Object[] modifyAreaParams = new Object[] {patchAreaReq.getAreaID(), patchAreaReq.getMemberID()};

        return this.jdbcTemplate.update(modifyAreaQuery, modifyAreaParams);
    }

    public int addArea(PostAreaReq postAreaReq) {
        String addAreaQuery = "insert into MemberArea(memberID, areaID) values (?, ?)";
        Object[] addAreaParams = new Object[] {postAreaReq.getMemberID(), postAreaReq.getAreaID()};

        this.jdbcTemplate.update(addAreaQuery, addAreaParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public int deleteAreaByMemberID(Long memberID) {
        String deleteAreaByMemberIDQuery = "";
        Object[] deleteAreaByMemberIDParams = new Object[] {memberID};

        return this.jdbcTemplate.update(deleteAreaByMemberIDQuery, deleteAreaByMemberIDParams);
    }
}
