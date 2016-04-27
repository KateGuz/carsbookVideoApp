package ua.guz;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class VideoTitle {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        VideoUrlDao videoUrlDao = new VideoUrlDao();

        YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                    }
                }).setApplicationName("video-title").build();

        String videoId = videoUrlDao.videoIds();
        YouTube.Videos.List videoRequest = youtube.videos().list("snippet");
        videoRequest.setId(videoId);
        videoRequest.setKey("AIzaSyCydUJQ3LZ_t3-aPyAICl9vY2itBNFhcj8");
        VideoListResponse listResponse = videoRequest.execute();
        List<Video> videoList = listResponse.getItems();
        for (Video video : videoList) {
            System.out.println(video.getSnippet().getTitle());
        }
    }
}
