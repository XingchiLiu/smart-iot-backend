package com.example.iot.repository.analysis.mapper;

import com.example.iot.domain.analysis.Operator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OperatorMapperTest {
    @Resource
    private OperatorMapper operatorMapper;

    @Test
    void getAllOperator() {
        List<Operator> operators = operatorMapper.getAllOperator();
        assertNotNull(operators);
        if (operators.size() != 0) {
            Operator operator = operators.get(0);
            assertNotNull(operator.getOperatorId());
            assertNotNull(operator.getName());
            assertNotNull(operator.getDescription());
            assertNotNull(operator.getFuncName());
            assertNotNull(operator.getJsCode());
        }
    }

    @Test
    void getOperatorById() {
        String name = "operator-test";
        String description = "operator demo";
        String jsCode = "var func = function() { return 1; }";
        String funcName = "func";
        Operator operator = new Operator(null, name, description, jsCode, funcName);

        Integer affectedLines = operatorMapper.insertOperator(operator);
        assertEquals(affectedLines, 1);

        Operator actualOperator = operatorMapper.getOperatorById(operator.getOperatorId());
        assertEquals(operator, actualOperator);
    }

    @Test
    void insertOperator() {
        String name = "operator-test";
        String description = "operator demo";
        String jsCode = "var func = function() { return 1; }";
        String funcName = "func";
        Operator operator = new Operator(null, name, description, jsCode, funcName);
        operatorMapper.insertOperator(operator);

        Integer operatorId = operator.getOperatorId();
        assertNotNull(operatorId);
        Operator actualOperator = operatorMapper.getOperatorById(operatorId);
        assertEquals(operator, actualOperator);
    }

    @Test
    void updateOperator() {
        String name = "operator-test";
        String description = "operator demo";
        String jsCode = "var func = function() { return 1; }";
        String funcName = "func";
        Operator operator = new Operator(null, name, description, jsCode, funcName);
        operatorMapper.insertOperator(operator);

        Integer operatorId = operator.getOperatorId();
        assertNotNull(operatorId);
        Operator actualOperator = operatorMapper.getOperatorById(operatorId);
        assertEquals(operator, actualOperator);

        operator.setDescription("");
        Integer affectedLines = operatorMapper.updateOperator(operator);
        assertEquals(affectedLines, 1);
    }

    @Test
    void deleteOperator() {
        String name = "operator-test";
        String description = "operator demo";
        String jsCode = "var func = function() { return 1; }";
        String funcName = "func";
        Operator operator = new Operator(null, name, description, jsCode, funcName);
        operatorMapper.insertOperator(operator);

        Integer operatorId = operator.getOperatorId();
        assertNotNull(operatorId);
        Operator actualOperator = operatorMapper.getOperatorById(operatorId);
        assertEquals(operator, actualOperator);

        Integer affectedLines = operatorMapper.deleteOperator(operatorId);
        assertEquals(affectedLines, 1);
    }
}