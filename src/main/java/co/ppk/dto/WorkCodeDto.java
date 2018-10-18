package co.ppk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkCodeDto {
    private String id;
    private String operatorId;
    private String billaboardId;
    private String authorization_code;
    private String status;
    private String createDate;
    private String updateDate;

    @JsonProperty("id")
    public String getId() { return id;  }
    public void setId(String id) { this.id = id; }

    @JsonProperty("operatorId")
    public String getOperatorId() { return operatorId;  }
    public void setOperatorId(String operatorId) { this.operatorId = operatorId;  }

    @JsonProperty("billaboardId")
    public String getBillaboardId() { return billaboardId;  }
    public void setBillaboardId(String billaboardId) { this.billaboardId = billaboardId;    }

    @JsonProperty("authorization_code")
    public String getAuthorization_code() { return authorization_code;  }
    public void setAuthorization_codes(String authorization_code) { this.authorization_code = authorization_code;    }

    @JsonProperty("status")
    public String getStatus() { return status;  }
    public void setStatus(String status) { this.status = status;    }

    @JsonProperty("createDate")
    public String getCreateDate() { return createDate; }
    public void setCreateDate(String createDate) { this.createDate = createDate; }

    @JsonProperty("updateDate")
    public String getUpdateDate() { return updateDate;  }
    public void setUpdateDate(String updateDate) { this.updateDate = updateDate;  }

    @Override
    public String toString() {
        return "OperatorDto{" +
                "id='" + id + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", billaboardId='" + billaboardId + '\'' +
                ", authorization_code='" + authorization_code + '\'' +
                ", status='" + status + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
