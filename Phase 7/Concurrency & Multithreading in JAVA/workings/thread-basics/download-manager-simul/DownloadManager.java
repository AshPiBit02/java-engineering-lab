import java.util.Random;

class DownloadFile implements Runnable {
    private String fileName;
    private boolean downloaded;
    private Random rand = new Random();

    DownloadFile(String fileName) {
        this.fileName = fileName;
        downloaded = false;
    }

    @Override
    public void run() {
        try {
            System.out.println("Downloading: " + fileName);
            Thread.sleep(rand.nextInt(1501) + 500);
            downloaded = true;
        } catch (InterruptedException e) {
            System.out.println("Canceled: " + fileName);
        }

    }

    public void downloadStatus() {
        if (downloaded) {
            System.out.println(fileName + " downloaded successfully!");
        } else {
            System.out.println(fileName + " download failed!!");
        }
    }

}

public class DownloadManager {
    public static void main(String[] args) throws InterruptedException {
        DownloadFile file1 = new DownloadFile("VSCode");
        DownloadFile file2 = new DownloadFile("Claude");
        DownloadFile file3 = new DownloadFile("Git");

        Thread t1 = new Thread(file1);
        Thread t2 = new Thread(file2);
        Thread t3 = new Thread(file3);
        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(800);

        t1.interrupt();
        t2.interrupt();
        t3.interrupt();

        t1.join();
        t2.join();
        t3.join();

        file1.downloadStatus();
        file2.downloadStatus();
        file3.downloadStatus();

    }

}
