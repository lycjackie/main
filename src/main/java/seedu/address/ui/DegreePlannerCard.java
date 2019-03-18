package seedu.address.ui;

import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import seedu.address.model.planner.DegreePlanner;

/**
 * An UI component that displays information of a {@code DegreePlanner}.
 */
public class DegreePlannerCard extends UiPart<Region> {

    private static final String FXML = "DegreePlannerListCard.fxml";

    public final DegreePlanner degreePlanner;

    @FXML
    private StackPane degreePlannerCardPane;

    @FXML
    private Label year;

    @FXML
    private Label semester;

    @FXML
    private ListView<String> degreePlannerListView;

    public DegreePlannerCard(DegreePlanner degreePlanner) {
        super(FXML);
        this.degreePlanner = degreePlanner;

        year.setText("Year: " + degreePlanner.getYear().year);
        semester.setText(" Semester: " + degreePlanner.getSemester().plannerSemester);

        year.setStyle("-fx-text-fill: white;");
        semester.setStyle("-fx-text-fill: white;");

        ObservableList<String> modules = degreePlanner.getCodes().stream().map(Object::toString)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        degreePlannerListView.setItems(modules);
        degreePlannerListView.setCellFactory((ListView<String> l) -> new StyleFormatCell());
        degreePlannerCardPane.setOnMouseClicked(null);
        degreePlannerCardPane.setPrefHeight(85);
    }

    /**
     * Custom {@code ListCell} that customizes a color of a {@code String}.
     */
    public class StyleFormatCell extends ListCell<String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(item);
            setTextFill(Color.WHITESMOKE);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DegreePlannerCard)) {
            return false;
        }

        // state check
        DegreePlannerCard card = (DegreePlannerCard) other;
        return year.getText().equals(card.year.getText());
    }
}
