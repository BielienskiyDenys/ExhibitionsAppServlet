package org.example.exhibitionsAppServlet.model.dao;

import org.example.exhibitionsAppServlet.model.entity.Exhibition;
import org.example.exhibitionsAppServlet.model.entity.enums.HallName;

import java.util.List;

public interface ExhibitionDao extends GenericDao<Exhibition> {
    List<Exhibition> findByNameLike(String exNameLike);
    List<Exhibition> findByThemeLike(String exNameLike);
    List<Exhibition> findByDatesBetween(String searchDateFrom, String searchDateTo);
    List<Exhibition> findByStatusId(int id);
    boolean addHallToExhibition(Long exhibitionId, HallName hallToAdd);
    boolean updateExpiredExhibitionsAndTicketsManually();
}
