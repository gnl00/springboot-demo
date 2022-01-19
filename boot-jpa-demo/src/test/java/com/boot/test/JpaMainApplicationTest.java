package com.boot.test;

import com.boot.dao.UserDao;
import com.boot.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * JpaMainApplication
 *
 * @author lgn
 */

@SpringBootTest
public class JpaMainApplicationTest {

    @Autowired
    UserDao userDao;

    @Test
    void testFindAll() {
        System.out.println(userDao.findAll());
    }

    @Test
    public void testFindByCondition() {
        System.out.println(userDao.findByAgeAfter(25));
    }

    // 单条件排序
    @Test
    public void testFindAllAndSort() {
        System.out.println(userDao.findAll(Sort.by(Sort.Direction.ASC, "name")));
    }

    // 多条件排序
    @Test
    public void testFindAllAndMultiSort() {
        Sort.Order idOrder = Sort.Order.asc("id");
        Sort.Order nameOrder = Sort.Order.desc("name");
        System.out.println(userDao.findAll(Sort.by(idOrder, nameOrder)));
    }

    // 根据值长度排序
    @Test
    public void testSortByLength() {}

    @Test
    public void testJpqlQuery() {
        System.out.println(userDao.jpqlFindByName("lgn"));
    }

    @Test
    public void testJpqlUpdate() {
        System.out.println(userDao.jpqlUpdate(18, "lgn"));
    }

    @Test
    public void testFindByPage() {
        System.out.println(userDao.getByPage(0, 5));
    }

    @Test
    public void testFindByPage2() {
        // 分页
        PageRequest pageRequest = PageRequest.of(0, 5);
        // 排序并分页
        PageRequest pageRequest2 = PageRequest.of(0, 5, Sort.by("name").ascending());
        Page<User> userPage = userDao.findAll(pageRequest);
        System.out.println(userPage.getContent());
        System.out.println(userPage.getTotalPages());
        System.out.println(userPage.getTotalElements());
    }

    @Test
    public void testSave() {
        User u = new User(null, "alen", 33);
        System.out.println(userDao.save(u));
    }

    @Test
    public void testUpdate() {
        User u = new User(1l, "lgn", 21);
        System.out.println(userDao.save(u));
    }

}

