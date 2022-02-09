package my_project.control;

import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.model.LeaderboardEntry;

import java.io.*;
import java.util.Arrays;

public class LeaderboardController {

    private final static String filePath = "src/main/resources/LeaderBoard.txt";
    private List<LeaderboardEntry> entries;

    public LeaderboardController(){
        entries = getFileContent();
    }


    /**
     * Adds the entry to the leaderboard
     * @param name the name of the player
     * @param score the reached score
     * @param difficulty the difficulty
     */
    public void addToLeaderBoard(String name, int score, String difficulty){
        insert(entries, new LeaderboardEntry(name, score, difficulty));
        writeLeaderBoard();
    }

    /**
     * Parses the content from the file
     * @return the list of all entries read from the file
     */
    private List<LeaderboardEntry> getFileContent() {
        List<LeaderboardEntry> lb = new List<>();
        FileInputStream inputStream;

        try {
            inputStream = new FileInputStream(filePath);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(":");
                    insert(lb, new LeaderboardEntry(values[0], Integer.parseInt(values[1]), values[2]));
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return lb;
    }

    /**
     * Inserts a new entry
     * @param lb the leaderboard
     * @param entry the new entry
     */
    private void insert (List<LeaderboardEntry> lb, LeaderboardEntry entry) {
        lb.toFirst();
        while (lb.hasAccess() && lb.getContent().score() > entry.score()) {
            lb.next();
        }

        if (!lb.hasAccess()) {
            lb.append(entry);
        } else {
            lb.insert(entry);
        }

        System.out.println("inserted" + entry);
    }

    /**
     * Writes the data to the leaderboard
     */
    private void writeLeaderBoard() {
        StringBuilder newFileDataString = new StringBuilder();

        entries.toFirst();
        while (entries.hasAccess()) {
            var entry = entries.getContent();
            newFileDataString.append(String.format("%s:%d:%s%n", entry.name(), entry.score(), entry.difficulty()));
            entries.next();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(newFileDataString.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public List<LeaderboardEntry> getEntries (){
        return entries;
    }

}
