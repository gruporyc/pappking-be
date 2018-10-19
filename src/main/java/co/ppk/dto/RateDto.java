package co.ppk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RateDto {
    private  String id;
    private  String date;
    private  String value;
    private  String status;
    private  String createDate;
    private  String updateDate;

    @JsonProperty("id")
    public String getId() { return id;  }
    public void setId(String id) { this.id = id; }

    @JsonProperty("date")
    public String getDate() { return date;  }
    public void setDate(String date) { this.date = date;  }

    @JsonProperty("value")
    public String getValue() { return value;  }
    public void setValue(String value) { this.value = value;    }

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
        return "BillboardDto{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", value='" + value + '\'' +
                ", status='" + status + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
