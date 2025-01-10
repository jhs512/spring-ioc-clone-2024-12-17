package com.ll.global.testFile;

import com.ll.domain.testFile.testFile.service.TestFileService;
import com.ll.framework.ioc.annotations.Bean;
import com.ll.framework.ioc.annotations.Configuration;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class TestFileConfig {
    private final TestFileService testFileService;
    @Bean
    public List<String> testSafeExts() {
        return List.of("jpg", "jpeg", "png", "gif", "bmp");
    }
}