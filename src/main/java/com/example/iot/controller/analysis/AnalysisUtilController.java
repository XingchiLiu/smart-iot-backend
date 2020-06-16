package com.example.iot.controller.analysis;

import com.example.iot.controller.VO.ResultVO;
import com.example.iot.controller.VO.analysis.DeviceDataVO;
import com.example.iot.service.analysis.AnalysisUtilService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhm
 */
@Api("data-analysis")
@Slf4j
@RestController
@RequestMapping("/analysis/util")
public class AnalysisUtilController {
    public static final String NO_DEVICES = "不存在设备";

    private AnalysisUtilService analysisUtilService;

    @Autowired
    public AnalysisUtilController(AnalysisUtilService analysisUtilService) {
        this.analysisUtilService = analysisUtilService;
    }

    /**
     * 获取所有设备及其传输的数据
     *
     * @return 设备信息列表，{@link com.example.iot.controller.VO.analysis.DeviceDataVO}
     */
    @GetMapping("/device/all")
    public ResultVO getAllDevicesInfo() {
        List<DeviceDataVO> deviceDatas = analysisUtilService.getAllDevicesInfo();
        if(deviceDatas == null) {
            return ResultVO.getFailed(NO_DEVICES);
        }
        return ResultVO.getSuccess(deviceDatas);
    }
}
