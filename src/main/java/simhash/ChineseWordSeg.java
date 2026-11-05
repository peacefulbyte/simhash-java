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
		return segmenter.sentenceProcess(doc);
	}

	@Override
	public List<String> tokens(String doc, Set<String> stopWords) {
		List<String> tokens = new ArrayList<String>();
		for (String t : segmenter.sentenceProcess(doc)) {
			if (stopWords == null || !stopWords.contains(t)) {
				tokens.add(t);
			}
		}
		return tokens;
	}

}
