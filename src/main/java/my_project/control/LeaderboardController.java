package my_project.control;

import java.io.*;
import java.util.Arrays;

public class LeaderboardController {

    private String[] names;
    private int[] scores;
    private String[] difficults;

    public LeaderboardController(){
        updateArrays();
    }

    /**
     * updatet die Arrays mit den Informationen aus LeaderBoard.txt
     */
    private void updateArrays(){
        String fileString = getFileContent();
        if(!fileString.equals("")) {
            String[] fileStringArray = fileString.split("\n");
            String[] stringArray=new String[fileStringArray.length];
            Arrays.sort(fileStringArray);
            for(int i=0;i<fileStringArray.length;i++){
                stringArray[stringArray.length-i-1]=fileStringArray[i];
            }
            fileStringArray=stringArray;
            names = new String[fileStringArray.length];
            scores = new int[fileStringArray.length];
            difficults = new String[fileStringArray.length];
            for (int i = 0; i < fileStringArray.length; i++) {
                String[] strings = fileStringArray[i].split(":");
                names[i] = strings[1];
                scores[i] = Integer.parseInt(strings[0]);
                difficults[i] = strings[2];
            }
        }
    }

    /**
     * fügt einen score hinzu
     */
    public void addToLeaderBoard(String name,int score,String difficult){
        String[] oldNames=new String[names.length];
        System.arraycopy(names,0,oldNames,0,names.length);
        String[] oldDifficults=new String[names.length];
        System.arraycopy(difficults,0,oldDifficults,0,names.length);
        int[] oldScores=new int[names.length];
        System.arraycopy(scores,0,oldScores,0,names.length);
        names=new String[oldNames.length+1];
        difficults=new String[oldNames.length+1];
        scores=new int[oldNames.length+1];
        System.arraycopy(oldNames,0,names,0,oldNames.length);
        System.arraycopy(oldDifficults,0,difficults,0,oldNames.length);
        System.arraycopy(oldScores,0,scores,0,oldNames.length);
        names[names.length-1]=name;
        difficults[names.length-1]=difficult;
        scores[names.length-1]=score;
        writeLeaderBoard();
        updateArrays();
    }

    /**
     * @return den Inhalt aus LeaderBoard.txt
     */
    private String getFileContent() {
        FileInputStream inputStream = null;

        try {
            inputStream = new FileInputStream("src/main/java/my_project/control/LeaderBoard.txt");
        } catch (
                FileNotFoundException ignore) {
        }

        StringBuilder sb = new StringBuilder();

        try {
            assert inputStream != null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * schreibt die Informationen über sie scores in LeaderBoard.txt
     */
    public void writeLeaderBoard() {
        StringBuilder newFileDataString = new StringBuilder();

        for(int i=0;i< names.length;i++) newFileDataString.append(String.format("%s:%s:%s%n", scores[i],names[i], difficults[i]));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/my_project/control/LeaderBoard.txt"))) {
            writer.write(newFileDataString.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public String[] getNames(){ return names; }
    public String[] getDifficults(){ return difficults; }
    public int[] getScores(){ return scores; }
}
