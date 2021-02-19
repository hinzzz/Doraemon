package com.hinz.jsr303.bean;

import com.hinz.jsr303.valid.AddGroup;
import com.hinz.jsr303.valid.GenderValue;
import com.hinz.jsr303.valid.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.*;

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

    @NotNull(message = "用户名不能为空")
    private String userName;

    @NotNull(message = "年龄不能为空")
    @Min(value = 0,message = "年龄必须大于0")
    private Integer age;

    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^(1)\\d{10}$",message = "手机号格式错误")
    private String mobileNo;

    @GenderValue(accessVals = {1,2})
    private String gender;

    @NotBlank(groups = {AddGroup.class})
    private String homeUrl;

}
