package org.eclipse.epsilon.emc.json;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.plainxml.PlainXmlType;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.models.CachedModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JsonModel extends CachedModel<Object>{

	/** CONSTANTS */
	
	public static String PROPERTY_FILE = "file";
	public static String PROPERTY_URI = "uri";
	public static String PROPERTY_USERNAME = "username";
	public static String PROPERTY_PASSWORD = "password";

	/** FIELDS */
	
	protected File file;
	protected String uri;
	protected String username;
	protected String password;

	/** SPECIAL FIELDS */
	
	protected JSONElement root = null;
	protected JsonPropertyGetter propertyGetter = new JsonPropertyGetter();
	
	/** TEST */
	
	public static void main(String[] args) throws Exception {
		JsonModel model = new JsonModel();
		model.setName("M");
		model.setFile(new File("commits.json"));
		model.load();

		EolModule module = new EolModule();
		module.getContext().getModelRepository().addModel(model);	
		module.parse("M.e_root.e_commit.properties.collect(e|e.id).println();");
		System.out.println(module.execute());
	}	
	
	/** KEY METHODS */
	
	@Override
	public boolean owns(Object instance) {
		return instance instanceof JSONElement 
				&& ((JSONElement) instance).getValue() instanceof JSONObject
				/* || instance instanceof JSONArray*/;
	}

	@Override
	public boolean hasType(String type) {
		return PlainXmlType.parse(type) != null;
	}
	
	@Override
	public boolean isInstantiable(String type) {
		return hasType(type);
	}
	
	@Override
	public String getTypeNameOf(Object instance) {
		if (instance instanceof JSONElement) {
			return ((JSONElement) instance).getTag();
		} else {
			return instance.getClass().getSimpleName();
		}
	}
	
	@Override
	protected Collection<String> getAllTypeNamesOf(Object instance) {
		return Collections.singleton(getTypeNameOf(instance));
	}

	@Override 
	protected Collection<Object> getAllOfTypeFromModel(String type)
			throws EolModelElementTypeNotFoundException {

		PlainXmlType jsonType = PlainXmlType.parse(type);
		if (jsonType == null) {
			throw new EolModelElementTypeNotFoundException(this.getName(), type);
		}
		String endType = jsonType.getTagName();
		List<Object> allOfType = new ArrayList<Object>();
		for (Object o : allContents()) {
			JSONElement element = (JSONElement) o;
			String tmpType = element.getTag();
			if (tmpType.equalsIgnoreCase(endType)){
				allOfType.add(element);
			} else if (
				tmpType.length()-1==endType.length() 
				&& tmpType.endsWith("s")
				&& tmpType.substring(0, tmpType.length()-1).equalsIgnoreCase(endType))
			{
				for (JSONElement child : element.getChildren()){
					allOfType.add(child);
				}
			}
		}
		return allOfType;
	}
	
	@Override
	protected Collection<Object> getAllOfKindFromModel(String kind)
			throws EolModelElementTypeNotFoundException {
		return getAllOfTypeFromModel(kind);
	}

	@Override // FIXME
	protected Collection<Object> allContentsFromModel() { 
		ArrayList<Object> elements = new ArrayList<Object>();
		JsonUtil.collectChildElements(root, elements);
		return elements;		
	}

	@Override
	protected Object getCacheKeyForType(String type)
			throws EolModelElementTypeNotFoundException {
		return type;
	}
	
	@Override
	protected Object createInstanceInModel(String type)
			throws EolModelElementTypeNotFoundException,
			EolNotInstantiableModelElementTypeException {
		if (JSONObject.class.getSimpleName().equals(type)) {
			return new JSONObject();
		} else if (JSONArray.class.getSimpleName().equals(type)) {
			return new JSONArray();
		} else {
			throw new EolModelElementTypeNotFoundException(this.getName(), type);
		}
	}
	
	/** OPERATIONAL (ROOT-RELATED) METHODS */

	@Override
	protected void loadModel() throws EolModelLoadingException {
		try {
			if (readOnLoad) {
				Reader reader = null;
				if (file == null) {
					HttpClient httpClient = HttpClients.createDefault();
					if (uri!=null){
						HttpGet httpGet = new HttpGet(uri);
						if (username != null) {
							String credentials =  username + ":" + password;
							httpGet.addHeader(HttpHeaders.AUTHORIZATION, 
									AuthSchemes.BASIC + " " + Base64.encodeBase64String(credentials.getBytes()));
						}
						HttpResponse httpResponse = httpClient.execute(httpGet);
						HttpEntity responseEntity = httpResponse.getEntity();
						reader = new InputStreamReader(responseEntity.getContent());
					}
				} else {
					reader = new FileReader(file);
				}
				
				root = new JSONElement(JSONValue.parse(reader));
			}
		} catch (Exception ex) {
			throw new EolModelLoadingException(ex, this);
		}
	}
	
	@Override
	protected void disposeModel() {
		root = null;
	}
	
	@Override
	public boolean store(String location) {
		try {
			FileUtil.setFileContents(JSONValue.toJSONString(root.getValue()), new File(location));
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean store() {
		if (file != null) {
			return store(file.getAbsolutePath());
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver)
			throws EolModelLoadingException {
		super.load(properties, resolver);
		String filePath = properties.getProperty(PROPERTY_FILE);
		if (filePath != null && filePath.trim().length() > 0) {
			file = new File(resolver.resolve(filePath));
		} else {
			uri = properties.getProperty(PROPERTY_URI);
			username = properties.getProperty(PROPERTY_USERNAME);
			password = properties.getProperty(PROPERTY_PASSWORD);
		}
		load();
	}

	/** GETTERS & SETTERS */
	
	@Override
	public IPropertyGetter getPropertyGetter() {
		return propertyGetter;
	}
	
	public JSONElement getRoot() {
		return root;
	}

	public void setRoot(JSONElement root) {
		this.root = root;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
	
	/** UNSUPPORTED OPERATIONS */
	
	@Override
	public Object getEnumerationValue(String enumeration, String label)
			throws EolEnumerationValueNotFoundException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getElementById(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getElementId(Object instance) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setElementId(Object instance, String newId) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected boolean deleteElementInModel(Object instance)
			throws EolRuntimeException {
		throw new UnsupportedOperationException(); // FIXME
	}
}
