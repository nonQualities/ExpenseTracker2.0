package org.exptrkr;

public class Categories {
    private long CatID = System.currentTimeMillis();
    private String CatName;

    public Categories(long categoryID, String categoryName) {
    }
    public Categories(){}

    public long getCatID() {
        return CatID;
    }

    public void setCatID(long catID) {
        CatID = catID;
    }

    public String getCatName() {
        return CatName;
    }

    public void setCatName(String catName) {
        CatName = catName;
    }


}
