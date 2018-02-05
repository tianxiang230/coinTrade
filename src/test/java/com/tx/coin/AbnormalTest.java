package com.tx.coin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tx.coin.dto.UserInfoDTO;
import com.tx.coin.utils.HttpsUtil;
import com.tx.coin.utils.JsonMapper;
import org.junit.Test;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by 你慧快乐 on 2018-1-11.
 */
public class AbnormalTest {
    @Test
    public void testJsonMapper() throws IOException {
        String json="{\"ssc\":\"0\",\"xuc\":\"0\",\"eos\":\"0\",\"fair\":\"0\",\"kcash\":\"0\",\"vib\":\"0\",\"ugc\":\"0\",\"ost\":\"0\",\"mot\":\"0\",\"brd\":\"0\",\"dna\":\"0\",\"xmr\":\"0\",\"ipc\":\"0\",\"bt1\":\"0\",\"ctr\":\"0\",\"bt2\":\"0\",\"xem\":\"0\",\"nas\":\"0\",\"iota\":\"0\",\"viu\":\"0\",\"wtc\":\"0\",\"tnb\":\"0\",\"dnt\":\"0\",\"dgb\":\"0\",\"dgd\":\"0\",\"zrx\":\"0\",\"sub\":\"0\",\"bcd\":\"0.00743277143\",\"aac\":\"0\",\"bcc\":\"0.0000000000010444\",\"bch\":\"0.0000000000010444\",\"omg\":\"0\",\"cmt\":\"0\",\"btc\":\"0.0000000094468699\",\"cvc\":\"0\",\"1st\":\"0\",\"mag\":\"0\",\"bcs\":\"0\",\"btg\":\"0\",\"bcx\":\"0\",\"btm\":\"0\",\"ark\":\"0\",\"smt\":\"0\",\"rcn\":\"0\",\"knc\":\"0\",\"rct\":\"0\",\"salt\":\"0\",\"storj\":\"0\",\"gnt\":\"0\",\"dpy\":\"0\",\"gnx\":\"0\",\"snm\":\"0\",\"mana\":\"0\",\"ppt\":\"0\",\"la\":\"0\",\"snt\":\"0\",\"sngls\":\"0\",\"rdn\":\"0\",\"fun\":\"0\",\"ace\":\"0.00000001332\",\"ast\":\"0\",\"pyn\":\"0\",\"ubtc\":\"0\",\"ukg\":\"0\",\"act\":\"0.00005729346\",\"yoyo\":\"0\",\"etc\":\"0.16511684514\",\"icn\":\"0\",\"dat\":\"0\",\"etf\":\"0\",\"vee\":\"0\",\"eth\":\"0.0000019143003223\",\"usdt\":\"0.8282957944023\",\"mco\":\"0\",\"aidoc\":\"0\",\"topc\":\"0\",\"zec\":\"0\",\"neo\":\"0\",\"itc\":\"0\",\"tio\":\"0\",\"lrc\":\"0\",\"elf\":\"0\",\"req\":\"0\",\"icx\":\"0\",\"mth\":\"0\",\"read\":\"0\",\"mtl\":\"0\",\"pay\":\"0\",\"bnt\":\"0\",\"mda\":\"0\",\"pro\":\"0\",\"f4sbtc\":\"0\",\"utk\":\"0\",\"edo\":\"0\",\"xrp\":\"0\",\"rnt\":\"0\",\"trx\":\"0\",\"dash\":\"0\",\"mdt\":\"0\",\"nuls\":\"0\",\"amm\":\"0\",\"hsr\":\"0\",\"link\":\"0\",\"cag\":\"0\",\"show\":\"0\",\"sbtc\":\"0.00022534872\",\"ngc\":\"0\",\"qun\":\"0\",\"qtum\":\"0\",\"gas\":\"0\",\"ltc\":\"0\",\"lend\":\"0\",\"avt\":\"0\",\"eng\":\"0\",\"san\":\"0\",\"evx\":\"0\",\"oax\":\"0\",\"wrc\":\"0\",\"qvt\":\"0\",\"int\":\"0\",\"xlm\":\"0\",\"swftc\":\"0\"}";
        ObjectMapper mapper=new ObjectMapper();
        UserInfoDTO userInfo=mapper.readValue(json,UserInfoDTO.class);
        System.out.println(userInfo.getAce());
    }

    @Test
    public void testJson(){
        List<Map<String,String>> list=new ArrayList<>();
        Map<String,String> map=new HashMap<>();
        map.put("1","one");
        map.put("2","tow");
        list.add(map);
        System.out.println(JsonMapper.nonDefaultMapper().toJson(list));
    }


    @Test
    public void test(){
        DecimalFormat decimalFormat = new DecimalFormat("####.#######");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        System.out.println(decimalFormat.format(2.34564376587));

    }

    @Test
    public void testHttp(){
        String url="https://www.baidu.com";
        String result = HttpsUtil.doGetSSL(url, null);
        System.out.println(result);
    }
}
