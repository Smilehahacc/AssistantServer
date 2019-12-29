import com.lynn.dao.UserInforMapper;
import com.lynn.pojo.UserInfor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ConnectTest {

    @Resource
    private UserInforMapper userInforMapper;

    @Test
    public void findUser(){
        UserInfor u = new UserInfor();
        u.setInfor_phone("18106983666");
        System.out.println(userInforMapper.findUser(u).getInfor_name());

    }

}
