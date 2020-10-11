package org.example.exhibitionsAppServlet.model.service;

import org.example.exhibitionsAppServlet.model.dao.DaoFactory;
import org.example.exhibitionsAppServlet.model.dao.ExhibitionDao;
import org.example.exhibitionsAppServlet.model.entity.Exhibition;
import org.example.exhibitionsAppServlet.model.entity.dto.ExhibitionDTO;
import org.example.exhibitionsAppServlet.model.entity.enums.HallName;

import java.util.List;
import java.util.Optional;

public class ExhibitionService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Exhibition> getAllExhibitions() {
        try (ExhibitionDao dao = daoFactory.createExhibitionDao()){
            return dao.findAll();
        }
    }

    public Optional<Exhibition> getExhibitionById(int id) {
        try (ExhibitionDao dao = daoFactory.createExhibitionDao()){
            return dao.findByID(id);
        }
    }

    public List<Exhibition> getExhibitionsByName(String exhibitionName) {
            try(ExhibitionDao dao = daoFactory.createExhibitionDao()) {
                return dao.findByNameLike(exhibitionName);
            }
    }

    public List<Exhibition> getExhibitionsBetweenDates(String searchStart, String searchEnd) {
        try(ExhibitionDao dao = daoFactory.createExhibitionDao()) {
            return dao.findByDatesBetween(searchStart, searchEnd);
        }
    }

    public List<Exhibition> getExhibitionsByTheme(String theme) {
        try(ExhibitionDao dao = daoFactory.createExhibitionDao()) {
            return dao.findByThemeLike(theme);
        }
    }

    public List<Exhibition> getExhibitionsByStatus(int status) {
        try(ExhibitionDao dao = daoFactory.createExhibitionDao()) {
            return dao.findByStatusId(status);
        }
    }

    public boolean createNewExhibition(ExhibitionDTO exhibitionDTO){
        Exhibition exhibition = new Exhibition(exhibitionDTO);
        boolean result;
        try(ExhibitionDao dao = daoFactory.createExhibitionDao()) {
            result = dao.create(exhibition);
            exhibitionDTO.setId(exhibition.getId());
        }
        return result;
    }

    public boolean addHallToExhibition(Long exhibitionId, HallName halltoAdd) {
        try(ExhibitionDao dao = daoFactory.createExhibitionDao()) {
            return dao.addHallToExhibition(exhibitionId, halltoAdd);
        }
    }

    public boolean updateExpiredExhibitionsAndTicketsManually() {
        try(ExhibitionDao dao = daoFactory.createExhibitionDao()) {
            return dao.updateExpiredExhibitionsAndTicketsManually();
        }
    }
}
