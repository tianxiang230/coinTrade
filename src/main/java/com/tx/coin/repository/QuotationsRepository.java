package com.tx.coin.repository;

import com.tx.coin.entity.Quotations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by 你慧快乐 on 2018-1-9.
 */
public interface QuotationsRepository extends JpaRepository<Quotations, Integer> {

    @Query(value = "SELECT q.id,q.buy,q.high,q.last,q.sell,q.low,q.vol,q.create_time,q.date,q.symbol FROM quotations q WHERE q.symbol=:symbol ORDER BY q.date desc LIMIT :pageSize", nativeQuery = true)
    List<Quotations> findDistinctBySymbolOrderByDate(@Param("symbol") String symbol, @Param("pageSize") int size);

    @Query(value = "SELECT q.last from quotations q where q.create_time like '%:00:%' and q.symbol=:symbol ORDER BY q.date desc LIMIT :pageSize",nativeQuery = true)
    List<Double> findHourBySymbolOrderByDate(@Param("symbol") String symbol, @Param("pageSize") int size);

    @Query(value = "SELECT q.last FROM quotations q WHERE q.symbol=:symbol ORDER BY q.date desc LIMIT :pageSize", nativeQuery = true)
    List<Double> getLastPriceBySymbolOrderByDate(@Param("symbol") String symbol, @Param("pageSize") int size);

}
