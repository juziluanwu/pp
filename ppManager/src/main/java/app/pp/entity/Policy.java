package app.pp.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class Policy {
    private Integer id;

    private String pnum;

    private Integer state;

    private Integer saleid;

    private Integer groupid;

    private Integer isdel;

    private Date createdtime;

    private Long creator;

    private String prefix;

    private Integer number;

    private Integer verb;

    private BigDecimal amount1;

    private BigDecimal amount2;

    private String yearlimit;

}