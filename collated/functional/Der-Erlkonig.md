# Der-Erlkonig
###### \java\seedu\address\storage\HtmlWriter.java
``` java
/**
 * Writes Person Data to a HTML file
 */
public class HtmlWriter {
    public static final String OPENING_LINE = "<!DOCTYPE html><html><head>\n"
            + "<body style=\"background-color:#383838;\"\n>"
            + "<font face=\"Segoe UI\" size=\"5\" color=\"white\">"
            + "<table><tr><th align=\"left\" colspan=\"2\">";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String amountBorrowed;
    private final String amountCurrentlyOwed;
    private final String dueDate;
    private final String runnerAssigned;

    private final List<Person> customerList;

    public HtmlWriter() {
        this.name = null;
        this.phone = null;
        this.email = null;
        this.address = null;
        this.amountBorrowed = null;
        this.amountCurrentlyOwed = null;
        this.dueDate = null;
        this.runnerAssigned = null;
        this.customerList = null;
    }

    /**
     * Constructs HtmlWriter with Customer's details
     * @param customer
     */
    public HtmlWriter(Customer customer) {
        this.name = customer.getName().fullName;
        this.phone = customer.getPhone().value;
        this.email = customer.getEmail().value;
        this.address = customer.getAddress().value;
        this.amountBorrowed = String.format("%,.2f", customer.getMoneyBorrowed().value);
        this.amountCurrentlyOwed = String.format("%,.2f", customer.getMoneyCurrentlyOwed());
        this.dueDate = customer.getOweDueDate().toString();
        this.runnerAssigned = customer.getRunner().getName().fullName;
        this.customerList = null;
    }

    /**
     * Constructs HtmlWriter with Runner's Details
     * @param runner
     */
    public HtmlWriter(Runner runner) {
        this.name = runner.getName().fullName;
        this.phone = runner.getPhone().value;
        this.email = runner.getEmail().value;
        this.address = runner.getAddress().value;
        this.amountBorrowed = "";
        this.amountCurrentlyOwed = "";
        this.dueDate = "";
        this.runnerAssigned = "";
        this.customerList = runner.getCustomers();
    }

    /**
     * Writes Customer's data to a HTML file and returns the file location
     * @return
     */
    public String writeCustomer() {
        String filepath = System.getProperty("user.dir") + File.separator + "PersonPage.html";
        String absoluteFilepath;
        File file = new File(filepath);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(OPENING_LINE);
            printWriter.println(name + "</th></tr>");
            printWriter.println("<tr><td style=\"width: 240px;\">phone: </td><td>" + phone + "</td></tr>");
            printWriter.println("<tr><td>email: </td><td>" + email + "</td></tr>");
            printWriter.println("<tr><td>address: </td><td>" + address + "</td></tr>");
            printWriter.println("<tr><td>amount borrowed: </td><td>$" + amountBorrowed + "</td></tr>");
            printWriter.println("<tr><td>amount owed: </td><td>$" + amountCurrentlyOwed + "</td></tr>");
            printWriter.println("<tr><td>due date: </td><td>" + dueDate + "</td></tr>");
            printWriter.println("<tr><td>runner assigned: </td><td>" + runnerAssigned + "</td></tr>");
            printWriter.println("</table></body></html>");
            printWriter.close();
        } catch (FileNotFoundException e) {
            return "";
        }
        absoluteFilepath = file.getAbsolutePath();
        absoluteFilepath = absoluteFilepath.replaceAll("\"", "/");
        return absoluteFilepath;
    }

    /**
     * Writes Runner's data to HTML file and returns the file location
     * @return
     */
    public String writeRunner() {
        String filepath = System.getProperty("user.dir") + File.separator + "PersonPage.html";
        String absoluteFilepath;
        File file = new File(filepath);
        int customerListSize = customerList.size();
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(OPENING_LINE);
            printWriter.println(name + "</th></tr>");
            printWriter.println("<tr><td style=\"width: 120px;\">phone: </td><td>" + phone + "</td></tr>");
            printWriter.println("<tr><td>email: </td><td>" + email + "</td></tr>");
            printWriter.println("<tr><td>address: </td><td>" + address + "</td></tr>");
            printWriter.println("</table>");
            printWriter.println("<br><br>");
            printWriter.println("<table>");
            printWriter.println("<tr><th align=\"left\">");
            printWriter.println("Customers Assigned [" + customerListSize + "]");
            printWriter.println("</th></tr>");
            for (Person eachCustomer: customerList) {
                printWriter.println("<tr><td>");
                printWriter.println("- " + eachCustomer.getName().fullName);
                printWriter.println("</td></tr>");
            }
            printWriter.println("</table></body></html>");
            printWriter.close();
        } catch (FileNotFoundException e) {
            return "";
        }
        absoluteFilepath = file.getAbsolutePath();
        absoluteFilepath = absoluteFilepath.replaceAll("\"", "/");
        return absoluteFilepath;
    }
}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Loads a HTML file with person details
     * @param person
     */
    private void loadPersonPage(Person person) {
        String personfilepath;
        if (person instanceof Customer) {
            htmlWriter = new HtmlWriter((Customer) person);
            personfilepath = htmlWriter.writeCustomer();
        } else if (person instanceof Runner) {
            htmlWriter = new HtmlWriter((Runner) person);
            personfilepath = htmlWriter.writeRunner();
        } else {
            personfilepath = "";
        }
        loadPage("file:///" + personfilepath);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

```
###### \resources\view\DarkTheme.css
``` css
 th {
     background-color: ;
     border-bottom: 1px solid white;
     padding: 5px;
     text-align: left;
 }

 td {
     height: 28px;
 }
```
