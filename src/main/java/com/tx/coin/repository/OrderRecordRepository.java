package com.tx.coin.repository;

import com.tx.coin.entity.OrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.repository
 * @Description
 * @date 2018-1-12 16:07
 */
public interface OrderRecordRepository extends JpaRepository<OrderRecord,Integer> {
}
