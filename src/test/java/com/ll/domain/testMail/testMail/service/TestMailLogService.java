package com.ll.domain.testMail.testMail.service;

import com.ll.domain.testMail.testMail.repository.TestMailLogRepository;
import com.ll.domain.testPost.testPost.service.TestFacadePostService;
import com.ll.framework.ioc.annotations.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestMailLogService {
    private final TestFacadePostService testFacadePostService;
    private final TestMailLogRepository testMailLogRepository;
    private final List<String> testSafeExts;
}
