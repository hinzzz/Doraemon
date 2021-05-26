package com.hinz.jsr303;

import com.hinz.jsr303.bean.AddressInfo;
import com.hinz.jsr303.bean.AddressInfoDetail;
import com.hinz.jsr303.bean.UserInfo;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class Jsr3ApplicationTests {

    @Test
    void contextLoads() {
    }



    public static void main(String[] args) {
        List<AddressInfo> addressInfos = new ArrayList<>();
        UserInfo userInfo = UserInfo.builder().userName("hinzzz").build();
        AddressInfo aid =new AddressInfo();
        aid.setDetail("良垌");
        addressInfos.add(aid);
        userInfo.setAddressInfos(addressInfos);

        System.out.println("userInfo = " + userInfo);
        List<AddressInfo> list = userInfo.getAddressInfos();

       /* list.stream().map(item -> {
            AddressInfoDetail ccc = AddressInfoDetail.builder().a("ccc").build();
            return ccc;
        }).collect(Collectors.toList());*/

        for (AddressInfo item : list) {
            item.setDetail("深圳");
        }

        System.out.println("userInfo = " + userInfo);

    }
}


