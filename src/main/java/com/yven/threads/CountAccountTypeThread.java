package com.yven.threads;

import com.yven.domain.Imuser;
import com.yven.utils.CountAccountType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CountAccountTypeThread {
    /**
     * 多线程处理list
     *
     * @param data  数据list
     * @param threadNum  线程数
     */
    public synchronized void handleList(List<Imuser> data, int threadNum,CountDownLatch latch) {
        int length = data.size();
        int tl = length % threadNum == 0 ? length / threadNum : (length
                / threadNum + 1);

        for (int i = 0; i < threadNum; i++) {
            int end = (i + 1) * tl;
            HandleThread thread = new HandleThread("线程[" + (i + 1) + "] ",  data, i * tl, end > length ? length : end,latch);
            thread.start();
        }
    }

    class HandleThread extends Thread {
        private String threadName;
        private List<Imuser> data;
        private int start;
        private int end;
        CountDownLatch latch;

        public HandleThread(String threadName, List<Imuser> data, int start, int end,CountDownLatch latch) {
            this.threadName = threadName;
            this.data = data;
            this.start = start;
            this.end = end;
            this.latch = latch;
        }

        @Override
        public void run() {
            while (end > start){
                Imuser imuser = data.get(start++);
                imuser.setTclAccount(CountAccountType.isTclAccount(imuser.getMobilePhone()));
                System.out.println(imuser);
            }
            latch.countDown();//计数器减一

        }

    }

    public static void main(String[] args) throws InterruptedException {
        CountAccountTypeThread test = new CountAccountTypeThread();
        CountDownLatch latch = new CountDownLatch(4);// 计数器为2
        Long startTime = System.currentTimeMillis();
        // 准备数据
        List<Imuser> data = new ArrayList<Imuser>();
        for (int i = 0; i < 10000; i++) {
            Imuser imuser = new Imuser();
            imuser.setUserId("1000"+i);
            imuser.setMobilePhone("1562277292"+i);
            data.add(imuser);
        }
        test.handleList(data, 4,latch);
        latch.await();
        System.out.println("end");
        //System.out.println(ArrayUtils.toString(data));
    }
}
