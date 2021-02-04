package com.mristudio.massnundoa.model;

public class DataModel {
    private String modelId;
    private String nameOfChapter;
    private String imagepathUrl;
    private String descriptionDetails;

    public DataModel() {
    }

    public DataModel(String modelId, String nameOfChapter, String imagepathUrl, String descriptionDetails) {
        this.modelId = modelId;
        this.nameOfChapter = nameOfChapter;
        this.imagepathUrl = imagepathUrl;
        this.descriptionDetails = descriptionDetails;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getNameOfChapter() {
        return nameOfChapter;
    }

    public void setNameOfChapter(String nameOfChapter) {
        this.nameOfChapter = nameOfChapter;
    }

    public String getImagepathUrl() {
        return imagepathUrl;
    }

    public void setImagepathUrl(String imagepathUrl) {
        this.imagepathUrl = imagepathUrl;
    }

    public String getDescriptionDetails() {
        return descriptionDetails;
    }

    public void setDescriptionDetails(String descriptionDetails) {
        this.descriptionDetails = descriptionDetails;
    }
}
