package com.cyqwsj.arithmetic.service;

import com.cyqwsj.arithmetic.service.impl.Expression;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wsj
 * @date 2020/10/9 22:54
 */
@Component
public class GenerateToFile {

    /**
     * 将题目和答案输出到指定路径
     *
     * @param expressionList 题目列表
     * @param exercisePath   问题文件的路径
     * @param answerPath     答案文件的路径
     */
    public void generatorToFile(List<Expression> expressionList, String exercisePath, String answerPath) {
        File questionFile = new File(exercisePath);
        File answerFile = new File(answerPath);

        BufferedWriter questionWriter = null;
        BufferedWriter answerWriter = null;
        try {
            questionWriter = new BufferedWriter(new FileWriter(questionFile));
            answerWriter = new BufferedWriter(new FileWriter(answerFile));

            for (int i = 0; i < expressionList.size(); i++) {
                questionWriter.write((i + 1) + ". " + expressionList.get(i).printQuestion());
                questionWriter.newLine();
                questionWriter.flush();

                answerWriter.write((i + 1) + ". " + expressionList.get(i).getAnswer().toString());
                answerWriter.newLine();
                answerWriter.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (answerWriter != null) {
                answerWriter.close();
            }
            if (questionWriter != null) {
                questionWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断答案是否正确并输出到文件
     *
     * @param exercisePath 已经有答案的问题文件
     * @param answerPath   答案文件
     * @param judgePath    结果
     */
    public void checkAnswer(String exercisePath, String answerPath, String judgePath) {
        File questionFile = new File(exercisePath);
        File answerFile = new File(answerPath);
        File checkAnswerFile = new File(judgePath);
        if (!questionFile.exists() || !answerFile.exists()) {
            throw new RuntimeException("文件路径错误");
        }
        BufferedReader questionReader = null;
        BufferedReader answerReader = null;
        BufferedWriter judgeWriter = null;
        try {
            questionReader = new BufferedReader(new FileReader(questionFile));
            answerReader = new BufferedReader(new FileReader(answerFile));
            judgeWriter = new BufferedWriter(new FileWriter(checkAnswerFile));
            String questionLine;
            String answerLine;
            String yourAnswer;
            String questionCount;
            String[] answerStrings;
            List<String> correctList = new LinkedList<>();
            List<String> wrongList = new LinkedList<>();
            while ((questionLine = questionReader.readLine()) != null) {
                int begin = questionLine.indexOf('=');
                yourAnswer = questionLine.substring(begin + 1);
                yourAnswer = yourAnswer.trim();

                int countEnd = questionLine.indexOf('.');
                questionCount = questionLine.substring(0, countEnd);
                questionCount = questionCount.trim();

                answerLine = answerReader.readLine();
                answerLine = answerLine.trim();
                answerStrings = answerLine.split(" ");

                if (yourAnswer.equals(answerStrings[answerStrings.length - 1])) {
                    correctList.add(questionCount);
                } else {
                    wrongList.add(questionCount);
                }
            }
            StringBuilder correctStringBuilder = new StringBuilder();
            correctStringBuilder.append("Correct:").append(correctList.size()).append("( ");
            for (String s : correctList) {
                correctStringBuilder.append(s);
                correctStringBuilder.append(", ");
            }
            int index = correctStringBuilder.lastIndexOf(",");
            if (index == -1) {
                correctStringBuilder.append(")");
            } else {
                correctStringBuilder.replace(index, correctStringBuilder.length(), ")");
            }


            StringBuilder wrongStringBuilder = new StringBuilder();
            wrongStringBuilder.append("Wrong:").append(wrongList.size()).append("( ");
            for (String s : wrongList) {
                wrongStringBuilder.append(s);
                wrongStringBuilder.append(", ");
            }
            index = wrongStringBuilder.lastIndexOf(",");
            if (index == -1) {
                wrongStringBuilder.append(")");
            } else {
                wrongStringBuilder.replace(index, wrongStringBuilder.length(), ")");
            }

            judgeWriter.write(correctStringBuilder.toString());
            judgeWriter.newLine();
            judgeWriter.write(wrongStringBuilder.toString());
            judgeWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (judgeWriter != null) {
                judgeWriter.close();
            }
            if (answerReader != null) {
                answerReader.close();
            }
            if (questionReader != null) {
                questionReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}