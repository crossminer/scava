package restmule.codegen;

import java.io.File;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.swing.JFileChooser;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.Target;

/**
 * This class does not work yet. 
 * The idea is to have a java alternative to trigger the code generation from the ANT file from java
 */
public class Main {

	private static final String SCHEMAS_DIR = "schemas";
	private static final String SETUP = "setup";
	private static final String M2T = "M2T";
	private static final String M2M = "M2M";
	private static final String ANT_PROJECT_HELPER = "ant.projectHelper";
	private static final String BUILD_XML = "build.xml";
	private static final String ANT_FILE = "ant.file";

	private static final String JSON_MODEL_FILE ="json.model.file";
	private static final String TARGET ="target";
	private static final String OUTPUT ="output";

	public static final String API_DESCRIPTOR = "API descriptor";
	public static final String API_KEY = "key";
	public static final String API_SECRET = "secret";
	public static final String HOST = "host";
	public static final String KEY_TYPE = "keytype";
	public static final String KEY_STORE = "keystore";
	public static final String KEY_PASS = "keypass";
	public static final String HELP = "help";


	public static void main(String[] args) {

		Vector<Target> targets = new Vector<Target>();

		Target m2mTarget = new Target();
		m2mTarget.setName(M2M);
		Target m2tTarget = new Target();
		m2tTarget.setName(M2T);
		Target setupTarget = new Target();
		setupTarget.setName(SETUP);

		File buildFile = new File(BUILD_XML);
		Project p = new Project();
		p.setUserProperty(ANT_FILE, buildFile.getAbsolutePath());
		p.init();
		ProjectHelper helper = ProjectHelper.getProjectHelper();
		p.addReference(ANT_PROJECT_HELPER, helper);
		helper.parse(p, buildFile);

		Scanner scanner = new Scanner(System.in);
		while (true){
			System.out.println("Which transformation would you like to start? (M2M / M2T) : ");
			String target = scanner.next();
			if (target.equals(M2M)){
				System.out.println("Select the OpenAPI JSON schema to transform.");
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setCurrentDirectory(new File(SCHEMAS_DIR));
				
				chooser.showOpenDialog(null);
				File selectedFile = chooser.getSelectedFile();
				if (selectedFile.isFile() && selectedFile.getName().endsWith(".json")){
					System.out.println(selectedFile.getName());
					
					targets.add(m2mTarget);
					break;
				} else {
					chooser = null;
					System.out.println("Something went wrong");
				}
				//p.executeSortedTargets(targets);
			} else if (target.equals(M2M)){
				//p.executeTarget(p.getDefaultTarget());
				break;
			} else { 
				System.out.println("Invalid");
			}

		}
		scanner.close();

	}

}
