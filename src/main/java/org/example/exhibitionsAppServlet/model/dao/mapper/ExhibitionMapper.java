package org.example.exhibitionsAppServlet.model.dao.mapper;

import org.example.exhibitionsAppServlet.model.entity.Exhibition;
import org.example.exhibitionsAppServlet.model.entity.enums.HallName;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ExhibitionMapper implements ObjectMapper<Exhibition> {
    @Override
    public Exhibition extractFromResultSet(ResultSet rs) throws SQLException {
        Exhibition exhibition = new Exhibition();
        exhibition.setId(rs.getLong("exhibition.id"));
        exhibition.setExName(rs.getString("exhibition.ex_name"));
        exhibition.setStartDate(rs.getDate("exhibition.start_date"));
        exhibition.setEndDate(rs.getDate("exhibition.end_date"));
        exhibition.setOpenTime(rs.getString("exhibition.open_time"));
        exhibition.setCloseTime(rs.getString("exhibition.close_time"));
        exhibition.setDescription(rs.getString("exhibition.description"));
        exhibition.setPrice(rs.getLong("exhibition.price"));
        exhibition.setThemes(rs.getString("exhibition.themes"));
        exhibition.setExhibitionStatus(rs.getInt("exhibition.exhibition_status_id"));
        exhibition.addHall(rs.getInt("exhibition_has_hall.hall_id"));
        return exhibition;
    }

    @Override
    public Exhibition makeUnique(Map<Long, Exhibition> cache, Exhibition exhibition) {
        if(cache.containsKey(exhibition.getId())){
            Exhibition exhibitionOld = cache.get(exhibition.getId());
            for (HallName hn: exhibition.getHallNameList()){
                if (!exhibitionOld.getHallNameList().contains(hn)) {
                    exhibitionOld.getHallNameList().add(hn);
                }
            }
        }

        cache.putIfAbsent(exhibition.getId(), exhibition);
        return cache.get(exhibition.getId());
    }
}
