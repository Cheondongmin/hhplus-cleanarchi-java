package com.hhplus_cleanarchi_java;

import com.hhplus_cleanarchi_java.util.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class IntegrationTest {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    protected void setUp() {
        databaseCleaner.clear();
    }
}
