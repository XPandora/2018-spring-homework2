package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void testReplace()
	{
		Controller wordLadder = new Controller();
		Assert.assertEquals(wordLadder.replaceChar("cat",0,'a'),"aat");
		Assert.assertEquals(wordLadder.replaceChar("hash",0,'c'),"cash");
	}
	@Test
	public void testInsert()
	{
		Controller wordLadder = new Controller();
		Assert.assertEquals(wordLadder.insertChar("car",0,'a'),"acar");
		Assert.assertEquals(wordLadder.insertChar("car",1,'a'),"caar");
		Assert.assertEquals(wordLadder.insertChar("car",2,'c'),"cacr");
		Assert.assertEquals(wordLadder.insertChar("car",3,'a'),"cara");
	}
	@Test
	public void testRemove()
	{
		Controller wordLadder = new Controller();
		Assert.assertEquals(wordLadder.removeChar("car",0),"ar");
		Assert.assertEquals(wordLadder.removeChar("car",1),"cr");
		Assert.assertEquals(wordLadder.removeChar("car",2),"ca");
	}
	@Test
	public void testWordCheck()
	{
		Controller wordLadder = new Controller();
		wordLadder.setWord("a","abc");
		Assert.assertTrue(wordLadder.wordCheck());
		wordLadder.setWord("!","a");
		Assert.assertFalse(wordLadder.wordCheck());
	}
	@Test
	public void testSameLength()
	{
		Controller wordLadder = new Controller();
		wordLadder.setWord("a","abc");
		Assert.assertFalse(wordLadder.isSameLength());
		wordLadder.setWord("cca","eeq");
		Assert.assertTrue(wordLadder.isSameLength());
	}

}
