package com.hinz.jsr303.bean;

import com.hinz.jsr303.valid.AddGroup;
import com.hinz.jsr303.valid.IntValue;
import com.hinz.jsr303.valid.UpdateGroup;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

/**
 * @author ：quanhz
 * @date ：Created in 2021/2/19 11:22
 * @Description : No Description
 */
@Data
public class UserInfo {

    @NotNull(message = "修改必须指定id",groups = {UpdateGroup.class})
    @Null(message = "新增不能指定id",groups = {AddGroup.class})
    private Long id;

    @Null(message = "用户名不能为空")
    private String userName;

    @NotNull(message = "年龄不能为空",groups = {AddGroup.class})
    @Min(value = 1,message = "年龄必须大于1",groups = {AddGroup.class,UpdateGroup.class})
    private Integer age;

    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^(1)\\d{10}$",message = "手机号格式错误")
    private String mobileNo;

    @IntValue(accessVals = {1,2},groups = {UpdateGroup.class})
    private Integer gender;

    @NotBlank(groups = {AddGroup.class})
    private String homeUrl;

    /**
     * 校验嵌套级联属性必须在属性上加@Valid
     */
    @Valid
    @NotNull
    @Size(min=1)
    private List<AddressInfo> addressInfos;

    @AssertTrue
    private Boolean flag;

    @Past
    private Date date;

}
