package seedu.address.ui;

import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.SaveVisitCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.visit.Visit;

/**
 * Instantiates a window for logging visit details.
 *
 */
public class VisitRecordWindow extends UiPart<Stage> {

    private static final String FXML = "VisitRecordForm.fxml";
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final int INVALID_VISIT_INDEX = -1;

    @FXML
    private TextArea diagnosis;

    @FXML
    private TextArea prescription;

    @FXML
    private TextArea comment;

    @FXML
    private Button button;

    private String visitDate;
    private String feedbackMessage = "";
    private int patientIndex;
    private int visitIndex;
    private Logic logic;

    /**
     * Instantiates VisitRecordWindow.
     *
     * @param root Stage to use for VisitRecordWindow
     */

    public VisitRecordWindow(EventHandler<WindowEvent> e, Stage root) {
        super(FXML, root);
        root.setOnHidden(e);
        setup();
    }

    /**
     * Instantiates VisitRecordWindow.
     */
    public VisitRecordWindow(EventHandler<WindowEvent> e) {
        this(e, new Stage());
    }

    /**
     * Displays VisitRecordWindow.
     */
    public void show() {
        logger.fine("Displaying Visit Record Window..");
        getRoot().show();
        getRoot().centerOnScreen();
        getRoot().addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            KeyCode userInput = event.getCode();
            if (userInput == KeyCode.ESCAPE) {
                this.hide();
            }
        });
    }

    /**
     * Checks if VisitRecordWindow is being displayed.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides VisitRecordWindow.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Removes all parameters in VisitWindow.
     */
    public void flushParameters() {
        prescription.clear();
        diagnosis.clear();
        comment.clear();
    }

    /**
     * Focuses on VisitRecordWindow.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Saves visit and exits VisitRecordWindow.
     */
    @FXML
    protected void saveVisit(ActionEvent actionEvent) throws CommandException {
        String prescriptionString = prescription.getText();
        String diagnosisString = diagnosis.getText();
        String commentString = comment.getText();

        SaveVisitCommand saveCommand = new SaveVisitCommand(patientIndex, visitDate, prescriptionString,
                                                            diagnosisString, commentString, visitIndex);

        CommandResult commandResult = logic.execute(saveCommand);
        this.feedbackMessage = commandResult.getFeedbackToUser();
        this.hide();
        this.feedbackMessage = "";
    }

    public void setVisitDetails(Logic logic, String visitDate, int patientIndex) {
        this.logic = logic;
        this.visitDate = visitDate;
        this.patientIndex = patientIndex;
        this.visitIndex = INVALID_VISIT_INDEX;
    }

    public void setPreviousVisitDetails(Logic logic, Visit visit, int visitIndex, int patientIndex) {
        this.logic = logic;
        this.visitIndex = visitIndex;
        this.patientIndex = patientIndex;
        this.visitDate = visit.getVisitDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String prescriptionToSet = visit.getPrescription();
        String diagnosisToSet = visit.getDiagnosis();
        String commentToSet = visit.getComment();

        prescription.setText(prescriptionToSet);
        diagnosis.setText(diagnosisToSet);
        comment.setText(commentToSet);
    }

    public void setup() {
        setAcceleratorForSaving(button);
    }


    private void setAcceleratorForSaving(Button button) throws IllegalArgumentException {
        if (button == null) {
            System.err.println("Button is null.");
        }

        assert button != null : "Button cannot be null.";

        if (button.getScene() != null) {
            // Do nothing.
        } else {
            throw new IllegalArgumentException("setSaveAccelerator must be called when a button is attached "
                    + "to a scene");
        }

        button.getScene().getAccelerators().put(KeyCombination.valueOf("F1"), new Runnable() {
            @FXML public void run() {
                button.fire();
            }
        });
    }

    public String getFeedbackMessage() {
        return this.feedbackMessage;
    }
}


