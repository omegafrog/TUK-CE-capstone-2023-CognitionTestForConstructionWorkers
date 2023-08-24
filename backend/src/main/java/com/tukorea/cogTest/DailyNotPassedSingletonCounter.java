package com.tukorea.cogTest;

import com.tukorea.cogTest.domain.enums.Risk;
import com.tukorea.cogTest.dto.SubjectDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class DailyNotPassedSingletonCounter {

    private static DailyNotPassedSingletonCounter instance= new DailyNotPassedSingletonCounter();

    private Map<Long, Long> fieldNotPassedCountMap = new HashMap<>();

    private LocalDate date=LocalDate.now();
    private boolean initialized=false;

    private DailyNotPassedSingletonCounter() {
    }

    public static DailyNotPassedSingletonCounter getInstance(){
        return instance;
    }
    public Long increase(Long fieldId){
        Long count = fieldNotPassedCountMap.get(fieldId);
        count++;
        fieldNotPassedCountMap.put(fieldId, count);
        return count;
    }

    @Scheduled(cron = "0 0 00 * * ?")
    private void initialize(){
        if(!date.equals(LocalDate.now())){
            fieldNotPassedCountMap.keySet().forEach(
                    key->fieldNotPassedCountMap.put(key, 0L)
            );
        }
        date = LocalDate.now();
    }


    public void update(Long fieldId, List<SubjectDTO> subjectList){
        log.info("NotPassedCount initialized. field:"+fieldId);
        fieldNotPassedCountMap.put(fieldId, 0L);
        subjectList.forEach(
                subject -> {
                    // 오늘 테스트한 인원이 있으면
                    if(subject.getLastTestedDate().equals(LocalDate.now())){
                        // 오늘 테스트한 결과가 정상이 아니라면
                        if(subject.getRisk()!= Risk.NORMAL)
                            increase(fieldId);
                    }
                }
        );
        initialized = true;
    }
    public boolean isInitialized(){
        return initialized;
    }
    public Long getCount(Long fielidId){
        return fieldNotPassedCountMap.get(fielidId);
    }
}
