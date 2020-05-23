package app.pp.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class PolicyEntity {


    private Integer id;

    private String pnum;

    private Integer state;

    private Integer saleid;

    private Integer groupid;

    private List<Group> group;

    private Integer isdel;

    private Date createdtime;

    private Long creator;

    private String prefix;

    private Integer number;

    private Integer verb;

    private String groupname;

    private Integer buycartype;

    private Integer page;

    private String carname;

    private String salenum;

    private BigDecimal amount1;

    private BigDecimal amount2;

    private String yearlimit;
}
