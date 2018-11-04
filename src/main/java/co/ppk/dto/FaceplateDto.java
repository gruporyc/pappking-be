package co.ppk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FaceplateDto {
    private String id;
    private String faceplate;
    private String customerid;
    private String createDate;
    private String updateDate;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("faceplate")
    public String getFaceplate() {
        return faceplate;
    }

    public void setFaceplate(String faceplate) {
        this.faceplate = faceplate;
    }

    @JsonProperty("customerid")
    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    @JsonProperty("create_date")
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @JsonProperty("update_date")
    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "FaceplateDto{" +
                "id='" + id + '\'' +
                ", faceplate='" + faceplate + '\'' +
                ", customerid='" + customerid + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
