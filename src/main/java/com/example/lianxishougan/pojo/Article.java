package com.example.lianxishougan.pojo;


import com.example.lianxishougan.anno.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
@Data
public class Article {
    private Integer id;//主键ID
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String title;//作品标题
    @NotEmpty
    private String content;//作品内容
    @NotEmpty
    @URL
    private String coverImg;//封面图像
    @State
    private String state;//发布状态 已发布|草稿|待审批|审批成功|审批失败
    @NotNull
    private Integer categoryId;//作品分类id
    private Integer createUser;//创建人ID
    private String advice;
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
