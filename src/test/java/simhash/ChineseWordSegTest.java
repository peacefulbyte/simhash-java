/**
 * 
 */
package simhash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * 中文分词器测试。
 * 
 * @author zhangcheng
 *
 */
class ChineseWordSegTest {

	private final ChineseWordSeg wordSeg = new ChineseWordSeg();

	@Test
	void shouldSplitChineseIntoSemanticWords() {
		List<String> tokens = wordSeg.tokens("中华人民共和国万岁");
		assertTrue(tokens.contains("中华人民共和国"), "分词结果应包含完整词语: " + tokens);
	}

	@Test
	void tokensShouldBeDeterministic() {
		String doc = "今天天气很好，我们去公园散步吧。";
		assertEquals(wordSeg.tokens(doc), wordSeg.tokens(doc));
	}

	@Test
	void shouldFilterStopWords() {
		String doc = "天气不错，我们去公园散步";
		Set<String> stopWords = new HashSet<String>(Arrays.asList("天气"));
		List<String> filtered = wordSeg.tokens(doc, stopWords);

		assertFalse(filtered.contains("天气"), "过滤后不应包含停用词: " + filtered);
		assertEquals(wordSeg.tokens(doc).size() - 1, filtered.size());
	}

	@Test
	void nullStopWordsShouldNotFilter() {
		String doc = "今天天气很好";
		assertEquals(wordSeg.tokens(doc), wordSeg.tokens(doc, null));
	}

}
