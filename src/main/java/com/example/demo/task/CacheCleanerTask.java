package com.example.demo.task;

import com.example.demo.services.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CacheCleanerTask {

    private final CacheService cacheService;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void clear() {
        cacheService.clearLessonsCache();
        cacheService.clearTeacherCache();
        cacheService.clearTeacherLessonsCache();
        cacheService.clearStudentCache();
    }
}
