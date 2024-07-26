package com.yutong.sheng.my_short_url.PO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@TableName("surl")
public class SurlPO {
    @TableId
    private Integer id;

    @TableField("short_url")
    private String shortUrl;

    @TableField("long_url")
    private String longUrl;

}
