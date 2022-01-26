// solution to problem UVa ID: 11860 on Online Judge: Document Analyser || by Kemal Kilic

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Main {
    private static final int MAX_WORDS = 100000;
    private static final String REG = "END|[a-z]+";
    private static final Pattern REG_PATTERN = Pattern.compile(REG);


    private HashMap<String, Boolean> map;

    private Main() {
        
    }

    static String ReadLn (int maxLg)  // utility function to read from stdin
    {
        byte lin[] = new byte [maxLg];
        int lg = 0, car = -1;
        // String line = "";

        try
        {
            while (lg < maxLg)
            {
                car = System.in.read();
                if ((car < 0) || (car == '\n')) break;
                lin [lg++] += car;
            }
        }
        catch (IOException e)
        {
            return (null);
        }

        if ((car < 0) && (lg == 0)) return (null);  // eof
        return (new String (lin, 0, lg));
    }

    private void analyze() {

        int initCapacity = (int) ((MAX_WORDS / 0.75) + 1);
        map = new HashMap<>(initCapacity);
        ArrayList<String> wordList = new ArrayList<>(MAX_WORDS); 
        wordList.add(0, "");

        
        // no need to close the buffer because the try statement will close it
        try {
            
            int wordIndex = 0;
            int docIndex = 0;
            
            while (true) {
                
                String line = ReadLn(255);
                if (line == null) {
                    break;
                }
                

                ArrayList<String> words = new ArrayList<>();
                Matcher m = REG_PATTERN.matcher(line);
                while(m.find()) {
                    words.add(m.group());
                }

                // for (int k = 0; k < words.size(); k++) {
                //     System.out.println(words.get(k));
                // }
                
                for (int i = 0; i < words.size(); i++) {
                    String word = words.get(i);
                    if (word.equals("END")) {
                        docIndex++;
                        generateOutput(map, wordList, docIndex);
                        // System.out.println("HAHA\n\n");

                        map.clear();
                        wordList.clear();
                        wordList.add(0, "");
                        wordIndex = 0;
                        
                    } else {
                        wordIndex++;
                        map.put(word, true);
                        wordList.add(wordIndex, word);
                    }
                }
            }

            // System.out.println("Size of hashmap = "+ map.size());
            // System.out.println(map.toString());
            // System.out.println("Size of words = "+ wordIndex);

            

        } catch(Exception e) {

        }
                
    }

    private void generateOutput(HashMap<String, Boolean> hMap, ArrayList<String> wList, int docIndex) {
        
        // System.out.println("map size: " + hMap.size());
        // System.out.println(hMap.toString());

        
        // for (int i = 1; i < wList.size(); i++) {
        //     System.out.println("word "+ i + ": " + wList.get(i));
        // }

        int wlistSize = wList.size();
        int hMapSize = hMap.size();
        HashMap<String, Integer> nMap = new HashMap<>();
        int i = 1;
        int max =0, min =0, maxMinDif = wlistSize + 1, p = 1, q = wlistSize, leastDif = wlistSize+1;
        boolean containsAllCheck = false;

        
        while(i < wlistSize) {

            nMap.put(wList.get(i), i);
            // System.out.println(nMap.toString());

            if ((!containsAllCheck) && (nMap.size() == hMapSize)) {
                containsAllCheck = true; // won't check again once it's true
            }

            if (containsAllCheck) {
                max = -1;
                min = wlistSize;
                for(Integer j : nMap.values()) {
                    if (j < min) 
                        min = j;
                    if (j > max)
                        max = j; 
                }
                maxMinDif = max - min;
            }

            if (maxMinDif < leastDif) {
                p = min;
                q = max;
                leastDif = maxMinDif;
            }

            // System.out.println("max = " + max
            //                     + " min = " + min
            //                     + " maxmindif = " + maxMinDif
            //                     + " p = " + p
            //                     + " q = " + q
            //                     + " leastdif = " + leastDif);

            i++;
        }

        //System.out.println("Document " + docIndex + ": " + pFinal + " " + qFinal);
        // System.out.println("Document " + docIndex + ": " + p + " " + q);

        int bufferSize = 4 * 1024;
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(System.out, bufferSize);
            String outStr = "Document " + docIndex + ": " + p + " " + q + "\n";
            byte[] output = outStr.getBytes();
            outputStream.write(output);
            outputStream.flush();
        } catch (Exception e) {
            System.out.println("Exception in generateOutout");
        }
        


    }


    public static void main(String args[]) {
        // long begin = System.nanoTime();
        Main work = new Main();
        work.analyze();
        // long end = System.nanoTime();

        // System.out.println("Runtime: " + (double)(end - begin) / 1_000_000_000 + "s" );

    }

    
    
}
