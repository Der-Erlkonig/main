package seedu.address.logic.commands;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LocateRequestEvent;
import seedu.address.model.person.Person;
import seedu.address.ui.MainWindow;

/**
 * Locate the address of a person by keywords on Google Map.
 * Keyword matching is case sensitive.
 */
public class LocateCommand extends Command implements PopulatableCommand {
    public static final String COMMAND_WORD = "locate";
    public static final String COMMAND_ALIAS = "lo";
  
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Locate person whose fields contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Valid specifiers are -n, -p, -e, -a, -t, for NAME, PHONE, EMAIL, ADDRESS and TAGS"
            + " respectively \n"
            + "Parameters: [SPECIFIER] KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " -n alice bob charlie";

    public static final String MESSAGE_LOCATE_SUCCESS = "Locate successful";
    public static final String MESSAGE_LOCATE_SELECT = "More than one person found! " +
            "Locate the person on top of the list by default.";

    private final int target = 0;
    private final int targetOne = 1;
    private final Predicate<Person> predicate;

    public LocateCommand(Predicate<Person> predicate) {
        this.predicate = predicate;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Display the place on Google Map identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LOCATE_SUCCESS = "Locate: %1$s";

    private final Index targetIndex;

    public LocateCommand (Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * For call in PopulatePrefixRequestEvent class, to assign string values.
     */
    public LocateCommand() {
        targetIndex = null;
    }


    @Override
    public CommandResult execute() {
        List<Person> lastShownList = model.getFilteredPersonList(predicate);

        Person location = lastShownList.get(target);

        // Open Google Map on BrowserPanel
        MainWindow.loadUrl("https://www.google.com.sg/maps/place/"
                + location.getAddress().toString());

        if (model.getFilteredPersonList().size() > 1) {
            return new CommandResult(String.format(MESSAGE_LOCATE_SELECT, targetOne));
        }

        EventsCenter.getInstance().post(new LocateRequestEvent(target));
        return new CommandResult(String.format(MESSAGE_LOCATE_SUCCESS, targetOne));
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((LocateCommand) other).predicate));
        // state check
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public String getTemplate() {
        return COMMAND_WORD + " ";
    }

    @Override
    public int getCaretIndex() {
        return getTemplate().length();
    }

    @Override
    public String getUsageMessage() {
        return MESSAGE_USAGE;
    }
}
