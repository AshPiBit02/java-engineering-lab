           System.out.println("Client closed connection.");
            try {
                Thread.sleep(500);
                System.out.println("Server Status: Closed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
