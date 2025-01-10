package com.ll.domain.testMail.testMail.service;

import com.ll.framework.ioc.annotations.Autowired;
import com.ll.framework.ioc.annotations.Service;

@Service
public class TestFacadeMailLogService {
    @Autowired
    private TestMailLogService testMailLogService;
}
