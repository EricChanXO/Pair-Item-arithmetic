package com.cyqwsj.arithmetic.controller;

import com.cyqwsj.arithmetic.constant.CommandConstant;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Scanner;

/**
 * @author cyq
 * @date 2020/10/11 9:32
 */
@Component
public class MainController {

    @Resource
    private GenerateController generateController;

    private final Scanner in = new Scanner(System.in);

    /**
     * 主控制台
     */
    public void mainConsole(){
        String command;
        do {
            command = in.nextLine();
            handler(command);
        } while (!"q".equals(command.replaceAll("\\s+", "")));
    }

    /**
     * 命令处理分派方法
     *
     * @param command 命令
     */
    public void handler(String command) {
        Assert.notNull(command, "命令不能为空");
        if (!command.contains(CommandConstant.MY_APP)) {
            System.out.println("Unknown command!");
            return;
        }
        if (command.contains(CommandConstant.NUMBER_VALUE) && command.contains(CommandConstant.RANGE_VALUE)) {
            //生成题目
            generateController.generateToFile(command);
        } else if (command.contains(CommandConstant.NUMBER_VALUE)) {
            //必须指定-r
            System.out.println("missing parameter '-r'!");
            System.out.println("--------------------------");
        } else if (command.contains(CommandConstant.RANGE_VALUE)) {
            //必须指定-n
            System.out.println("missing parameter '-n'!");
            System.out.println("--------------------------");
        } else if (command.contains(CommandConstant.CHECK_FILE_VALUE)) {
            //必须指定-a
            System.out.println("missing parameter '-a'!");
            System.out.println("--------------------------");
        } else if (command.contains(CommandConstant.CHECK_ANSWER_VALUE)) {
            //必须指定-e
            System.out.println("missing parameter '-e'!");
            System.out.println("--------------------------");
        }
    }
}