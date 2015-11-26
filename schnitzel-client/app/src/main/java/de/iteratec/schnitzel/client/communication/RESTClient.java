package de.iteratec.schnitzel.client.communication;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.iteratec.schnitzel.common.model.Beacon;
import de.iteratec.schnitzel.common.model.Puzzle;
import de.iteratec.schnitzel.common.model.PuzzleStep;

/**
 * Created by Michael on 26.11.2015.
 */
public class RESTClient {
    private final static Gson gson = new Gson();
    private final static String TAG = "RESTClient";
    private final static String BASE_URL = "http://lt436:8080/schnitzel-server/schnitzel/";

    public static PuzzleStep getFirstPuzzleStep(long puzzleID) throws ExecutionException, InterruptedException {
        AsyncTask<Long, Long, PuzzleStep> task = new AsyncTask<Long, Long, PuzzleStep>() {

            @Override
            protected PuzzleStep doInBackground(Long... params) {

                if (params != null && params.length > 0) {
                    try {
                        URL url = new URL(BASE_URL + "puzzle/" + params[0] + "/firstStep");
                        return getRestResult(url, PuzzleStep.class);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
        return task.execute(puzzleID).get();
    }

    public static List<Puzzle> getPuzzles() throws ExecutionException, InterruptedException {
        AsyncTask<Void, Long, List<Puzzle>> task = new AsyncTask<Void, Long, List<Puzzle>>() {

            @Override
            protected List<Puzzle> doInBackground(Void... params) {
                try {
                    URL url = new URL(BASE_URL + "puzzles");
                    String json = getRestResultAsString(url);
                    Type listType = new TypeToken<List<Puzzle>>() {
                    }.getType();
                    return (List<Puzzle>) gson.fromJson(json, listType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        return task.execute().get();
    }

    public static Beacon getBeacon(long puzzleStepId) throws ExecutionException, InterruptedException {
        AsyncTask<Long, Long, Beacon> task = new AsyncTask<Long, Long, Beacon>() {

            @Override
            protected Beacon doInBackground(Long... params) {
                if (params != null && params.length > 0) {
                    try {
                        URL url = new URL(BASE_URL + "step/" + params[0] + "/beacon");
                        return getRestResult(url, Beacon.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
        return task.execute(puzzleStepId).get();
    }

    public static PuzzleStep getSuccessor(long puzzleStepId) throws ExecutionException, InterruptedException {
        AsyncTask<Long, Long, PuzzleStep> task = new AsyncTask<Long, Long, PuzzleStep>() {

            @Override
            protected PuzzleStep doInBackground(Long... params) {
                if (params != null && params.length > 0) {
                    try {
                        URL url = new URL(BASE_URL + "step/"+params[0]+"/successor");
                        return getRestResult(url, PuzzleStep.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
        return task.execute(puzzleStepId).get();
    }

    private static <T> T getRestResult(URL url, Class<T> clazz) {
        InputStream in;
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Log.e(TAG, "Error occured while trying to connect to the server." + e.getMessage());
            return null;
        }
        InputStreamReader reader = new InputStreamReader(in);
        return gson.fromJson(reader, clazz);
    }

    private static String getRestResultAsString(URL url) {
        InputStream in;
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Log.e(TAG, "Error occured while trying to connect to the server." + e.getMessage());
            return null;
        }
        return readStringFromInputStream(in);
    }

    private static String readStringFromInputStream(InputStream in) {
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder total = new StringBuilder();
        String line;
        try {
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total.toString();
    }

}
