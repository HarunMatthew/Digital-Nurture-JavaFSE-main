//You are developing a document management system that needs to create different types of documents 
// (e.g., Word, PDF, Excel). Use the Factory Method Pattern to achieve this.

interface Document{
    void open();
}

// concrete product - word document
class WordDocument implements Document{
    @Override
    public void open() {
        System.out.println("Opening Word Document......");
    }
}

// concrete product - pdf document
class PDFDocument implements Document{
    @Override
    public void open() {
        System.out.println("Opening PDF Document......");
    }
}

// concrete product - excel document
class ExcelDocument implements Document{
    @Override
    public void open() {
        System.out.println("Opening Excel Document......");
    }
}

abstract class DocumentFactory{
    public abstract Document createDocument();
}

// concrete factory - word document factory
class WordDocumentFactory extends DocumentFactory{
    @Override
    public Document createDocument() {
        return new WordDocument();
    }
}

// concrete factory - pdf document factory
class PDFDocumentFactory extends DocumentFactory{
    @Override
    public Document createDocument() {
        return new PDFDocument();
    }
}


// concrete factory - excel document factory
class ExcelDocumentFactory extends DocumentFactory{
    @Override
    public Document createDocument() {
            return new ExcelDocument();
    }
}

// client class - uses the factory instead of creating the object directly
public class FactoryMethodPatternExample {

    public static void main(String[] args) {

        // creating word document using word document factory
        DocumentFactory wordFactory = new WordDocumentFactory();
        Document word = wordFactory.createDocument();
        word.open();

        // creating pdf document using pdf document factory
        DocumentFactory pdfFactory = new PDFDocumentFactory();
        Document pdf = pdfFactory.createDocument();
        pdf.open();

        // creating excel document using excel document factory
        DocumentFactory excelFactory = new ExcelDocumentFactory();
        Document excel = excelFactory.createDocument();
        excel.open();
    }
}