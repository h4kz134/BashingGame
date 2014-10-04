package bashinggame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class HighScore {

    Properties prop;
    private int Easy;
    private int Medium;
    private int Hard;

    //Read highsores from file
    private void readHighScores() {
        Easy = Integer.parseInt(prop.getProperty("Easy"));
        Medium = Integer.parseInt(prop.getProperty("Medium"));
        Hard = Integer.parseInt(prop.getProperty("Hard"));
    }

    public int getHighscore(int level) {
        switch (level) {
            case 0:
                return Easy;
            case 1:
                return Medium;
            case 2:
                return Hard;

        }
        return 0;
    }

    public void setHighScore(int score, int level) {
        String s = "";
        OutputStream output = null;
        switch (level) {
            case 0:
                s = "Easy";
                Easy = score;
                break;
            case 1:
                s = "Medium";
                Medium = score;
                break;
            case 2:
                s = "Hard";
                Hard = score;
                break;
        }
        try {
            output = new FileOutputStream("highscores.properties");
            // set the properties value
            prop.setProperty(s, score + "");
            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void initializeHighScores() {
        prop = new Properties();
        InputStream in;
        try {
            in = new FileInputStream("highscores.properties");

            try {
                prop.load(in);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (FileNotFoundException ex) {
            OutputStream output = null;
            try {
                output = new FileOutputStream("highscores.properties");
                // set the properties value
                prop.setProperty("Easy", "0");
                prop.setProperty("Medium", "0");
                prop.setProperty("Hard", "0");

                // save properties to project root folder
                prop.store(output, null);

            } catch (IOException io) {
                io.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        readHighScores();
    }
}
