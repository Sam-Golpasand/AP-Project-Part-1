package dk.dtu.compute.course02324.assignment3.lists.uses;


import dk.dtu.compute.course02324.assignment3.lists.implementations.GenericComparator;
import dk.dtu.compute.course02324.assignment3.lists.types.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import jakarta.validation.constraints.NotNull;

import java.util.*;

/**
 * A GUI element that is allows the user to interact and
 * change a list of persons.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class PersonsGUI extends GridPane {

    /**
     * The list of persons to be maintained in this GUI.
     */
    final private List<Person> persons;

    private GridPane personsPane;
    private GridPane exceptionsPane;
    private Label averageWeightLabel;
    private Label commonNameLabel;
    private ArrayList<Exception> exceptions = new ArrayList<>();

    /**
     * Constructor which sets up the GUI attached a list of persons.
     *
     * @param persons the list of persons which is to be maintained in
     *                this GUI component; it must not be <code>null</code>
     */
    public PersonsGUI(@NotNull List<Person> persons) {
        this.persons = persons;

        this.setVgap(5.0);
        this.setHgap(5.0);

        // text filed for user entering a name
        TextField field = new TextField();
        field.setPrefColumnCount(5);
        field.setText("name");

        TextField weightField = new TextField();
        weightField.setPrefColumnCount(5);
        weightField.setText("weight");

        // TODO for all buttons installed below, the actions need to properly
        //      handle (catch) exceptions, and it would be nice if the GUI
        //      could also show the exceptions thrown by user actions on
        //      button pressed (cf. Assignment 2).

        // button for adding a new person to the list (based on
        // the name in the text field (the weight is just incrementing)
        // TODO a text field for the weight could be added to this GUI
        Button addButton = new Button("Add");
        addButton.setOnAction(
                e -> {
                    try {
                        Person person = new Person(field.getText(), Double.parseDouble(weightField.getText()));
                        persons.add(person);
                        // makes sure that the GUI is updated accordingly
                    } catch (Exception error) {
                        exceptions.add(error);
                    } finally {
                        update();
                    }
                });

        Comparator<Person> comparator = new GenericComparator<>();

        // button for sorting the list (according to the order of Persons,
        // which implement the interface Comparable, which is converted
        // to a Comparator by the GenericComparator above)
        Button sortButton = new Button("Sort");
        sortButton.setOnAction(
                e -> {
                    try {
                        persons.sort(comparator);
                    } catch (Exception error) {
                        exceptions.add(error);
                    } finally {
                        update();
                    }
                });

        // button for clearing the list
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(
                e -> {
                    persons.clear();
                    // makes sure that the GUI is updated accordingly
                    update();
                });

        TextField indexField = new TextField();
        indexField.setPrefColumnCount(5);
        indexField.setText("index");

        Button addToIndexButton = new Button("Add to index");
        addToIndexButton.setOnAction(
                e -> {
                    try {

                        Person person = new Person(field.getText(), Double.parseDouble(weightField.getText()));
                        persons.add(Integer.parseInt(indexField.getText()), person);
                        // makes sure that the GUI is updated accordingly
                    } catch (Exception error) {
                        exceptions.add(error);
                    } finally {
                        update();
                    }
                });


        averageWeightLabel = new Label("Average Weight: ");
        commonNameLabel = new Label("Most Common Name: ");

        // combines the above elements into vertically arranged boxes
        // which are then added to the left column of the grid pane
        VBox actionBox = new VBox(field, weightField, addButton, sortButton, clearButton, indexField, addToIndexButton, averageWeightLabel, commonNameLabel);
        actionBox.setSpacing(5.0);
        this.add(actionBox, 0, 0);

        // create the elements of the right column of the GUI
        // (scrollable person list) ...
        Label labelPersonsList = new Label("Persons:");

        personsPane = new GridPane();
        personsPane.setPadding(new Insets(5));
        personsPane.setHgap(5);
        personsPane.setVgap(5);

        ScrollPane scrollPane = new ScrollPane(personsPane);
        scrollPane.setMinWidth(300);
        scrollPane.setMaxWidth(300);
        scrollPane.setMinHeight(300);
        scrollPane.setMaxHeight(300);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // ... and adds these elements to the right-hand columns of
        // the grid pane
        VBox personsList = new VBox(labelPersonsList, scrollPane);
        personsList.setSpacing(5.0);
        this.add(personsList, 1, 0);


        Label labelExceptions = new Label("Exceptions:");
        exceptionsPane = new GridPane();
        exceptionsPane.setPadding(new Insets(5));
        exceptionsPane.setHgap(5);
        exceptionsPane.setVgap(5);

        ScrollPane exceptionsScroll = new ScrollPane(exceptionsPane);
        exceptionsScroll.setMinWidth(300);
        exceptionsScroll.setMaxWidth(300);
        exceptionsScroll.setMinHeight(150);
        exceptionsScroll.setMaxHeight(150);
        exceptionsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        exceptionsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox exceptionsBox = new VBox(labelExceptions, exceptionsScroll);
        exceptionsBox.setSpacing(5.0);
        this.add(exceptionsBox, 1, 1); // under persons list

        // updates the values of the different components with the values from
        // the stack
        update();
    }

    /**
     * Updates the values of the GUI elements with the current values
     * from the list.
     */
    private void update() {
        personsPane.getChildren().clear();
        double totalWeight = 0;

        Map<String, Integer> nameCount = new HashMap<>();
        // adds all persons to the list in the personsPane (with
        // a delete button in front of it)
        for (int i=0; i < persons.size(); i++) {
            Person person = persons.get(i);
            totalWeight += person.weight;

            // Increment or insert name if is already in list of nameCount
            if (nameCount.get(person.name) == null) {
                nameCount.put(person.name, 1);
            } else {
                int newCount = nameCount.get(person.name) + 1;
                nameCount.put(person.name, newCount);
            }

            Label personLabel = new Label(i + ": " + person.toString());
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(
                    e -> {
                        try {
                            persons.remove(person);
                        } catch (Exception error) {
                            exceptions.add(error);
                        } finally {
                            update();
                        }
                    }
            );



            HBox entry = new HBox(deleteButton, personLabel);
            entry.setSpacing(5.0);
            entry.setAlignment(Pos.BASELINE_LEFT);
            personsPane.add(entry, 0, i);
        }

        if (!persons.isEmpty()) {
            double average = totalWeight / persons.size();
            averageWeightLabel.setText("Average Weight: " + average);
        } else {
            averageWeightLabel.setText("Average Weight: ");
        }

        // Get the name of the most occuring person in the list
        String name = "";
        int currentMaxCount = 0;

        Set<Map.Entry<String, Integer>> nameCountSet = nameCount.entrySet();

        for (Map.Entry<String, Integer> entry : nameCountSet) {
            if (entry.getValue() > currentMaxCount) {
                name = entry.getKey();
                currentMaxCount = entry.getValue();
            }
        }

        if (!name.isEmpty()) {
            commonNameLabel.setText("Most Common Name: " + name);
        }

        exceptionsPane.getChildren().clear();
        for (int i = 0; i < exceptions.size(); i++) {
            Exception ex = exceptions.get(i);
            Label exLabel = new Label(ex.getClass() + " " +  ex.getMessage());
            exceptionsPane.add(exLabel, 0, i);
        }

    }

    // TODO this GUI could be extended by some additional widgets for issuing other
    //      operations of lists. And the possibly thrown exceptions should be caught
    //      in the event handler (and possibly shown in an additional text area for
    //      exceptions; see Assignment 2).

}
