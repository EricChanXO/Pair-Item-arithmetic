package com.cyqwsj.arithmetic.controller;

import com.cyqwsj.arithmetic.constant.CommandConstant;
import com.cyqwsj.arithmetic.service.GenerateToFile;
import com.cyqwsj.arithmetic.service.Generator;
import com.cyqwsj.arithmetic.service.impl.Expression;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 生成器控制类
 *
 * @author cyq
 * @date 2020/10/11 9:38
 */
@Component
public class GenerateController {

    @Resource
    private Generator generator;

    @Resource
    private GenerateToFile generateToFile;

    private static final String REG = "([^<>/\\\\|:\"\"\\*\\?]+)\\.\\w+$+";

    /**
     * 生成题目以及答案并输出文件到当前目录下
     *
     * @param command 命令
     */
    public void generateToFile(String command) {
        int totalNum = 0;
        int rangeNum = 0;
        if (command == null) {
            return;
        }
        String[] commands = command.split("\\s+");
        if (commands.length != 5) {
            System.out.println("错误命令！");
            return;
        }
        for (int i = 0; i < commands.length; i++) {
            // -n 数量
            if (CommandConstant.NUMBER_VALUE.equals(commands[i])) {
                totalNum = Integer.parseInt(commands[i + 1]);
            }
            // -r 数量
            if (CommandConstant.RANGE_VALUE.equals(commands[i])) {
                rangeNum = Integer.parseInt(commands[i + 1]);
            }
        }
        List<Expression> expressions = generator.generate(totalNum, rangeNum);
        Assert.isTrue(Objects.nonNull(expressions), "生成题目失败");
        //默认生成到当前目录下的Exercises.txt Answers.txt
        generateToFile.generatorToFile(expressions, "Exercises.txt", "Answers.txt");
        System.out.println("生成成功！");
    }

    /**
     * 对给定的题目文件和答案文件，判定答案中的对错并进行数量统计
     *
     * @param command 命令
     */
    public void checkQuestion(String command) {
        String exercisePath = null;
        String answerPath = null;
        if (command == null) {
            return;
        }
        String[] strings = command.split("\\s+");
        if (strings.length != 5) {
            System.out.println("错误命令！");
            return;
        }
        for (int i = 0; i < strings.length; i++) {
            if (CommandConstant.CHECK_FILE_VALUE.equals(strings[i])) {
                exercisePath = strings[i + 1];
            } else if (CommandConstant.CHECK_ANSWER_VALUE.equals(strings[i])) {
                answerPath = strings[i + 1];
            }
        }
        if (!pathIsLegal(exercisePath) || !pathIsLegal(answerPath)) {
            System.out.println("文件路径不合法");
            return;
        }
        if (!pathExist(exercisePath) || !pathExist(answerPath)) {
            System.out.println("文件路径不存在");
            return;
        }
        generateToFile.checkAnswer(exercisePath, answerPath, "Grade.txt");
        System.out.println("校验完成！");
    }

    /**
     * 判断路径是否正确
     *
     * @param path 路径
     * @return boolean
     */
    private boolean pathIsLegal(String path) {
        Matcher matcher = Pattern.compile(REG).matcher(path);
        return matcher.find();
    }

    /**
     * 判断文件是否存在
     *
     * @param path 路径
     * @return boolean
     */
    private boolean pathExist(String path) {
        File file = new File(path);
        return file.exists();
    }

}