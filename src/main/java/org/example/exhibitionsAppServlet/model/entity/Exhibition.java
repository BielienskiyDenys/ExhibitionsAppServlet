package org.example.exhibitionsAppServlet.model.entity;

import org.example.exhibitionsAppServlet.model.entity.dto.ExhibitionDTO;
import org.example.exhibitionsAppServlet.model.entity.enums.ExhibitionStatus;
import org.example.exhibitionsAppServlet.model.entity.enums.HallName;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Exhibition {
    private Long id;
    private String exName;
    private Calendar startDate;
    private Calendar endDate;
    private String openTime;
    private String closeTime;
    private String description;
    private Long price;
    private String themes;
    private ExhibitionStatus exhibitionStatus;
    private List<HallName> hallNameList = new ArrayList<>();

    public Exhibition() {
    }

    public Exhibition(ExhibitionDTO exhibitionDTO) {
        this.exName = exhibitionDTO.getExName();
        this.startDate = exhibitionDTO.getStartDate();
        this.endDate = exhibitionDTO.getEndDate();
        this.openTime = exhibitionDTO.getOpenTime();
        this.closeTime = exhibitionDTO.getCloseTime();
        this.description = exhibitionDTO.getDescription();
        this.price = exhibitionDTO.getPrice();
        this.themes = exhibitionDTO.getThemes();
        this.exhibitionStatus = ExhibitionStatus.ACTIVE;
        this.hallNameList = new ArrayList<HallName>();
        this.hallNameList.add(exhibitionDTO.getHallName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExName() {
        return exName;
    }

    public void setExName(String exName) {
        this.exName = exName;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public void setStartDate(Date sqlDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sqlDate);
        this.startDate = cal;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public void setEndDate(Date sqlDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sqlDate);
        this.endDate = cal;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getThemes() {
        return themes;
    }

    public void setThemes(String themes) {
        this.themes = themes;
    }

    public ExhibitionStatus getExhibitionStatus() {
        return exhibitionStatus;
    }

    public void setExhibitionStatus(ExhibitionStatus exhibitionStatus) {
        this.exhibitionStatus = exhibitionStatus;
    }

    public void setExhibitionStatus(int statusId) {
        this.exhibitionStatus = ExhibitionStatus.values()[statusId];
    }

    public List<HallName> getHallNameList() {
        return hallNameList;
    }

    public void setHallNameList(List<HallName> hallNameList) {
        this.hallNameList = hallNameList;
    }

    public boolean addHall(int hallId) {
        HallName hallToAdd = HallName.values()[hallId];
        if (hallNameList.contains(hallToAdd)){
            return false;
        } else
        this.hallNameList.add(hallToAdd);
        return true;
    }

    @Override
    public String toString() {
        return "Exhibition{" +
                "id=" + id +
                ", exName='" + exName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", openTime='" + openTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", themes='" + themes + '\'' +
                ", exhibitionStatus=" + exhibitionStatus +
                ", hallNameList=" + hallNameList +
                '}';
    }


}
