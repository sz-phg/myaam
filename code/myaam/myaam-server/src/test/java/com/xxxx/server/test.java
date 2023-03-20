package com.xxxx.server;

import com.xxxx.server.service.IMenuRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
    @Autowired
    IMenuRoleService iMenuRoleService;

    @Test
    public void test(){
        System.out.println(iMenuRoleService);
        System.out.println(iMenuRoleService.list());
    }
}
