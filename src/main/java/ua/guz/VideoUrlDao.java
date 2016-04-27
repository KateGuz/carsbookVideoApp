package ua.guz;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideoUrlDao {

    private static final String GET_VIDEO_URLS_SQL = "SELECT videoUrl FROM video LIMIT 0, 1000;";
    private static final String COMMA_SEPARATOR = ",";
    private static final int ID_LENGTH = 11;

    private Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/carsbook", "root", "root");
    }

    public List<String> getVideoUrls() throws SQLException, ClassNotFoundException {
        List<String> urls = new ArrayList<String>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connect().createStatement();
            resultSet = statement.executeQuery(GET_VIDEO_URLS_SQL);
            while (resultSet.next()) {
                urls.add(resultSet.getString("videoUrl"));
            }
        } finally {
            resultSet.close();
            statement.close();
        }

        return urls;
    }

    public String videoIds() throws SQLException, ClassNotFoundException, FileNotFoundException, UnsupportedEncodingException {
        List<String> urls = getVideoUrls();
        StringBuilder ids = new StringBuilder();

        PrintWriter writer = new PrintWriter("not-youtube-links.txt", "UTF-8");
        for (String videoUrl : urls) {
            int startIndex = 0;
            if (videoUrl.contains("youtube")) {
                startIndex = videoUrl.indexOf("v=") + 2;
            } else if (videoUrl.contains("youtu.be")) {
                startIndex = 17;
            } else {
                writer.println(videoUrl);
            }
            ids.append(videoUrl.substring(startIndex, startIndex + ID_LENGTH));
            ids.append(COMMA_SEPARATOR);
        }
        writer.close();
        return ids.toString();
    }

}
