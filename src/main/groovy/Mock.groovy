import org.jsoup.helper.SRHttpConnection
import org.jsoup.nodes.Document
import org.supercsv.cellprocessor.Optional
import org.supercsv.cellprocessor.ift.CellProcessor
import org.supercsv.io.CsvListReader
import org.supercsv.io.CsvMapReader
import org.supercsv.prefs.CsvPreference

class Mock {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\user\\Downloads\\Pragoti Sarani Giveaway - Sheet1.csv")


        CsvMapReader mapReader = new CsvMapReader(new FileReader(file), CsvPreference.STANDARD_PREFERENCE);

        // the header columns are used as the keys to the Map
        final String[] header = mapReader.getHeader(true);
        final CellProcessor[] processors = [new Optional(), new Optional()]

        List customers = []
        Map<String, Object> customer;
        while( (customer = mapReader.read(header, processors)) != null ) {
            try {
                Document document = SRHttpConnection.connect(customer["Profile "]).get();
                customer.name = document.select("#fb-timeline-cover-name").text()
            } catch (Exception ex) {
                customer.name = customer["Name "]
            }

            println((customer.name ?: "") + "," + customer["Profile "])
        }

    }
}
