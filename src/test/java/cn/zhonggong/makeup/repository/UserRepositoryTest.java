package cn.zhonggong.makeup.repository;

import cn.zhonggong.makeup.domain.User;
import cn.zhonggong.makeup.service.UserService;
import cn.zhonggong.makeup.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 军辉
 * 2019-07-11 20:28
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    private UserServiceImpl userService;


    @Test
    public void testAdd() {
        User user = new User(1, "sdfsdf", "dsfsdf", "23411", 1, 1);

        //Assert.assertTrue(userService.save(user),1);
        Assert.assertNotNull(userService.save(user));
    }

    @Test
    public void testFindByName() {
        User user = userService.findByName("dsfsdf");
        Assert.assertNotNull(user);
    }

}