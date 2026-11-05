/**
 * 
 */
package simhash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Simhash 指纹计算与汉明距离测试。
 * 
 * @author zhangcheng
 *
 */
class SimhashTest {

	private static final Simhash SIMHASH = new Simhash(new ChineseWordSeg());

	@Test
	void hammingDistanceShouldCountDifferentBits() {
		assertEquals(0, SIMHASH.hammingDistance(0L, 0L));
		assertEquals(1, SIMHASH.hammingDistance(0L, 1L));
		assertEquals(2, SIMHASH.hammingDistance(3L, 0L));
		assertEquals(64, SIMHASH.hammingDistance(-1L, 0L));

		assertEquals(0, SIMHASH.hammingDistance(0x0f0f0f0f, 0x0f0f0f0f));
		assertEquals(32, SIMHASH.hammingDistance(0x0000ffff, 0xffff0000));
	}

	@Test
	void sameDocumentShouldProduceSameHash() {
		String doc = "近年来，人工智能技术在医疗领域的应用日益广泛，显著提升了诊疗效率。";
		long hash1 = SIMHASH.simhash64(doc);
		long hash2 = SIMHASH.simhash64(doc);
		assertEquals(hash1, hash2);
		assertEquals(0, SIMHASH.hammingDistance(hash1, hash2));
	}

	@Test
	void sameDocumentShouldProduceSame32BitHash() {
		String doc = "近年来，人工智能技术在医疗领域的应用日益广泛，显著提升了诊疗效率。";
		assertEquals(SIMHASH.simhash32(doc), SIMHASH.simhash32(doc));
	}

	@Test
	void nearDuplicateShouldBeCloseButUnrelatedShouldBeFar() throws IOException {
		List<String> docs = readTestArticles();
		long origin = SIMHASH.simhash64(docs.get(0));
		long nearDuplicate = SIMHASH.simhash64(docs.get(1));
		long unrelated = SIMHASH.simhash64(docs.get(3));

		assertTrue(SIMHASH.hammingDistance(origin, nearDuplicate) <= 3,
				"近重复文章的距离应不大于3");
		assertTrue(SIMHASH.hammingDistance(origin, unrelated) > 3,
				"无关文章的距离应大于3");
	}

	private static List<String> readTestArticles() throws IOException {
		return Files.readAllLines(Paths.get("src/test/resources/test_articles.txt"), StandardCharsets.UTF_8);
	}

}
