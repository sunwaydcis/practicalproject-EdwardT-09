package addressapp
//
import addressapp.model.Person
import addressapp.util.Database
import addressapp.view.PersonEditDialogController
import javafx.event.ActionEvent
import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes.*
import scalafx.scene as sfxs
import javafx.scene as jfxs
import scalafx.collections.ObservableBuffer
import scalafx.scene.image.Image
import scalafx.stage.{Modality, Stage}

object MainApp extends JFXApp3:
  Database.setupDB()
  //window root pane
//  var roots: Option[scalafx.scene.layout.BorderPane] = None
  var roots: Option[sfxs.layout.BorderPane] = None
  //stylesheet
  var cssResource = getClass.getResource("view/style.css")

  val personData = new ObservableBuffer[Person]()

  //constructor
  personData ++= Person.getAllPersons
  

  override def start():Unit =

    //transform path of RootLayout.fxml to URI for resource location

    val rootResource = getClass.getResource("view/RootLayout.fxml")

    //initialize the loader object
    val loader = new FXMLLoader(rootResource)

    //load root layout from fxml file

    loader.load()

    //retrieve the root component BorderPane from the FXML
    //refer to slides on scala option monad
    roots = Option(loader.getRoot[jfxs.layout.BorderPane])

    stage = new PrimaryStage():
      title = "AddressApp"
      icons += new Image(getClass.getResource(
        "/images/book.png").toExternalForm)
      scene = new Scene():
        stylesheets = Seq(cssResource.toExternalForm)
        root = roots.get
          //call to display personOverview when app starts
    showPersonOverview()
  end start
    
  //actions for displaying PersonOverview window
  def showPersonOverview():Unit=
    val resource = getClass.getResource("view/PersonOverview.fxml")
    val loader = new FXMLLoader(resource)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.get.center = roots
  end showPersonOverview
  
    
    
    
  def showPersonEditDialog(person: Person): Boolean =
    val resource = getClass.getResource("view/PersonEditDialog.fxml")
    val loader = new FXMLLoader(resource)
    loader.load()
    val roots2 = loader.getRoot[jfxs.Parent]
    val control = loader.getController[PersonEditDialogController]
    val dialog = new Stage():
      initModality(Modality.ApplicationModal)
      initOwner(stage)
      scene = new Scene:
        stylesheets = Seq(cssResource.toExternalForm)
        root = roots2
    control.dialogStage = dialog
    control.person = person
    dialog.showAndWait()
    control.okClicked
  end showPersonEditDialog
  
 
        


