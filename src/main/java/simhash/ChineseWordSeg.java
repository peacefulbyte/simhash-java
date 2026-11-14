/**
 * 
 */
package simhash;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.huaban.analysis.jieba.JiebaSegmenter;

/**
 * 中文分词器：基于结巴分词（jieba-analysis）。
 * 
 * 该实现按词语切分中文文本，避免二元切分割裂词语，
 * 例如 "中华人民共和国" 被切成 "中华/华人/人民/民共/共和/和国" 这类
 * 跨词边界的噪音 token，从而提升中文文本 simhash 指纹的准确率。
 * 
 * 依赖：com.huaban:jieba-analysis:1.0.2（词典内置在 jar 中，开箱即用）
 * 
 * @author zhangcheng
 * 
 */
public class ChineseWordSeg implements IWordSeg {

	private JiebaSegmenter segmenter = new JiebaSegmenter();

	@Override
	public List<String> tokens(String doc) {
		return tokens(doc, null);
	}

	@Override
	public List<String> tokens(String doc, Set<String> stopWords) {
		List<String> tokens = new ArrayList<String>();
		for (String t : segmenter.sentenceProcess(doc)) {
			// 空白 token 必须丢弃：正文去标点后残留的空格会被切成独立 token，
			// 一篇稿件里重复出现几十次，权重会压倒正文词，导致无关稿件的
			// simhash 指纹几乎相同（两篇不同主题的稿件汉明距离仅 1）。
			if (t == null || t.trim().isEmpty()) {
				continue;
			}
			if (stopWords == null || !stopWords.contains(t)) {
				tokens.add(t);
			}
		}
		return tokens;
	}

}

