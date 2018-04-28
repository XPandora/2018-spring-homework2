package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.*;
import java.util.*;

@RestController
@RequestMapping(value = "/user/wordLadder")


public class Controller {
    protected static Logger logger=LoggerFactory.getLogger(Controller.class);
    String word1,word2;
    String transpath="";
    Set<String> dictionary;

    public Controller(){}

    public Controller(String s1,String s2,Set<String> set){
        word1 = s1;
        word2 = s2;
        dictionary = set;
    }

    public void setDict(Set<String> set){
        dictionary = set;
    }

    public void setWord(String s1, String s2){
        word1 = s1;
        word2 = s2;
    }

    public boolean wordCheck(){
        for(char letter:word1.toCharArray()){
            if('a'<=letter && letter<='z') continue;
            else return false;
        }

        for(char letter:word2.toCharArray()){
            if('a'<=letter && letter<='z') continue;
            else return false;
        }

        return true;
    }

    public boolean isSameLength(){
        return word1.length()==word2.length();
    }

    public String replaceChar(String str, int index, char letter){
        String newStr = str.substring(0,index)+ letter + str.substring(index + 1,str.length());
        return newStr;
    }

    public String insertChar(String str, int index, char letter){
        String newStr = str.substring(0,index)+ letter + str.substring(index,str.length());
        return newStr;
    }

    public String removeChar(String str, int index){
        String newStr = str.substring(0,index) + str.substring(index + 1,str.length());
        return newStr;
    }

    public String transfer(){
        Queue<Stack<String>> pathQueue = new LinkedList<Stack<String>>();
        Stack<String> initialPath = new Stack<String>();
        Set<String> usedWord = new HashSet<String>();

        initialPath.push(word1);
        pathQueue.offer(initialPath);
        usedWord.add(word1);

        while(!pathQueue.isEmpty()){
            Stack<String> tempPath = pathQueue.poll();
            String tempWord = tempPath.peek();
            int tempSize;

            if(isSameLength()){
                tempSize = word1.length();
            }
            else{
                tempSize = 2 * tempWord.length() + 1;
            }

            for(int i = 0;i<tempSize;i++){
                for(char letter = 'a';letter<='z';letter++){
                    tempWord = tempPath.peek();

                    if(isSameLength()){
                        tempWord = replaceChar(tempWord,i,letter);
                    }
                    else{
                        if(i % 2 == 1){
                            tempWord = replaceChar(tempWord,(i-1)/2,letter);
                        }
                        else{
                            if(tempWord.length() < word2.length()){
                                tempWord = insertChar(tempWord,i/2,letter);
                            }
                            else{
                                if (i / 2 <tempWord.length()){
                                    tempWord = removeChar(tempWord,i/2);
                                }
                            }
                        }
                    }

                    if(usedWord.contains(tempWord)) continue;
                    if(!tempWord.equals(word2) && !dictionary.contains(word2)) continue;

                    Stack<String> newPath = (Stack<String>) tempPath.clone();
                    newPath.push(tempWord);
                    usedWord.add(tempWord);

                    if(tempWord.equals(word2)){
                        while(!newPath.isEmpty()){
                            transpath+=(newPath.pop() + " ");
                        }
                        logger.debug("访问Worderladder,Path={}",transpath);
                        return transpath;
                    }
                    pathQueue.offer(newPath);

                }
            }
        }
        logger.debug("访问WordLadder,转换失败");
        return "failed";

    }
    @RequestMapping(value = "/start={start}&end={end}",method = RequestMethod.GET)

    public static String wordLadder(@PathVariable String start,@PathVariable String end) throws IOException
    {
        String filepath = "src\\main\\java\\dictionary.txt";
        String line;
        Set<String> dictionary = new HashSet<String>();
        Controller wordLadder = new Controller();

        File fileReader = new File(filepath);
        InputStreamReader reader = new InputStreamReader(new FileInputStream(fileReader));
        BufferedReader wordBuffer = new BufferedReader(reader);
        line = wordBuffer.readLine();
        while(line != null){
            dictionary.add(line);
            line = wordBuffer.readLine();
        }
        wordLadder.setDict(dictionary);




        if (start.equals(end)){
            return "The two words must be different";
        }

        wordLadder.setWord(start,end);
        if(!wordLadder.wordCheck()){
            return "error!";
        }

        return wordLadder.transfer();
    }
}



