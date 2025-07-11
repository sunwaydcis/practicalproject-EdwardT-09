package addressapp
//
import addressapp.model.Person
import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes.*
import scalafx.scene as sfxs
import javafx.scene as jfxs
import scalafx.collections.ObservableBuffer

object MainApp extends JFXApp3:
  //window root pane
//  var roots: Option[scalafx.scene.layout.BorderPane] = None
  var roots: Option[sfxs.layout.BorderPane] = None
  val personData = new ObservableBuffer[Person]()

  //constructor
  personData += new Person("Hans","Muster")
  personData += new Person("Ruth","Mueller")
  personData += new Person("Heinz","Kurz")
  personData += new Person("Cornelia","Meier")
  personData += new Person("Werner","Meyer")
  personData += new Person("Lydia", "Kunz")
  personData += new Person("Anna", "Best")
  personData += new Person("Stefan", "Meier")
  personData += new Person("Martin", "Mueller")
  

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
      scene = new Scene():
        root = roots.get
          //call to display personOverview when app starts
    showPersonOverview()

    //actions for displaying PersonOverview window
    def showPersonOverview():Unit=
      val resource = getClass.getResource("view/PersonOverview.fxml")
      val loader = new FXMLLoader(resource)
      loader.load()
      val roots = loader.getRoot[jfxs.layout.AnchorPane]
      this.roots.get.center = roots



