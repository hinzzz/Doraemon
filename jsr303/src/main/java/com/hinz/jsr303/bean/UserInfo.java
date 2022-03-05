package com.hinz.jsr303.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hinz.jsr303.valid.IntValue;
import com.hinz.jsr303.valid.ValidatorGroup;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ：quanhz
 * @date ：Created in 2021/2/19 11:22
 * @Description : No Description
 */
@Data
@Builder
public class UserInfo implements Serializable {

    @NotNull(message = "修改必须指定id",groups = {ValidatorGroup.Edit.class})
    @Null(message = "新增不能指定id",groups = {ValidatorGroup.Add.class})
    private Long id;

    @NotEmpty(message = "用户名不能为空")
    private String userName;

    @NotNull(message = "年龄不能为空",groups = {ValidatorGroup.Add.class})
    @Min(value = 1,message = "年龄必须大于1",groups = {ValidatorGroup.Add.class,ValidatorGroup.Edit.class})
    private Integer age;

    @NotEmpty(message = "手机号不能为空",groups = {ValidatorGroup.Add.class})
    @Pattern(regexp = "^(1)\\d{10}$",message = "手机号格式错误")
    private String mobileNo;

    @IntValue(accessVals = {1,2},groups = {ValidatorGroup.Edit.class})
    private Integer gender;

    @NotBlank(groups = {ValidatorGroup.Add.class})
    private String homeUrl;

    /**
     * 校验嵌套级联属性必须在属性上加@Valid
     */
    @Valid
    @Size(min = 1)
    @NotNull(message = "地址列表不能为空",groups = {ValidatorGroup.Edit.class})
    private List<AddressInfo> addressInfos;

    @AssertTrue
    private Boolean flag;

    @Email
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date birthday;

    @Null
    private ChangeColumnTypeEnum anEnum;
}
