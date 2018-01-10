package com.tx.coin.repository;

import com.tx.coin.entity.Quotations;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 你慧快乐 on 2018-1-9.
 */
public interface QuotationsRepository extends JpaRepository<Quotations,Integer> {
}
