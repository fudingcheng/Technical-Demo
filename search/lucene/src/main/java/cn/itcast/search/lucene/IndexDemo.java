package cn.itcast.search.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * Created by fudingcheng on 2018-08-30.
 */
public class IndexDemo {

    @Test
    public void createIndex() throws Exception {
        //创建文档对象
        Document document = new Document();
        //添加字段
        document.add(new LongField("range",1, Field.Store.YES));
        document.add(new StringField("id","6", Field.Store.YES));
        document.add(new TextField("title","传智播客付鼎程碉堡了", Field.Store.YES));
        document.add(new TextField("content","传智播客西安中心", Field.Store.YES));
        //创建目录对象,指定索引库存放的位置
        Directory directory = FSDirectory.open(new File("C:\\Users\\fudingcheng\\Desktop\\indexDir"));
        //创建分词器对象
        Analyzer analyzer = new IKAnalyzer();
        //创建分词器写入器配置对象
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LATEST,analyzer);
        //创建索引写入器
        IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);
        //向索引库写入文档
        indexWriter.addDocument(document);
        //写入器提交
        indexWriter.commit();
        //写入器关闭
        indexWriter.close();
    }


    @Test
    public void testSearch() throws IOException, ParseException {
        //初始化索引库对象
        Directory directory = FSDirectory.open(new File("C:\\Users\\fudingcheng\\Desktop\\indexDir"));
        //创建索引读取工具
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建索引搜索工具
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //创建查询解析器
        //QueryParser parser = new QueryParser("title",new IKAnalyzer());
        //同时查询多个字段的解析器,即查询title和Content
        MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[]{"title","content"},new IKAnalyzer());
        //解析查询条件
        Query query = parser.parse("传智播客");
        //执行搜索,返回值topDocs包含命中数,得分文档
        TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE);
        System.out.println("一共命中:"+topDocs.totalHits+"条数据");
        //获得命中的文档数组对象,包含文档的得分和编号
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //遍历命中的文档数组对象
        for(ScoreDoc scoreDoc: scoreDocs){
            //得分
            System.out.println("得分:"+scoreDoc.score);
            int doc = scoreDoc.doc;
            //文档编号
            System.out.println("文档编号:"+doc);
            //根据编号可以获得文档详情
            Document document = indexReader.document(doc);
            System.out.println("id:"+document.get("id"));
            System.out.println("title:"+document.get("title"));
        }

    }



    public void baseSearch(Query query) throws IOException, ParseException {
        //初始化索引库对象
        Directory directory = FSDirectory.open(new File("C:\\Users\\fudingcheng\\Desktop\\indexDir"));
        //创建索引读取工具
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建索引搜索工具
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //执行搜索,返回值topDocs包含命中数,得分文档
        TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE);
        System.out.println("一共命中:"+topDocs.totalHits+"条数据");
        //获得命中的文档数组对象,包含文档的得分和编号
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //遍历命中的文档数组对象
        for(ScoreDoc scoreDoc: scoreDocs){
            //得分
            System.out.println("得分:"+scoreDoc.score);
            int doc = scoreDoc.doc;
            //文档编号
            System.out.println("文档编号:"+doc);
            //根据编号可以获得文档详情
            Document document = indexReader.document(doc);
            System.out.println("id:"+document.get("id"));
            System.out.println("title:"+document.get("title"));
        }
    }

    //词条查询
    @Test
    public void teamQuery() throws IOException, ParseException {
        //词条查询:在title字段中查询"付鼎程",并且条件不分词
        Term term = new Term("title","付鼎程");
        TermQuery query = new TermQuery(term);
        baseSearch(query);
    }


    @Test
    public void wildcardQuery() throws IOException, ParseException {
        /**
         * 词条通配符查询
         * *:代表N个字符
         * ?:代表1个字符
         */
        Term term = new Term("title","传智?客");
        Query query = new WildcardQuery(term);
        baseSearch(query);
    }

    @Test
    public void fuzzyQueryTest() throws IOException, ParseException {
        //确定查询的字段和条件
        Term term = new Term("title","facebo");
        //模糊查询,字段的偏移值最多为2;默认的偏移值为0-2
        Query query = new FuzzyQuery(term,2);   //facebook-facebo = 2
        baseSearch(query);
    }


    @Test
    public void numberQuery() throws IOException, ParseException {
        //查询id字段,范围是1-5,即左包含同时右包含
        NumericRangeQuery query = NumericRangeQuery.newLongRange("range",1L,2L,true,true);
        baseSearch(query);
    }

    @Test
    public void booleanQuery() throws IOException, ParseException {
        //创建boolean查询对象
        BooleanQuery query = new BooleanQuery();
        //条件1:数值范围查询
        NumericRangeQuery rangeQuery = NumericRangeQuery.newLongRange("range",1L,2L,true,true);

        //条件2:通配符查询
        Term term = new Term("title","传智?客");
        WildcardQuery wildcardQuery = new WildcardQuery(term);

        //组合条件
        // 交集： must+must = A和B之间共同的部分
        // 并集： should + should = A和B的结果集合并 (or)
        query.add(rangeQuery, BooleanClause.Occur.MUST);
        query.add(wildcardQuery, BooleanClause.Occur.MUST);

        baseSearch(query);
    }

    @Test
    public void updateIndex() throws IOException {
        // 创建文档对象
        Document document = new Document();
        document.add(new StringField("id", "9", Field.Store.YES));
        document.add(new TextField("title", "谷歌地图之父跳槽Google", Field.Store.YES));

        //指定索引的位置
        Directory directory = FSDirectory.open(new File("C:\\Users\\fudingcheng\\Desktop\\indexDir"));
        // 索引写入器配置对象
        IndexWriterConfig conf = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
        // 索引写入器对象
        IndexWriter indexWriter = new IndexWriter(directory, conf);

        //更新id为1的文档
        indexWriter.updateDocument(new Term("id","1"),document);

        //提交,关闭流
        indexWriter.commit();
        indexWriter.close();
    }

    @Test
    public void deleteIndex() throws IOException {
        //指定索引的位置
        Directory directory = FSDirectory.open(new File("C:\\Users\\fudingcheng\\Desktop\\indexDir"));
        // 索引写入器配置对象
        IndexWriterConfig conf = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
        // 索引写入器对象
        IndexWriter indexWriter = new IndexWriter(directory, conf);
        //删除id=9的文档
        indexWriter.deleteDocuments(new Term("id","9"));
        //提交,关闭流
        indexWriter.commit();
        indexWriter.close();
    }

    @Test
    public void testHighLighter() throws IOException, ParseException, InvalidTokenOffsetsException {
        //指定索引的位置
        Directory directory = FSDirectory.open(new File("C:\\Users\\fudingcheng\\Desktop\\indexDir"));
        //创建IndexReader读取索引文件夹
        IndexReader reader = DirectoryReader.open(directory);
        //创建搜索对象
        IndexSearcher searcher = new IndexSearcher(reader);
        //创建解析器,解析title字段
        QueryParser parser = new QueryParser("title",new IKAnalyzer());
        //解析查询条件,获得query对象
        Query query = parser.parse("传智播客");
        //创建高亮工具,指定高亮的标签
        Formatter formatter = new SimpleHTMLFormatter("<em>","</em>");
        //创建QueryScore对象
        QueryScorer scorer = new QueryScorer(query);
        //创建高亮工具
        Highlighter highlighter = new Highlighter(formatter,scorer);
        //指定搜索
        TopDocs topDocs = searcher.search(query, 10);
        //获得命中的文档数组对象,包含文档的得分和编号
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        System.out.println("一共命中:"+topDocs.totalHits+"条数据");
        //遍历命中的文档数组对象
        for(ScoreDoc scoreDoc: scoreDocs){
            //得分
            System.out.println("得分:"+scoreDoc.score);
            int doc = scoreDoc.doc;
            //文档编号
            System.out.println("文档编号:"+doc);
            //根据编号可以获得文档详情
            Document document = reader.document(doc);
            //获得title,处理高亮
            String title = document.get("title");
            //用高亮工具处理title 参数:分词器   高亮字段的名称 高亮字段的原始值
            String highlighterTitle = highlighter.getBestFragment(new IKAnalyzer(), "title", title);

            System.out.println(highlighterTitle);

            System.out.println("id:"+document.get("id"));
            System.out.println("title:"+document.get("title"));
        }

    }

    @Test
    public void testOrder() throws IOException, ParseException, InvalidTokenOffsetsException {
        //初始化索引库对象
        Directory directory = FSDirectory.open(new File("C:\\Users\\fudingcheng\\Desktop\\indexDir"));
        //创建索引读取工具
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建索引搜索工具
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //创建查询解析器
        //QueryParser parser = new QueryParser("title",new IKAnalyzer());
        //同时查询多个字段的解析器,即查询title和Content
        MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[]{"title","content"},new IKAnalyzer());
        //解析查询条件
        Query query = parser.parse("传智播客");
        //创建排序对象
        Sort sort = new Sort(new SortField("id", SortField.Type.STRING_VAL,false));
        //执行搜索,返回值topDocs包含命中数,得分文档
        TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE,sort);
        System.out.println("一共命中:"+topDocs.totalHits+"条数据");
        //获得命中的文档数组对象,包含文档的得分和编号
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //遍历命中的文档数组对象
        for(ScoreDoc scoreDoc: scoreDocs){
            //得分
            System.out.println("得分:"+scoreDoc.score);
            int doc = scoreDoc.doc;
            //文档编号
            System.out.println("文档编号:"+doc);
            //根据编号可以获得文档详情
            Document document = indexReader.document(doc);
            System.out.println("id:"+document.get("id"));
            System.out.println("title:"+document.get("title"));
        }
    }

    @Test
    public void testSearchPage() throws IOException, ParseException, InvalidTokenOffsetsException {
        //初始化索引库对象
        Directory directory = FSDirectory.open(new File("C:\\Users\\fudingcheng\\Desktop\\indexDir"));
        //创建索引读取工具
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建索引搜索工具
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //创建查询解析器
        //QueryParser parser = new QueryParser("title",new IKAnalyzer());
        //同时查询多个字段的解析器,即查询title和Content
        MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[]{"title","content"},new IKAnalyzer());
        //解析查询条件
        Query query = parser.parse("传智播客");
        //创建排序对象
        Sort sort = new Sort(new SortField("id", SortField.Type.STRING_VAL,false));
        //每页显示记录数
        int pageSize = 1;
        //每页大小
        int pageCount = 10;
        //从哪里开始查
        int start = (pageSize-1)*pageCount;
        //查询到什么位置
        int end = pageSize*pageCount;
        //执行搜索,返回值topDocs包含命中数,得分文档;  查询end条数据
        TopDocs topDocs = indexSearcher.search(query, end,sort);
        System.out.println("一共命中:"+topDocs.totalHits+"条数据");
        //获得命中的文档数组对象,包含文档的得分和编号
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //遍历命中的文档数组对象,从start开始遍历到最后
        for(int i = start;i<scoreDocs.length;i++){
            ScoreDoc scoreDoc = scoreDocs[i];
            //得分
            System.out.println("得分:"+scoreDoc.score);
            int doc = scoreDoc.doc;
            //文档编号
            System.out.println("文档编号:"+doc);
            //根据编号可以获得文档详情
            Document document = indexReader.document(doc);
            System.out.println("id:"+document.get("id"));
            System.out.println("title:"+document.get("title"));
        }
    }



}
