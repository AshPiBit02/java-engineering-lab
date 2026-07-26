public class ConcreteExporters {
    static class PDFExporter extends DocumentExporter {
        Document createDocument() {
            return new ConcreteDocument.PDFDocument();
        }
    }

    static class WordExporter extends DocumentExporter {
        Document createDocument() {
            return new ConcreteDocument.WordDocument();
        }
    }
}
