package com.hinz.problems;

import com.hinz.problems.spring.annotation.QualifierProblem;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ProblemsApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private QualifierProblem qualifierProblem;

    @Test
    void testQualifier(){
        //qualifierProblem.toSay();
        qualifierProblem.list();
    }
}
