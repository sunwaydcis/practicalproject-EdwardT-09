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
    if(selectedIndex >= 0) then
      personTable.items().remove(selectedIndex)
    else
      val alert = new Alert(AlertType.Warning):
        initOwner(MainApp.stage)
        title = "No Selection"
        headerText = "No Person Selected"
        contentText = "Please select a person in the table"
      alert.showAndWait()
  end handleDeletePerson
  