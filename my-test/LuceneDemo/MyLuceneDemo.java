package LuceneDemo;

import jdk.internal.org.objectweb.asm.tree.analysis.Analyzer;
import org.apache.tomcat.jni.Directory;

import javax.management.Query;
import javax.swing.text.Document;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author zhangs
 * @Description
 * @createDate 2020/4/3
 */
public class MyLuceneDemo {
    public static void main(String[] args) {

    }

    public static void testLucene() throws Exception {
        /*String longTerm = "longtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongtermlongterm";
        String text = "This is the text to be indexed:" + longTerm;

        *//**
         * 初始化索引库位置
         *//*
        Path indexPath = Files.createTempDirectory("tempIndex");
        System.out.println(indexPath.toString());

        *//**
         * 打开索引存储位置
         *//*
        try (Directory dir = FSDirectory.open(indexPath)) {
            Analyzer analyzer = new StandardAnalyzer(CharArraySet.EMPTY_SET);
            // 指定分词器为StandardAnalyzer
            try (IndexWriter iw = new IndexWriter(dir, new IndexWriterConfig(analyzer))) {
                // 创建文档
                Document doc = new Document();
                // 这里的StringField，根据该类的注释，作用是创建一个Field，将fieldname作为一个token
                // Field.Store表示是否在索引中存储原始的域值
                // Field.Store.YES:在查询结果里显示域值（比如文章标题）
                // Field.Store.NO:不需要显示域值（比如文章内容）
                doc.add(new StringField("fieldname", text, Field.Store.YES));
                // 写入文件，创建索引库
                iw.addDocument(doc);
            }

            // 检索.
            try (IndexReader reader = DirectoryReader.open(dir)) {
                IndexSearcher searcher = new IndexSearcher(reader);
                // 根据分词内容查询所以文件，并返回一个结果
                assert 1 == searcher.count(new TermQuery(new Term("fieldname", longTerm)));

                // 按照单个分词查询
                Query query = new TermQuery(new Term("fieldname", "text"));
                // 通过IndexSearcher按照分词查询命中的所有结果
                TopDocs hits = searcher.search(query, 1);
                assert 1 == hits.totalHits;

                // 遍历按照分词查询命中的所有结果
                for (int i = 0; i < hits.scoreDocs.length; i++) {
                    Document hitDoc = searcher.doc(hits.scoreDocs[i].doc);
                    assert text.equals(hitDoc.get("fieldname"));
                }

                // 多关键词查询：根据给出的分词列表，查询所有命中的结果
                PhraseQuery phraseQuery = new PhraseQuery("fieldname", "to", "be");
                assert 1 == searcher.count(phraseQuery);
            }
        }

        IOUtils.rm(indexPath);*/
    }
}
