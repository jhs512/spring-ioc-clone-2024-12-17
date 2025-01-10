package com.ll.domain.testMail.testMail.service;

import com.ll.domain.testMail.testMail.repository.TestMailLogRepository;
import com.ll.domain.testPost.testPost.service.TestFacadePostService;
import com.ll.framework.ioc.annotations.Autowired;
import com.ll.framework.ioc.annotations.Service;

import java.util.List;

@Service
public class TestMailLogService {
    private final TestFacadePostService testFacadePostService;
    private final TestMailLogRepository testMailLogRepository;
    private final List<String> testSafeExts;

    public TestMailLogService(TestFacadePostService testFacadePostService, TestMailLogRepository testMailLogRepository) {
        this.testFacadePostService = testFacadePostService;
        this.testMailLogRepository = testMailLogRepository;
        this.testSafeExts = null;
    }

    @Autowired
    public TestMailLogService(TestFacadePostService testFacadePostService, TestMailLogRepository testMailLogRepository, List<String> testSafeExts) {
        this.testFacadePostService = testFacadePostService;
        this.testMailLogRepository = testMailLogRepository;
        this.testSafeExts = testSafeExts;
    }
}
