package http.ne.sty.com.nehttp.neokhttp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池管理类
 */
public class ThreadPoolManager {
    private static final int DELAY_TIME = 3000;
    private static ThreadPoolManager threadPoolManager = new ThreadPoolManager();
    private static volatile Handler mHandler;

    public static ThreadPoolManager getInstance() {
        if(mHandler == null) {
            synchronized (ThreadPoolManager.class) {
                if(mHandler == null) {
                    mHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return threadPoolManager;
    }

    //创建存放网络请求的队列
    private LinkedBlockingDeque<Runnable> mQueue = new LinkedBlockingDeque<>();

    //将请求放入队列
    public void addTask(Runnable runnable) {
        if(runnable != null) {
            try {
                mQueue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //创建延迟队列
    private DelayQueue<HttpTask> mDelayQueue = new DelayQueue<>();
    public void addDelay(HttpTask ht) {
        if(ht != null) {
            ht.setDelayTime(DELAY_TIME);
            mDelayQueue.offer(ht);
        }
    }
    public Runnable delayThread = new Runnable() {
        @Override
        public void run() {
            HttpTask ht = null;
            while (true) {
                try {
                    ht = mDelayQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(ht.getRetryCount() < 3) {
                    mThreadPoolExecutor.execute(ht);
                    ht.setRetryCount(ht.getRetryCount() + 1);
                    Log.i("sty", "=== 重试机制 === " + ht.getRetryCount());
                }else {
                    Log.i("sty", "=== 重试机制 === 执行次数超限，放弃 " + ht.getRetryCount());
                    final HttpTask.TaskListener taskListener = ht.getTaskListener();
                    if(taskListener != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                taskListener.onTaskFailure();
                            }
                        });
                    }
                }
            }
        }
    };

    //创建线程池
    private ThreadPoolExecutor mThreadPoolExecutor;
    private ThreadPoolManager() {
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 10, 15,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                //处理抛出来的任务
                addTask(r);
            }
        });

        mThreadPoolExecutor.execute(ddThread);
        mThreadPoolExecutor.execute(delayThread);
    }

    //创建调度线程
    public Runnable ddThread = new Runnable() {
        Runnable runnable = null;
        @Override
        public void run() {
            while (true) {
                try {
                    runnable = mQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(runnable != null) {
                    mThreadPoolExecutor.execute(runnable);
                }
            }
        }
    };
}
