import java.util.*;
import java.io.*;

public class WordLadder {
    /**
     * wordladder
     * get the ladder("answer") back from "word2" to "word1" through the dictionary which is stored in the arraylist "words"
     */
    static final ArrayList<String> words = new ArrayList<String>();
    static Stack<String> answer = new Stack<String>();
    static final String dicPath = System.getProperty("user.dir") + "\\src\\main\\resources\\";
    static final String defaultDicName = "dictionary.txt";
    static  String word1;
    static  String word2;
    enum KEY{QUIT,ERR,OK}

    public static void start(){
        if(!getDic()){
            System.out.println("Have a nice day!");
            System.exit(0);
        }
        while(true){
            KEY key = getWords();
            if(key==KEY.QUIT)
                break;
            else if(key==KEY.ERR)
                continue;
            if(wordLadder())
                printout();
        }
    }
    static boolean getDic(){
        while(true){
            try {
                System.out.println("Dictionary file name?(input 'q' to quit and the default dictionary name is " +
                                   "dictionary.txt To use the default name, just press the Enter key) ");
                Scanner scanner = new Scanner(System.in);
                String dicName = scanner.nextLine();
                if("".equals(dicName))
                    dicName = defaultDicName;
                else if("q".equals(dicName)){
                    scanner.close();
                    return false;
                }
                File file = new File(dicPath + dicName);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String tmpWord;
                tmpWord = reader.readLine();
                while (tmpWord != null) {
                    words.add(tmpWord);
                    tmpWord = reader.readLine();
                }
                reader.close();
                return true;
            }
            catch(Exception e){
                System.err.println("No such file!");
            }
        }
    }
    private static KEY getWords(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Word #1 (or Enter to quit): ");
        word1 = scanner.nextLine();
        try {
            if (getAWord(word1)) {
                scanner.close();
                return KEY.QUIT;
            }
            System.out.println("Word #2 (or Enter to quit): ");
            word2 = scanner.nextLine();
            if (getAWord(word2)) {
                scanner.close();
                return KEY.QUIT;
            }
            checkTwoWords();
            return KEY.OK;
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return KEY.ERR;
        }
    }
    static boolean getAWord(String word)throws Exception{
        if("".equals(word)){
            System.out.println("Have a nice day!");
            return true;
        }
        checkWord(word);
        return false;
    }

    static void checkWord(String word)throws Exception{
        char [] cWord = word.toCharArray();
        for(char ch : cWord){
            if( !((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')))
                throw new Exception("Not a word!");
        }
        word = word.toLowerCase();
        if(!checkHaveWord(word))
            throw new Exception("The two words must be found in the dictionary.");
    }
    private static boolean checkHaveWord(String word){
        int low = 0;
        int high = words.size() - 1;
        while (low <= high){
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
    private static void checkTwoWords()throws Exception{
    lengthCheck();
    sameCheck();
}
    static void lengthCheck()throws Exception{
        if(word1.length() != word2.length())
            throw new Exception("The two words must be the same length.");
    }
    static void sameCheck()throws Exception{
        if(word1.equals(word2))
            throw new Exception("The two words must be different.");
    }

    static boolean wordLadder(){
        Stack<String> s = new Stack<String>();
        s.push(word1);

        Queue<Stack<String>> line = new LinkedList<Stack<String>>();
        line.offer(s);

        Set<String> wordsBeenUsed = new HashSet<String>();
        wordsBeenUsed.add(word1);
        try {
            while (!line.isEmpty()) {
                String upWord = line.peek().peek();
                for (int i = 0; i < upWord.length(); i++) {
                    for (char j = 'a'; j <= 'z'; j++) {
                        String tmpWord = upWord.substring(0, i) + j + upWord.substring(i + 1);
                        if (checkHaveWord(tmpWord)) {
                            if (!wordsBeenUsed.contains(tmpWord)) {
                                if (tmpWord.equals(word2)) { //get the ladder
                                    line.peek().push(tmpWord);
                                    answer = line.peek();
                                    return true;
                                }
                                wordsBeenUsed.add(tmpWord);
                                //copy the stack
                                Stack<String> tmp = (Stack<String>) line.peek().clone();
                                tmp.push(tmpWord);
                                line.add(tmp);
                            }
                        }
                    }
                }
                line.poll();
            }
            throw new Exception("No word ladder found from " + word1 + " back to " + word2 + ".");
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return false;
        }
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

