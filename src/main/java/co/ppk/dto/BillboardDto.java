package co.ppk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BillboardDto {
    private  String id;
    private  String code;
    private  String address;
    private  String createDate;
    private  String updateDate;

    @JsonProperty("id")
    public String getId() { return id;  }
    public void setId(String id) { this.id = id; }

    @JsonProperty("code")
    public String getCode() { return code;  }
    public void setCode(String code) { this.code = code;  }

    @JsonProperty("address")
    public String getAddress() { return address;  }
    public void setAddress(String address) { this.address = address;    }

    @JsonProperty("createDate")
    public String getCreateDate() { return createDate; }
    public void setCreateDate(String createDate) { this.createDate = createDate; }

    @JsonProperty("updateDate")
    public String getUpdateDate() { return updateDate;  }
    public void setUpdateDate(String updateDate) { this.updateDate = updateDate;  }

    @Override
    public String toString() {
        return "BillboardDto{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", address='" + address + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
