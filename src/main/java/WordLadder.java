import java.util.*;
import java.io.*;

public class WordLadder {
    /**
     * wordladder
     * get the ladder(answer) from word1 to word2 through the dictionary which is stored in the arraylist words
     */
    private static final ArrayList<String> words = new ArrayList<String>();
    private static Stack<String> answer = new Stack<String>();
    private static final String dicPath = System.getProperty("user.dir") + "\\src\\main\\resources\\";
    private static  String word1;
    private static  String word2;

    public static void main(final String args []) {
        while(true) {
            try {
                getDic();
                break;
            } catch (Exception e) {
                System.out.println("No such file!");
            }
        }

        while(true){
                if(!getWords())
                    break;
                if(!wordLadder())
                    continue;
                printout();
            }
        }

    private static void getDic() throws Exception{
        System.out.println("Dictionary file name? ");
        Scanner scanner = new Scanner(System.in);
        String dicName = scanner.nextLine();
        scanner.close();
        File file = new File(dicPath +dicName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String tmpWord;
        tmpWord = reader.readLine();
        while(tmpWord !=null){
            words.add(tmpWord);
            tmpWord = reader.readLine();
        }
        reader.close();
    }
    private static boolean getWords(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Word #1 (or Enter to quit): ");
        word1 = scanner.nextLine();
        try {
            if (getAWord(word1)) {
                scanner.close();
                return false;
            }
            System.out.println("Word #2 (or Enter to quit): ");
            word2 = scanner.nextLine();
            if (getAWord(word2)) {
                scanner.close();
                return false;
            }
            scanner.close();
            checkTwoWords();
            return true;
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return false;
        }
    }
    private static boolean getAWord(String word)throws Exception{
        if(word == null){
            System.out.println("Have a nice day!");
            return true;
        }
        checkWord(word1);
        return false;
    }

    private static void checkWord(String word)throws Exception{
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
    private static void checkTwoWords()throws Exception{
    lengthCheck();
    sameCheck();
}
    private static void lengthCheck()throws Exception{
        if(word1.length() != word2.length())
            throw new Exception("The two words must be the same length.");
    }
    private static void sameCheck()throws Exception{
        if(word1.equals(word2))
            throw new Exception("The two words must be different.");
    }

    private static boolean wordLadder(){
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
                                if (tmpWord.equals(word2)) {
                                    line.peek().push(tmpWord);
                                    answer = line.peek();
                                    return true;
                                }
                                wordsBeenUsed.add(tmpWord);
                                Stack<String> tmp = line.peek();
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

