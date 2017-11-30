package com.test.demo;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import static org.apache.lucene.document.StringField.TYPE_STORED;

/**
 * Created by Ryan Miao on 11/29/17.
 */
public class CreateIndex {

    public static void main(String[] args) throws IOException {
        //定义IndexWriter
        //index是一个相对路径，当前工程
        Path path = FileSystems.getDefault().getPath("", "index");

        Directory directory = FSDirectory.open(path);
        //定义分词器
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer).setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        //定义文档
        Document document = new Document();
        //定义文档字段
        document.add(new TextField("id", "5499", Field.Store.YES));
        document.add(new TextField("title", "红米5高配版", Field.Store.YES));
        document.add(new TextField("sellPoint", "I like use xiaomi shouji.", Field.Store.YES));
        //写入数据
        indexWriter.addDocument(document);
        //添加新的数据
        document = new Document();
        document.add(new TextField("id", "123", Field.Store.YES));
        document.add(new TextField("title", "小米6", Field.Store.YES));
        document.add(new TextField("sellPoint", "Do you like xiaomi", Field.Store.YES));
        indexWriter.addDocument(document);
        //添加新的数据
        document = new Document();
        document.add(new TextField("id", "123", Field.Store.YES));
        document.add(new TextField("title", "小米6", Field.Store.YES));
        document.add(new TextField("sellPoint", "Do you like xiaomi", Field.Store.YES));
        indexWriter.addDocument(document);
        //添加新的数据
        document = new Document();
        document.add(new TextField("id", "1", Field.Store.YES));
        document.add(new TextField("title", "小米Note", Field.Store.YES));
        document.add(new TextField("sellPoint", "Do you like xiaomi", Field.Store.YES));
        indexWriter.addDocument(document);
        //添加新的数据
        document = new Document();
        document.add(new TextField("id", "2", Field.Store.YES));
        document.add(new TextField("title", "小米Note2", Field.Store.YES));
        document.add(new TextField("sellPoint", "Do you like xiaomi", Field.Store.YES));
        indexWriter.addDocument(document);
        //添加新的数据
        document = new Document();
        document.add(new TextField("id", "3", Field.Store.YES));
        document.add(new TextField("title", "小米Note3", Field.Store.YES));
        document.add(new TextField("sellPoint", "Do you like xiaomi", Field.Store.YES));
        indexWriter.addDocument(document);
        //添加新的数据
        document = new Document();
        document.add(new TextField("id", "4", Field.Store.YES));
        document.add(new TextField("title", "小米Mix高配版", Field.Store.YES));
        document.add(new TextField("sellPoint", "Do you like xiaomi", Field.Store.YES));
        indexWriter.addDocument(document);
        //添加新的数据
        document = new Document();
        document.add(new TextField("id", "5", Field.Store.YES));
        document.add(new TextField("title", "小米Mix2尊享版", Field.Store.YES));
        document.add(new TextField("sellPoint", "Do you like xiaomi Mix2++", Field.Store.YES));
        indexWriter.addDocument(document);
        //添加新的数据
        document = new Document();
        document.add(new TextField("id", "6", Field.Store.YES));
        document.add(new TextField("title", "小米Mix2标准版", Field.Store.YES));
        document.add(new TextField("sellPoint", "Do you like xiaomi Mix2", Field.Store.YES));
        indexWriter.addDocument(document);
        //添加新的数据
        document = new Document();
        document.add(new TextField("id", "22", Field.Store.YES));
        document.add(new TextField("title", "小米Note3高配版", Field.Store.YES));
        document.add(new TextField("sellPoint", "Do you like xiaomi Note3++", Field.Store.YES));
        indexWriter.addDocument(document);
        document = new Document();
        document.add(new TextField("id", "8324", Field.Store.YES));
        document.add(new TextField("title", "OnePlus5", Field.Store.YES));
        document.add(new TextField("sellPoint", "Hello, no, I want he.", Field.Store.YES));
        indexWriter.addDocument(document);
        //提交
        indexWriter.commit();
        //关闭
        indexWriter.close();

    }

}
