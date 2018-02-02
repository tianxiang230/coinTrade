package com.tx.coin.repository;

import com.tx.coin.entity.PlatFormConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.repository
 * @Description
 * @date 2018-2-1 13:21
 */
public interface PlatFormConfigRepository extends JpaRepository<PlatFormConfig, Integer> {

    @Query(value = "SELECT * from platform_config WHERE plat=:plat", nativeQuery = true)
    PlatFormConfig selectByPlat(@Param("plat") String plat);
}
