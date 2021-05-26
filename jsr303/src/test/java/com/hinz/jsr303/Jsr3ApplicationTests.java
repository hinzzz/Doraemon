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
        ArrayList<AddressInfo> list = new ArrayList<>();
        AddressInfoDetail aaaaaaaaaaaa = AddressInfoDetail.builder().a("aaaaaaaaaaaa").build();
        list.add(aaaaaaaaaaaa);
        UserInfo hinz = UserInfo.builder().userName("hinz").addressInfos(list).build();
        System.out.println("hinz = " + hinz);

        List<AddressInfo> bbbb = list.stream().map(item -> {
            AddressInfoDetail ccc = AddressInfoDetail.builder().a("ccc").build();
            return ccc;
        }).collect(Collectors.toList());

        bbbb.forEach(item->{
            System.out.println("item = " + item.getDetail());
        });

    }
}
