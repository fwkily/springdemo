package com.fwkily.mapper.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("mytable")
@Data
public class MytablePo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String content;
}
