package spider.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
@Entity
@Data
public class School {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sId;
    private String sName;
    private String postCode;
    private String sTel;

    @Lob
    private String sDesc;
    private String address;
    private String province;
    private String level;

    @Override
    public String toString() {
        return "School{" +
                "sId=" + sId +
                ", sName='" + sName + '\'' +
                ", postCode='" + postCode + '\'' +
                ", sTel='" + sTel + '\'' +
                ", sDesc='" + sDesc + '\'' +
                ", address='" + address + '\'' +
                ", province='" + province + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
