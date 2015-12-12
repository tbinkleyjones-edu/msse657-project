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
 * An async task used to send feedback to the Regis feedback service.
 * <p>
 * The task opens a socket to the server, posts a feedback message, retrieves a response message,
 * and finally closes the socket. The response message is returned to the caller via
 * a callback interface. The task's execute method accepts a single String parameter, which is
 * used as the feedback message.
 */
public class FeedbackTask extends AsyncTask<String, Void, String> {

    private final FeedbackCallback callback;

    /**
     * Constructs a new FeedbackTask.
     *
     * @param callback the callback used to return the service's response.
     */
    public FeedbackTask(FeedbackCallback callback) {
        this.callback = callback;
    }

    /**
     * @param params expected to contain a single String value, which is used as the feedback message.
     * @return
     */
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

    /**
     * The callback interface used to return the service's response to the caller.
     */
    public interface FeedbackCallback {

        /**
         * Called by the AsyncTask's onPostExecute withe the service's response message.
         *
         * @param message
         */
        void execute(String message);
    }
}
