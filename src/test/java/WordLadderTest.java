
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.*;
//import java.util.Stack;

/**
* WordLadder Tester. 
* 
* @author hyq
* @since <pre>March 8, 2018</pre>
* @version 1.0 
*/ 
public class WordLadderTest {
/** 
* 
* Method: getDic() 
* 
*/

@Test
public void getDicQuit() {
//   Method method = wordLadder.getClass().getMethod("getDic");
//   method.setAccessible(true);
    String fileName = "q";
    InputStream stdin = System.in;
    try {
        System.setIn(new ByteArrayInputStream(fileName.getBytes()));
//        method.invoke(wordLadder);
        Assert.assertFalse(WordLadder.getDic());
    } finally {
        System.setIn(stdin);
    }
}

@Test
public void getDicNormal(){
    String fileName = "dictionary.txt";
    InputStream stdin = System.in;
    try {
        System.setIn(new ByteArrayInputStream(fileName.getBytes()));
        Assert.assertTrue(WordLadder.getDic());
    } finally {
        System.setIn(stdin);
    }
}

/**
*
* Method: getAWord(String word)
*
*/
@Test
public void testGetAWordQuit() throws Exception {
    Assert.assertTrue(WordLadder.getAWord(""));
}
@Test
public void testGetAWordNormal() throws Exception {
    String fileName = "dictionary.txt";
    InputStream stdin = System.in;
    System.setIn(new ByteArrayInputStream(fileName.getBytes()));
    Assert.assertTrue(WordLadder.getDic());
    System.setIn(stdin);
    Assert.assertFalse(WordLadder.getAWord("azure"));
}
/** 
* 
* Method: checkWord(String word) 
* 
*/
@Rule
public ExpectedException expectedEx = ExpectedException.none();
@Test
public void testNotAWord() throws Exception {
    expectedEx.expect(Exception.class);
    expectedEx.expectMessage("Not a word!");
    WordLadder.checkWord("q2w");
} 

/** 
* 
* Method: checkHaveWord(String word) 
* 
*/

@Test
public void testCheckHaveWord() throws Exception {
    String fileName = "dictionary.txt";
    InputStream stdin = System.in;
    System.setIn(new ByteArrayInputStream(fileName.getBytes()));
    Assert.assertTrue(WordLadder.getDic());
    System.setIn(stdin);
    expectedEx.expect(Exception.class);
    expectedEx.expectMessage("The two words must be found in the dictionary.");
    WordLadder.checkWord("q");
}

/** 
* 
* Method: lengthCheck() 
* 
*/ 
@Test
public void testLengthCheck() throws Exception { 
    WordLadder.word1="fragile";
    WordLadder.word2="break";
    expectedEx.expect(Exception.class);
    expectedEx.expectMessage("The two words must be the same length.");
    WordLadder.lengthCheck();
} 

/** 
* 
* Method: sameCheck() 
* 
*/ 
@Test
public void testSameCheck() throws Exception {
    WordLadder.word1="paradox";
    WordLadder.word2="paradox";
    expectedEx.expect(Exception.class);
    expectedEx.expectMessage("The two words must be different.");
    WordLadder.sameCheck();
} 

/** 
* 
* Method: wordLadder() 
* 
*/ 
@Test
public void noLadder() {
    String fileName = "dictionary.txt";
    InputStream stdin = System.in;
    System.setIn(new ByteArrayInputStream(fileName.getBytes()));
    Assert.assertTrue(WordLadder.getDic());
    System.setIn(stdin);
    WordLadder.word1="sepia";
    WordLadder.word2="azure";
    Assert.assertFalse(WordLadder.wordLadder());
}

@Test
public void haveLadder() {
    String fileName = "dictionary.txt";
    InputStream stdin = System.in;
    System.setIn(new ByteArrayInputStream(fileName.getBytes()));
    Assert.assertTrue(WordLadder.getDic());
    System.setIn(stdin);
    WordLadder.word1="angel";
    WordLadder.word2="demon";
//    Stack<String> correctAnswer = new Stack<String>();
//    correctAnswer.push("angel");
//    correctAnswer.push("anger");
//    correctAnswer.push("auger");
//    correctAnswer.push("luger");
//    correctAnswer.push("leger");
//    correctAnswer.push("leges");
//    correctAnswer.push("lemes");
//    correctAnswer.push("demes");
//    correctAnswer.push("demos");
//    correctAnswer.push("demon");
    //
    Assert.assertTrue(WordLadder.wordLadder());
//    Assert.assertSame(WordLadder.answer,correctAnswer);
    }
} 
