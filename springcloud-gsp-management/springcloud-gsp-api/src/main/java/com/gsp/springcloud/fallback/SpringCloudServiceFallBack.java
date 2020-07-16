package com.gsp.springcloud.fallback;

import com.gsp.springcloud.base.ResultData;
import com.gsp.springcloud.service.SpringCloudService;
import feign.hystrix.FallbackFactory;

import java.util.Map;

/**
 * @Author Don
 * @Description 该类用作熔断时执行的备份方法
 * @Date 2020/7/13 14:38
 **/
//@Component
public class SpringCloudServiceFallBack implements FallbackFactory<SpringCloudService> {
    /**
     * 该方法就是用于熔断的方法
     *
     * @param throwable
     * @return
     */
    public SpringCloudService create(Throwable throwable) {
        SpringCloudService springCloudService = new SpringCloudService() {
            public ResultData selectAll() {
                return null;
            }

            public ResultData selectUnit(Map map) {
                return null;
            }

            public ResultData selectScoreRecords(Map map) {
                return null;
            }

            public ResultData selectHeaderOfUnit(Map map) {
                return null;
            }

            public ResultData selectHeaderOfUnitDetail(Map map) {
                return null;
            }

            public ResultData selectTechnicalPerson(Map map) {
                return null;
            }

            public ResultData selectTechnicalPersonDetail(Map map) {
                return null;
            }

            public ResultData selectAuditRecords(Map map) {
                return null;
            }

            public ResultData selectEquipment(Map map) {
                return null;
            }

            public ResultData selectEquipmentDetail(Map map) {
                return null;
            }

            public ResultData selectSpecialPost(Map map) {
                return null;
            }

            public ResultData selectSpecialPostDetail(Map map) {
                return null;
            }

            public ResultData selectResource(Map map) {
                return null;
            }
        };
        return springCloudService;
    }
}
