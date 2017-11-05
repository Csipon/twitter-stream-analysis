package com.bobbbaich.fb.bot.service.domain.impl;

import com.bobbbaich.fb.bot.dao.UserDao;
import com.bobbbaich.fb.bot.dao.CrudDao;
import com.bobbbaich.fb.bot.model.User;
import com.bobbbaich.fb.bot.service.domain.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private UserDao userDao;
    private CrudDao<User, Long> userCrudDao;

    @Override
    public Long create(User user) {
        return userCrudDao.create(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        LOG.debug("userRepository.findByUsername(username): {}", user);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else {
            return new SocialUser(
                    user.getUsername(),
                    user.getPassword(),
                    user.getUserRoles());
        }
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setUserCrudDao(CrudDao<User, Long> userCrudDao) {
        this.userCrudDao = userCrudDao;
    }
}
