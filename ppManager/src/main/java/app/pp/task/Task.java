package app.pp.task;


import app.pp.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Task {
    private static final Logger logger = LoggerFactory.getLogger(Task.class);

    @Autowired
    DeviceService deviceService;

//
//    @Scheduled(cron = "0 0 * * * ?")
//     //@Scheduled(cron =  "0  */1 * * * ?")
//    public void device()throws Exception{
//
//        deviceService.getDevice();
//    }
}
