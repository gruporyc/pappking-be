package co.ppk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OperatorDto {
    private  String id;
    private  String document_type;
    private  String document_number;
    private  String name;
    private  String last_name;
    private  String address;
    private  String personal_phone;
    private  String assigned_phone;
    private  String status;
    private  String createDate;
    private  String updateDate;

    @JsonProperty("id")
    public String getId() { return id;  }
    public void setId(String id) { this.id = id; }

    @JsonProperty("document_type")
    public String getDocument_type() { return document_type;  }
    public void setDocument_type(String document_type) { this.document_type = document_type;  }

    @JsonProperty("document_number")
    public String getDocument_number() { return document_number;  }
    public void setDocument_number(String document_number) { this.document_number = document_number;    }

    @JsonProperty("name")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @JsonProperty("last_name")
    public String getLast_name() { return last_name;  }
    public void setLast_name(String last_name) { this.last_name = last_name;  }

    @JsonProperty("address")
    public String getAddress() { return address;  }
    public void setAddress(String address) { this.address = address; }

    @JsonProperty("personal_phone")
    public String getPersonal_phone() { return personal_phone;  }
    public void setPersonal_phone(String personal_phone) { this.personal_phone = personal_phone;  }

    @JsonProperty("assigned_phone")
    public String getAssigned_phone() { return assigned_phone;  }
    public void setAssigned_phones(String assigned_phone) { this.assigned_phone = assigned_phone;    }

    @JsonProperty("status")
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

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
                ", document_type='" + document_type + '\'' +
                ", document_number='" + document_number + '\'' +
                ", name='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", address='" + address + '\'' +
                ", personal_phone='" + personal_phone + '\'' +
                ", assigned_phone='" + assigned_phone + '\'' +
                ", status='" + status + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
