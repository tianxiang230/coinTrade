package com.tx.coin.repository;

import com.tx.coin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by 你慧快乐 on 2018-1-9.
 */
public interface UserRepository extends JpaRepository<User,Integer>{
/*        List<User> findByAge(int age);

        List<User> findByAgeLessThan(int age);

        List<User> findById(long id);


        List<User> findByUsernameAndPassword(String username , String password);


        List<User> findByUsernameOrPassword(String username , String password);

        long  deleteByAge(int age);*/
@Query(value = "select count(user.id) From User user where user.age= :age")
long  countByAge(@Param("age") int age);

@Query(value = "select user from User user where user.name = ?1",nativeQuery=false)
User findByName(String name);
        /*


        @Query(value = "SELECT * FROM User WHERE username = ?1", nativeQuery = true)
        User findByName1(String name);

        @Query("from User where username  =:username ")
        List<User> findByName3(@Param("username")String name);

        @Query("select c from User c where c.username like %?1")
        User findByNamelike(String name);

        @Query("update   User c set c.username=?1  where c.username = ?2")
        User updateByName(String name1,String name2);

        @Modifying
        @Query("update User u set u.age = ?1 where u.id = ?2")
        int update(int age1 , long id);
        */
    }

