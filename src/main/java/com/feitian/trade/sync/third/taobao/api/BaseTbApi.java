package com.feitian.trade.sync.third.taobao.api;

import com.feitian.trade.sync.model.TbUser;
import com.feitian.trade.sync.third.taobao.ClientFactory;
import com.feitian.trade.sync.third.taobao.exception.InvalidSessionException;
import com.taobao.api.TaobaoResponse;

public class BaseTbApi {
    protected ClientFactory clientFactory;

    void sessionIsValid(TaobaoResponse response, TbUser user) {
        if (null != response && response.getErrorCode() != null && response.getErrorCode().equals("27")) {
            throw new InvalidSessionException(user.getUserId());
        }
    }
}
