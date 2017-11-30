package com.test.demo;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.FuzzyQuery;
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
 * 查询语法
 //使用query查询(创建queryparser，再通过queryparser创建query)
 // 1.创建Parse对象(设置默认搜索域为content)
 QueryParser parse = new QueryParser(Version.LUCENE_35, "content",new StandardAnalyzer(Version.LUCENE_35));
 // 改变空格的默认操作(改为AND型)
 parse.setDefaultOperator(Operator.AND);
 // 开启第一个字符的通配符匹配(*xxx,?xxx),默认关闭,因为效率比较低
 parse.setAllowLeadingWildcard(true);
 // 2.通过parse生成query(搜索content域中包含有like的)
 Query query = parse.parse("like");
 // 能够一直加条件(空格默认就是OR)
 query = parse.parse("basketball i");
 // 改变搜索域(域:值)
 query = parse.parse("name:mark");
 // 同样能进行*或？的通配符匹配(通配符默认不能放在首位)
 query = parse.parse("name:*i");
 // name中不包含mark,但是content中包含basketball(-和+必须放在域说明的前面)
 query = parse.parse("- name:mark + basketball");
 // id的1~3(TO表示一个闭区间,TO必须是大写的)
 query = parse.parse("id:[1 TO 3]");
 // {}表示1~3的开区间匹配
 query = parse.parse("id:{1 TO 3}");
 // name域值是lili或mark,默认域值是game
 query = parse.parse("name:(lili OR mark) AND game");
 // 两个‘’号表示短语匹配
 query = parse.parse("'i like basketball'");
 // 表示i basketball之间有一个单词遗漏的匹配
 query = parse.parse("\"i basketball\"~1");
 // 加个~就能模糊查询mark
 query = parse.parse("name:mirk~");
 // 没有办法匹配数字范围(自己扩展parse)
 query = parse.parse("attach:[1 TO 3]");
 sutil.SearchByQueryParse(query, 5);
 *
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
        //Proximity Searches邻近词查询,这两个单词中间可以有一部分内容（这部分内容通过字符个数限制）
        Analyzer standardAnalyzer = new StandardAnalyzer();
        QueryParser parser = new QueryParser("title", standardAnalyzer);
        Query query = parser.parse("小米 note~");
        print(indexSearcher, query);

        //支持搜索模糊词，如果想要搜索模糊词，需要在词语后面加上符号~,也支持在~后面添加模糊系数，模糊系数[0-1]，越靠近1表示越相近,默认模糊系数为0.5。
        Query query2 = parser.parse("noe~");
        print(indexSearcher, query2);

        //目录查找，最多可以错误几个字符
        Term term = new Term("title","Mix~");
        Query query3 = new FuzzyQuery(term,2);
        print(indexSearcher, query3);

        //限定符?
        QueryParser parser4 = new QueryParser("sellPoint", standardAnalyzer);
        Query query4 = parser4.parse("sho?ji");
        print(indexSearcher, query4);

        //通配符*
        QueryParser parser5 = new QueryParser("sellPoint", standardAnalyzer);
        Query query5 = parser5.parse("lik*");
        print(indexSearcher, query5);

        //关闭索引查看器
        indexReader.close();
    }

    private static void print(IndexSearcher indexSearcher, Query query) throws IOException {
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
            System.out.println("score:"+scoreDoc.score+";\t"+"id:" + doc.get("id")+";\t"+  "title:" + doc.get("title")+";\t"+   "sellPoint:" + doc.get("sellPoint"));
        }


        System.out.println("---------------分界线------------------");
    }
}