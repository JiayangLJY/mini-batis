package mini.batis.test.dao;

import mini.batis.test.po.User;

public interface IUserDao {

    User queryUserInfoById(Long uId);

}

