simhash-java
============

A simple implementation of simhash algorithm by java, managed by Maven.

### Features:<br />  
1. compute the simhash of a string<br />  
2. compute the similarity between two strings by hamming distance<br />  

### How to build:
- mvn package // build the jar and run the unit tests<br />  

### How to use:
```java
IWordSeg wordSeg = new ChineseWordSeg(); // jieba analyzer for Chinese docs
Simhash simhash = new Simhash(wordSeg);

long hash1 = simhash.simhash64("doc1");
long hash2 = simhash.simhash64("doc2");

int dist = simhash.hammingDistance(hash1, hash2);
boolean nearDuplicate = dist <= 3;
```
<br />  

### Dependencies:
- jieba-analysis 1.0.2 (chinese word segmentation)<br />  

### Future:
1. Improve the performace under big data.<br />  

### Note:
1. ChineseWordSeg(结巴分词) is the recommended analyzer for Chinese docs.<br />  
2. Run unit tests with mvn test (see src/test/java).<br />  
