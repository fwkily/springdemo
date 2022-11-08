package com.fwkily.mapper.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("bs_order")
@Data
public class OrderPo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String hobby;
}
