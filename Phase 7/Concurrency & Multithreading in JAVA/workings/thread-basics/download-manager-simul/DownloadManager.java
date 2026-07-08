import java.util.Random;

class DownloadFile implements Runnable {
    private String fileName;
    private boolean downloaded;
    private Random rand = new Random();
    private int progress;

    DownloadFile(String fileName) {
        this.fileName = fileName;
        downloaded = false;
        progress = 0;
    }

    @Override
    public void run() {
        while (progress < 100) {
            int percentInc = rand.nextInt(10);
            try {
                progress += percentInc;
                if (progress >= 100) {
                    System.out.println();
                    System.out.println("Downloading: " + fileName + " [ 100% ]");
                    downloaded = true;
                    System.out.println(fileName + " downloaded successfully!");
                    System.out.println();
                } else {
                    System.out.println("Downloading: " + fileName + " [ " + progress + "% ]");
                }
                Thread.sleep(rand.nextInt(205));
            } catch (InterruptedException e) {
                System.out.println();
                System.out.println("Canceled: " + fileName + "[ " + progress + "% ]");
                break;
            }
        }

    }

    public void fileStatus() {
        if (downloaded) {
            System.out.println(fileName + ": [Downloaded]");
        } else {
            System.out.println(fileName + ": [Timeout]");
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

        Thread.sleep(2000);

        t1.interrupt();
        t2.interrupt();
        t3.interrupt();

        t1.join();
        t2.join();
        t3.join();

        showFileStatus(file1, file2, file3);

    }

    private static void showFileStatus(DownloadFile f1, DownloadFile f2, DownloadFile f3) {
        System.out.println();
        System.out.println("Download Manager");
        System.out.println("-".repeat(30));
        f1.fileStatus();
        f2.fileStatus();
        f3.fileStatus();
    }

}
