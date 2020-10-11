package org.example.exhibitionsAppServlet.model.entity.dto;

import org.example.exhibitionsAppServlet.model.entity.enums.ExhibitionStatus;
import org.example.exhibitionsAppServlet.model.entity.enums.HallName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ExhibitionDTO {
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
    private List<HallName> hallNameList;
    private HallName hallName;

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

    public void setStartDate(String startDate) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime(sdf.parse(startDate));
        } catch (ParseException e) {
            //TODO log
        }
        this.startDate = calendar;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public void setEndDate(String endDate) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime(sdf.parse(endDate));
        } catch (ParseException e) {
            //TODO log
        }
        this.endDate = calendar;
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

    public List<HallName> getHallNameList() {
        return hallNameList;
    }

    public void setHallNameList(List<HallName> hallNameList) {
        this.hallNameList = hallNameList;
    }

    public HallName getHallName() {
        return hallName;
    }

    public void setHallName(HallName hallName) {
        this.hallName = hallName;
    }
}
