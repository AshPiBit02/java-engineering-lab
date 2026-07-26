public class ConcreteDocument {
    static class PDFDocument implements Document {
        @Override
        public void generate() {
            System.out.println("Generating PDF document...");
        }
    }

    static class WordDocument implements Document {
        @Override
        public void generate() {
            System.out.println("Generating word document...");
        }
    }
}
