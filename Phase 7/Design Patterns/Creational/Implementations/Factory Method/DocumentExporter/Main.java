public class Main {
    public static void main(String[] args){
        DocumentExporter exporter=new ConcreteExporters.PDFExporter();
        exporter.export();

        exporter = new ConcreteExporters.WordExporter();
        exporter.export();

    }    
        
}