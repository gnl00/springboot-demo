package com.boot.dao;

import com.boot.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * UserDao
 *
 * JpaRepository<实体类类型，主键类型> 用来完成基本CRUD操作
 * JpaSpecificationExecutor<实体类类型> 用于复杂查询（分页等查询操作）
 *
 * @author lgn
 */

@Repository
public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    List<User> findByAgeAfter(Integer age);

    // 分页查询 1，使用原生 sql 语句
    @Query(value = "select * from tb_user limit :page, :size ", nativeQuery = true)
    List<User> getByPage(@Param("page") int page,@Param("size") int size);

    // 分页查询 2，方法已经在 PagingAndSortingRepository 中定义，直接使用即可
    // Page<User> findAll(Pageable pageable);

    // 使用 jpql 方式查询
    // 当使用 @Query 注解时，约定方法会失效。如 findByName 方法实际查询条件是 @Query 中的语句，而非方法名中的ByName
    // from 后面紧接着的是使用 @Table 标注的对应【实体类名】，而【非数据库表名】
    // ?1 方法中的表示第一个参数
    @Query(value = "from User where name = ?1")
    User jpqlFindByName(String name);

    // 使用 jpql 方式更新
    // 使用 jpql 更新时需要加上 @Modifying 注解
    // 使用 jpql 进行更新或者删除操作时，需要加上 @Transactional 注解
    // 更新、删除操作方法的返回值只能是void或int/Integer
    @Query(value = "update User set age = ?1 where name = ?2")
    @Modifying
    @Transactional
    int jpqlUpdate(Integer age, String name);

}
