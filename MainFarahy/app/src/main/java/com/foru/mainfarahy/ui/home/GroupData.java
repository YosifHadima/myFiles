package com.foru.mainfarahy.ui.home;

import java.util.List;

public class GroupData {
    private String storeName;
    private String userId;
    private String businessName;
    private String imageUrl;
    private List<ChildData> children;

    // Constructor
    public GroupData(String storeName, String imageUrl, List<ChildData> children) {
        this.storeName = storeName;
        this.imageUrl = imageUrl;
        this.children = children;
    }

    public GroupData() {

    }
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    // Getters and setters

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<ChildData> getChildren() {
        return children;
    }

    public void setChildren(List<ChildData> children) {
        this.children = children;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String imageUrl) {
        this.userId = userId;
    }
}
