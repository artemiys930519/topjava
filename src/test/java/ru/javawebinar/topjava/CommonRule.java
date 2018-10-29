package ru.javawebinar.topjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.HashMap;
import java.util.Map;

public class CommonRule implements TestRule {
    public static Map<Long, String> testMap = new HashMap();
    private static Long start;

    @Override
    public Statement apply(Statement statement, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                start = System.currentTimeMillis();
                statement.evaluate();
                long testTime = System.currentTimeMillis() - start;
                System.out.println("Время выполнения теста: " + testTime + " ms.");
                testMap.put(testTime, description.getMethodName());
            }
        };
    }
}
