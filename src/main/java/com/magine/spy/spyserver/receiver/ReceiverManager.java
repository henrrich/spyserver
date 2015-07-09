package com.magine.spy.spyserver.receiver;

import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Class that controls the receiver thread.
 */
public class ReceiverManager {
	
	private static ReceiverManager instance;
	
	private Executor receiverExecutor; 
	
    private ReceiverManager() {
    	receiverExecutor = Executors.newCachedThreadPool();
    }

    /**
	 * Get the singleton instance of the ReceiverManager class
	 * 
	 * @return ReceiverManager global instance of ReceiverManager class
	 */
    public static ReceiverManager getInstance() {
        if (instance == null ) {
            synchronized (ReceiverManager.class) {
                if (instance == null) {
                    instance = new ReceiverManager();
                }
            }
        }

        return instance;
    }
    
    /**
	 * Start the receiver thread to receive collected log achieve from client via TCP
	 * socket
	 */
    public void startReceiver(Socket connection) {
    	Receiver receiver = new Receiver(connection);
    	receiverExecutor.execute(receiver);
    }

}
