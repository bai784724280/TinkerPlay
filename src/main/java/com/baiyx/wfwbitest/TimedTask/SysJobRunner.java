package com.baiyx.wfwbitest.TimedTask;

import com.baiyx.wfwbitest.Dao.ISysJobRepository;
import com.baiyx.wfwbitest.Entity.SysJobPO;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 白宇鑫
 * @Date: 2022-10-10 上午 11:27
 * @Description: CommandLineRunner用于在应用程序启动后执行特定的操作,程序启动后扫描数据库定时任务表,加载可执行的定时任务
 */
@Service
public class SysJobRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(SysJobRunner.class);

    @Autowired
    private ISysJobRepository sysJobRepository;

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    @Override
    public void run(String... args) {
        // 初始加载数据库里状态为正常的定时任务
        // List<SysJobPO> jobList = sysJobRepository.getSysJobListByStatus(SysJobStatus.NORMAL.ordinal());
        List<SysJobPO> jobList = sysJobRepository.getSysJobListByStatus(1);
        // 程序启动时,一次性加载数据库里面状态为1的正常的定时任务
        if (CollectionUtils.isNotEmpty(jobList)) {
            for (SysJobPO job : jobList) {
                SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
                cronTaskRegistrar.addCronTask(task, job.getCronExpression());
            }

            logger.info("定时任务已加载完毕...");
        }
    }
}
