package backend.client;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class ResponseLogic {
    private HashMap<String, ClientResponseHandler> handlers;
    private HashMap<String, HostResponseHandler> serverHandlers;
    public ResponseLogic(HashMap<String, ClientResponseHandler> handlers, HashMap<String, HostResponseHandler> serverHandlers) {

        this.handlers = handlers;
        this.serverHandlers = serverHandlers;
    }

    public void sendHandle(String message, String type, boolean isServer) {
            if(attemptSend(message,type, isServer)){
                return;
            }

            for(int i = 0; i<10; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Log.w("Warning", "Interrupted!", e);
                    // Restore interrupted state...
                    Thread.currentThread().interrupt();
                }
                if (attemptSend(message, type, isServer)) {
                    return;
                } else {
                    Log.i("ERROR", "INVALID UI TYPE " + type);
                }
            }
    }

    private boolean attemptSend(String message, String type, boolean isServer){
        synchronized (handlers) {
            if (handlers.containsKey(type) || serverHandlers.containsKey(type)) {
                send(message, type, isServer);
                return true;
            }
        }
        return false;
    }


    private void send(String message, String type, boolean isServer) {

        Message handleMessage = new Message();
        Bundle b = new Bundle();
        b.putString("payload", message);
        handleMessage.setData(b);
        if(isServer){
            serverHandlers.get(type).sendMessage(handleMessage);
        }else {
            handlers.get(type).sendMessage(handleMessage);
        }
    }

    public HashMap<String, ClientResponseHandler> getHandler() {
        return handlers;
    }

    public void registerActivity(String type, AppCompatActivity activity) {
        synchronized (handlers) {
            if (handlers.containsKey(type)) {
                Log.i("LOGICINFO", "ALREADY CONTAINS " + type + " with signature " + handlers.get(type).getUiActivity());
                handlers.get(type).replaceActivity(activity);
            } else {
                handlers.put(type, new ClientResponseHandler(activity));
            }
        }
    }

    public void registerServerResponse(String type, AppCompatActivity activity) {
        synchronized (serverHandlers) {
            if (serverHandlers.containsKey(type)) {
                Log.i("LOGICINFO", "serverHandlers ALREADY CONTAINS " + type + " with signature " + serverHandlers.get(type).getUiActivity());
                serverHandlers.get(type).replaceActivity(activity);
            } else {
                serverHandlers.put(type, new HostResponseHandler(activity));
            }
        }
    }

    public void removeType(String type){
        synchronized (handlers){
            if(handlers.containsKey(type)){
                handlers.remove(type);
            }
        }
    }
}
