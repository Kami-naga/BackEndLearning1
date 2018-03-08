import java.util.*;
import java.io.*;

public class WordLadder {

    static final ArrayList<String> words = new ArrayList<String>();
    static Stack<String> answer = new Stack<String>();
    static final String path = System.getProperty("user.dir") + "\\src\\main\\resources\\";
    static  String word1;
    static  String word2;

    private void error(String s){
        try{
            throw new Exception(s);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(final String args []) {
        while(true){
//            try{
            getDic();
            break;
//            }
//            catch(Exception e){
//                System.out.println(e.what());
//            }
        }
        while(true){
//            try{
                if(!getWords())
                    break;
                wordLadder();
                printout();
//            }
//            catch(Exception e){
//                System.out.println(e.what())
            }
        }

    private static void getDic(){
        System.out.println("Dictionary file name? ");
        Scanner scanner = new Scanner(System.in);
        String dicName = scanner.nextLine();
        scanner.close();
        File file = new File(path+dicName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        if (reader ==null)
                error("No such file!");
        String tmpWord;
        while(tmpWord = reader.readLine()!=null){
            words.add(tmpWord);
        }
        reader.close();
    }
    private static boolean getWords(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Word #1 (or Enter to quit): ");
        word1 = scanner.nextLine();
        if(!getAWord(word1)){
            scanner.close();
            return false;
        }
        System.out.println("Word #2 (or Enter to quit): ");
        word2 = scanner.nextLine();
        if(!getAWord(word2)){
            scanner.close();
            return false;
        }
        scanner.close();
        checkTwoWords();
        return true;
    }
    private static boolean getAWord(String word){
        if(word == null){
            System.out.println("Have a nice day!");
            return false;
        }
        checkWord(word1);
        return true;
    }

    private static void checkWord(String word){
        char [] cWord = word.toCharArray();
        for(char ch : cWord){
            if( !((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')))
                error("Not a word!");
        }
        word.toLowerCase();
        if(!checkHaveWord(word))
            error("The two words must be found in the dictionary.");
    }
    private static boolean checkHaveWord(String word){
        int low = 0;
        int high = words.size() - 1;
        while (low<high){
            int mid = (low + high) / 2;
            String w = words.get(mid);
            if(w.equals(word))
                return true;
            else if(word.compareTo(w) < 0)
                high = mid -1;
            else
                low = mid + 1;
        }
        return false;
    }
    private static void checkTwoWords(){
    lengthCheck();
    sameCheck();
}
    private static void lengthCheck(){
        if(word1.length() != word2.length())
            error("The two words must be the same length.");
    }
    private static void sameCheck(){
        if(word1.equals(word2))
            error("The two words must be different.");
    }

    private static void wordLadder(){
        Stack<String> s = new Stack<String>();
        s.push(word1);

        Queue<Stack<String>> line = new LinkedList<Stack<String>>();
        line.offer(s);

        Set<String> wordsBeenUsed = new HashSet<String>();
        wordsBeenUsed.add(word1);

        while(!line.isEmpty()){
            String upWord = line.peek().peek();
            for(int i = 0;i < upWord.length(); i++){
                for(char j ='a';j <= 'z';j++){
                    String tmpWord = upWord.substring(0,i) + j + upWord.substring(i + 1);
                    if(checkHaveWord(tmpWord)){
                        if(!wordsBeenUsed.contains(tmpWord)){
                            if(tmpWord.equals(word2)){
                                line.peek().push(tmpWord);
                                answer = line.peek();
                            }
                            wordsBeenUsed.add(tmpWord);
                            Stack<String> tmp = line.peek();
                            tmp.push(tmpWord);
                            line.add(tmp);
                        }
                        else
                            continue;
                    }
                    else
                        continue;
                }
            }
            line.poll();
        }
        error("No word ladder found from "+word1+" back to "+word2+".");
    }

    private static void printout(){
        System.out.println("A ladder from "+word1+" back to " +word2+":");
        while(!answer.empty()){
            System.out.print(answer.pop()+"\t");
        }
        System.out.println();
        System.out.println();
    }
}

