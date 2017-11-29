package com.test;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Created by MoSon on 2017/7/1.
 */
public class Search {

    public static void main(String[] args) throws IOException, ParseException {
        //定义索引目录
        Path path = FileSystems.getDefault().getPath("index");
        Directory directory = FSDirectory.open(path);
        //定义索引查看器
        IndexReader indexReader = DirectoryReader.open(directory);
        //定义搜索器
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //搜索内容
        //定义查询字段
        Analyzer standardAnalyzer = new CJKAnalyzer();
        QueryParser parser = new QueryParser("title", standardAnalyzer);
        Query query = parser.parse("红米");
//        Term term = new Term("title","I like you");
//        Query query = new FuzzyQuery(term);

        //命中前10条文档
        TopDocs topDocs = indexSearcher.search(query, 10);
        //打印命中数
        System.out.println("命中数：" + topDocs.totalHits);
        //取出文档
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //遍历取出数据
        for (ScoreDoc scoreDoc : scoreDocs) {
            //通过indexSearcher的doc方法取出文档
            Document doc = indexSearcher.doc(scoreDoc.doc);
            System.out.println("id:" + doc.get("id"));
            System.out.println("sellPoint:" + doc.get("sellPoint"));
            System.out.println("title:" + doc.get("title"));
        }

        //关闭索引查看器
        indexReader.close();
    }
}