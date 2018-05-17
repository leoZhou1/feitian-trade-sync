package com.feitian.trade.sync.third.taobao.api;

import com.feitian.trade.sync.model.TbUser;
import com.feitian.trade.sync.third.taobao.ClientFactory;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.CrmServiceChannelShortlinkCreateRequest;
import com.taobao.api.response.CrmServiceChannelShortlinkCreateResponse;

public class CrmApi extends BaseTbApi {
    public CrmApi(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    public CrmServiceChannelShortlinkCreateResponse shortlinkCreate(String shortLinkName, String linkType, String shortLinkData, TbUser user) {
        TaobaoClient client = clientFactory.getClient("json");
        CrmServiceChannelShortlinkCreateRequest req = new CrmServiceChannelShortlinkCreateRequest();
        req.setShortLinkName(shortLinkName);
        req.setLinkType(linkType);
        req.setShortLinkData(shortLinkData);
        if ("LT_SHOP".equals(linkType)) {
            req.setShortLinkData(null);
        }
        CrmServiceChannelShortlinkCreateResponse resp = null;
        try {
            resp = client.execute(req, user.getSessionKey());
            //检查sessionKey是否过期
            this.sessionIsValid(resp, user);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return resp;
    }
}
