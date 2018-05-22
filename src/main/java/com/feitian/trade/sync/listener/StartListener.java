package com.feitian.trade.sync.listener;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

import com.feitian.trade.sync.model.TbOrder;
import com.feitian.trade.sync.model.TbUser;
import com.feitian.trade.sync.service.IImportOrderService;
import com.feitian.trade.sync.service.IUserService;
import com.feitian.trade.sync.service.impl.LocalOrderService;

@Component
public class StartListener implements ApplicationListener<ApplicationEvent> {
    private final static long PAGE_SIZE = 10;
    private final IImportOrderService importOrderService;
    private final IUserService userService;
    private final LocalOrderService orderService;
    private Thread syncThread;
    private volatile boolean isStop = false;

    @Autowired
    public StartListener(IImportOrderService importOrderService, IUserService userService, LocalOrderService orderService) {
        this.importOrderService = importOrderService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent || event instanceof ContextStartedEvent) {
            this.startSyncThread();
            System.out.println(event.getClass().getSimpleName() + " 事件已发生！");
        }
    }

    public void startSync() {
        isStop = false;
    }

    public void stopSync() {
        isStop = true;
    }

    public void startSyncThread() {
        if (null == syncThread || !syncThread.isAlive()) {
            syncThread = new Thread(new SyncThread(importOrderService));
            syncThread.start();
        }
    }

    private class SyncThread implements Runnable {
        private IImportOrderService importOrderService;
        SyncThread(IImportOrderService importOrderService) {
            this.importOrderService = importOrderService;
        }
        @Override
        public void run() {
            while (!isStop) {
                Date now = new Date();
                List<TbUser> users = userService.findIncrementUsers(PAGE_SIZE);
                for (TbUser user : users) {
                    List<TbOrder> orders = importOrderService.importByUser(user, user.getLastUpdateTime(), now, false);
                    //保险起见，保存订单和修改最后更新时间，应该在同个事务里，目前还不是
                    if(null!=orders&&!orders.isEmpty()) {
                    	orderService.saveOrders(orders);
                    	user.setLastUpdateTime(now);
                    	userService.updateLastUpdateTime(user.getUserId(), now);
                    }
                }
            }
        }
    }
}
