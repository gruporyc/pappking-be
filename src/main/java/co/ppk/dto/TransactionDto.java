package co.ppk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionDto {
    private  String id;
    private  String phone;
    private  String license_plate;
    private  String billboards_code;
    private  String start_date;
    private  String start_time;
    private  String end_date;
    private  String end_time;
    private  String time;
    private  String price;
    private  String closed;
    private  String createDate;
    private  String updateDate;

    @JsonProperty("id")
    public String getId() { return id;  }
    public void setId(String id) { this.id = id; }

    @JsonProperty("phone")
    public String getPhone() { return phone;  }
    public void setPhone(String phone) { this.phone = phone;  }

    @JsonProperty("license_plate")
    public String getLicense_plate() { return license_plate;  }
    public void setLicense_plate(String license_plate) { this.license_plate = license_plate;    }

    @JsonProperty("billboards_code")
    public String getBillboards_code() { return billboards_code; }
    public void setBillboards_code(String billboards_code) { this.billboards_code = billboards_code; }

    @JsonProperty("start_date")
    public String getStart_date() { return start_date; }
    public void setStart_date(String start_date) { this.start_date = start_date; }

    @JsonProperty("start_time")
    public String getStart_time() { return start_time;  }
    public void setStart_time(String start_time) { this.start_time = start_time; }

    @JsonProperty("end_date")
    public String getEnd_date() { return end_date; }
    public void setEnd_date(String end_date) { this.end_date = end_date; }

    @JsonProperty("end_time")
    public String getEnd_time() { return end_time;  }
    public void setEnd_time(String end_time) { this.end_time = end_time; }

    @JsonProperty("time")
    public String getTime() { return time;  }
    public void setTime(String time) { this.time = time; }

    @JsonProperty("price")
    public String getPrice() { return price;  }
    public void setPrice(String price) { this.price = price; }

    @JsonProperty("closed")
    public String getClosed() { return closed;  }
    public void setClosed(String closed) { this.closed = closed; }

    @JsonProperty("createDate")
    public String getCreateDate() { return createDate; }
    public void setCreateDate(String createDate) { this.createDate = createDate; }

    @JsonProperty("updateDate")
    public String getUpdateDate() { return updateDate;  }
    public void setUpdateDate(String updateDate) { this.updateDate = updateDate;  }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", license_plate='" + license_plate + '\'' +
                ", billboards_code='" + billboards_code + '\'' +
                ", start_date='" + start_date + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_date='" + end_date + '\'' +
                ", end_time='" + end_time + '\'' +
                ", time='" + time + '\'' +
                ", price='" + price + '\'' +
                ", closed='" + closed + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
