package com.ll.domain.testMail.testMail.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ll.framework.ioc.annotations.Repository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TestMailLogRepository {
    private final ObjectMapper testBaseObjectMapper;
    private final JavaTimeModule testBaseJavaTimeModule;
}
