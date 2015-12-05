/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis.service.tasks;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Tim on 12/5/15.
 */
public class ChatTask extends AsyncTask<String, Void, String> {

    private final ChatCallback callback;

    public ChatTask(ChatCallback callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder result = new StringBuilder();

        try {
            InetAddress server = InetAddress.getByName("www.regisscis.net");
            Socket socket = new Socket(server, 8080);

            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(params[0]);

            String response = in.readLine();
            result.append(response);

            in.close();
            out.close();
            socket.close();

        } catch (Exception e) {
            result.append("EXCEPTION: " + e.getMessage());
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        callback.execute(result);
    }

    public interface ChatCallback {
        void execute(String message);
    }
}
