/**
 * 
 */
package simhash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * MurmurHash 基础行为测试。
 * 
 * @author zhangcheng
 *
 */
class MurmurHashTest {

	@Test
	void hash64ShouldBeDeterministic() {
		assertEquals(MurmurHash.hash64("hello world"), MurmurHash.hash64("hello world"));
		assertEquals(MurmurHash.hash64("你好，世界"), MurmurHash.hash64("你好，世界"));
	}

	@Test
	void hash32ShouldBeDeterministic() {
		assertEquals(MurmurHash.hash32("hello world"), MurmurHash.hash32("hello world"));
		assertEquals(MurmurHash.hash32("你好，世界"), MurmurHash.hash32("你好，世界"));
	}

	@Test
	void differentInputsShouldProduceDifferentHash() {
		assertNotEquals(MurmurHash.hash64("hello"), MurmurHash.hash64("hellp"));
		assertNotEquals(MurmurHash.hash32("hello"), MurmurHash.hash32("hellp"));
	}

	@Test
	void emptyStringShouldHashToZero() {
		assertEquals(0L, MurmurHash.hash64(""));
		assertEquals(0, MurmurHash.hash32(""));
	}

}
