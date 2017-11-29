package com.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoSon on 2017/7/5.
 */
public class AnalyzerResult {

    /**
     * 获取指定分词器的分词结果
     *
     * @param analyzeStr 要分词的字符串
     * @param analyzer   分词器
     * @return 分词结果
     */
    public List<String> getAnalyseResult(String analyzeStr, Analyzer analyzer) {
        List<String> response = new ArrayList<String>();
        TokenStream tokenStream = null;
        try {
            //返回适用于fieldName的TokenStream，标记读者的内容。
            tokenStream = analyzer.tokenStream("address", new StringReader(analyzeStr));
            // 语汇单元对应的文本
            CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
            //消费者在使用incrementToken（）开始消费之前调用此方法。
            //将此流重置为干净状态。 有状态的实现必须实现这种方法，以便它们可以被重用，就像它们被创建的一样。
            tokenStream.reset();
            //Consumers（即IndexWriter）使用此方法将流推送到下一个令牌。
            while (tokenStream.incrementToken()) {
                response.add(attr.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (tokenStream != null) {
                try {
                    tokenStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    public static void main(String[] args) {
        List<String> analyseResult = new AnalyzerResult().getAnalyseResult("I like xiaomi shouji", new StandardAnalyzer());
        for (String result : analyseResult) {
            System.out.println(result);
        }
    }
}