package com.tx.coin.service;

import com.tx.coin.dto.KLineDataDTO;
import com.tx.coin.enums.TimeIntervalEnum;

import java.util.Date;
import java.util.List;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service
 * @Description 获取K线数据
 * @date 2018-1-13 22:35
 */
public interface IKLineService {

    /**
     * 获取K线数据
     * @param symbol
     * @param intervalTye
     * @param size 可空
     * @param since 可空
     * @return
     */
    List<KLineDataDTO> pullLineData(String symbol, TimeIntervalEnum intervalTye, int size, Date since);


}
