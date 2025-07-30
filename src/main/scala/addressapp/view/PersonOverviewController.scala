package addressapp.view

import addressapp.MainApp
import addressapp.model.Person
import javafx.fxml.FXML
import javafx.scene.control.{Label, TableColumn, TableView}
import scalafx.Includes.*
import scalafx.beans.binding.Bindings
import addressapp.util.DateUtil
import addressapp.util.DateUtil.asString
import javafx.event.ActionEvent
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scala.util.{Failure, Success}
@FXML
class PersonOverviewController():

  @FXML
  private var personTable: TableView[Person] = null
  @FXML
  private var firstNameColumn:TableColumn[Person,String] = null
  @FXML
  private var lastNameColumn: TableColumn[Person,String] = null
  @FXML
  private var firstNameLabel:Label = null
  @FXML
  private var lastNameLabel: Label = null
  @FXML
  private var streetLabel: Label = null
  @FXML
  private var postalCodeLabel: Label = null
  @FXML
  private var cityLabel: Label = null
  @FXML
  private var birthdayLabel: Label = null

      //person is null, remove all the text
  //initialize Table View display contents ,odel
  def initialize(): Unit =
    personTable.items = MainApp.personData
    //initialize column's cell values
    firstNameColumn.cellValueFactory = {x=> x.value.firstName}
    lastNameColumn.cellValueFactory = {x=> x.value.lastName}

    showPersonDetails(None)

    personTable.selectionModel().selectedItem.onChange(
      (_, _, newValue) => showPersonDetails(Option(newValue))
    )
  end initialize


  private def showPersonDetails(person: Option[Person]): Unit =
    person match
      case Some(person) =>
        //fill the labels with info from the Person object.
        firstNameLabel.text <== person.firstName
        lastNameLabel.text <== person.lastName
        streetLabel.text <== person.street
        cityLabel.text <== person.city
        //scalafx delegates convert the ScalaFx property
        //into its JavaFX equivalent
        postalCodeLabel.text <== person.postalCode.delegate.asString()
        birthdayLabel.text <== Bindings.createStringBinding(
          () => {person.date.value.asString}, person.date
        )

      case None =>
        //no person selected, clear all labels
        firstNameLabel.text.unbind()
        lastNameLabel.text.unbind()
        streetLabel.text.unbind()
        cityLabel.text.unbind()
        postalCodeLabel.text.unbind()
        birthdayLabel.text.unbind()

        firstNameLabel.text = ""
        lastNameLabel.text = ""
        streetLabel.text = ""
        cityLabel.text = ""
        postalCodeLabel.text= ""
        birthdayLabel.text = ""
  end showPersonDetails

  def handleDeletePerson(action:ActionEvent): Unit =
    val selectedIndex =
      personTable.selectionModel().selectedIndex.value
    val selectedPerson =
      personTable.selectionModel().selectedItem.value
    if(selectedIndex >= 0) then
      selectedPerson.delete() match
        case Success(x) =>
          personTable.items().remove(selectedIndex)
        case Failure(e) =>
          val alert = new Alert(Alert.AlertType.Warning):
            initOwner(MainApp.stage)
            title = "Failed to Delete"
            headerText = "Database Error"
            contentText = "Database error: Failed to delete"
          alert.showAndWait()
    else
      val alert = new Alert(AlertType.Warning):
        initOwner(MainApp.stage)
        title = "No Selection"
        headerText = "No Person Selected"
        contentText = "Please select a person in the table"
      alert.showAndWait()
    end if
  end handleDeletePerson

  def handleNewPerson(action :ActionEvent) =
    val person = new Person("", "")
    val okClicked = MainApp.showPersonEditDialog(person)
    if(okClicked) then
      person.save() match
        case Success(x) =>
          MainApp.personData += person
        case Failure(e) =>
          val alert = new Alert(Alert.AlertType.Warning):
            initOwner(MainApp.stage)
            title = "Failed to Save"
            headerText = "Database Error"
            contentText = "Database error: Failed to save changes"
          alert.showAndWait()
    end if
  end handleNewPerson

  def handleEditPerson(action: ActionEvent) =
    val selectedPerson =
      personTable.selectionModel().selectedItem.value
    if(selectedPerson != null) then
      val okClicked = MainApp.showPersonEditDialog(selectedPerson)
      if (okClicked) then
        selectedPerson.save() match
          case Success(x) =>
            showPersonDetails(Some(selectedPerson))
          case Failure(e) =>
            val alert = new Alert(Alert.AlertType.Warning):
              initOwner(MainApp.stage)
              title = "Failed to Save"
              headerText = "Database Error"
              contentText = "Database error: Failed to save changes"
            alert.showAndWait()
      end if
    else
      val alert = new Alert(Alert.AlertType.Warning):
        initOwner(MainApp.stage)
        title = "No Selection"
        headerText = "No Person Selected"
        contentText = "Please select a person in the table"
      alert.showAndWait()
  end handleEditPerson
