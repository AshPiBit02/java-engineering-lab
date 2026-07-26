abstract class DocumentExporter {
    abstract Document createDocument();

    void export() {
        prepare();
        Document doc = createDocument();
        doc.generate();
        log();
    }

    private void prepare() {
        System.out.println("Preparing export environment...");
    }

    private void log() {
        System.out.println("Export logged.\n");
    }
}
