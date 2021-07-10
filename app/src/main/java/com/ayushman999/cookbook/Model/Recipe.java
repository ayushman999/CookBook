package com.ayushman999.cookbook.Model;

public class Recipe {
    private String mealId, title,category,imgLink,ytLink,ingList,amtList,description,srcLink;

    public Recipe(String mealId,String title, String category, String imgLink, String ytLink, String ingList, String amtList, String description, String srcLink) {
        this.title = title;
        this.category = category;
        this.imgLink = imgLink;
        this.ytLink = ytLink;
        this.ingList = ingList;
        this.amtList = amtList;
        this.description = description;
        this.srcLink = srcLink;
        this.mealId=mealId;
    }
    public Recipe(String mealId,String title, String category,String imgLink)
    {
        this.mealId=mealId;
        this.title=title;
        this.category=category;
        this.imgLink=imgLink;
    }

    public String getMealId()
    {
        return mealId;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getImgLink() {
        return imgLink;
    }

    public String getYtLink() {
        return ytLink;
    }

    public String getIngList() {
        return ingList;
    }

    public String getAmtList() {
        return amtList;
    }

    public String getDescription() {
        return description;
    }

    public String getSrcLink() {
        return srcLink;
    }
}
