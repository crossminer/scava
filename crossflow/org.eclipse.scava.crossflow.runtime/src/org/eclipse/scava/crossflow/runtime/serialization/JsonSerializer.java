package org.eclipse.scava.crossflow.runtime.serialization;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class JsonSerializer implements Serializer {

	public static final String TYPE_PROPERTY_KEY = "_type_";

	protected Gson gson;
	protected Map<String, Class<?>> registeredTypes;

	public JsonSerializer() {
		this(false);
	}

	public JsonSerializer(boolean pretty) {
		gson = pretty ? new GsonBuilder().setPrettyPrinting().create() : new Gson();
		this.registeredTypes = new HashMap<>();
	}

	@Override
	public Serializer registerType(Class<?> clazz) {
		if (!isRegistered(clazz)) {
			registeredTypes.put(clazz.getSimpleName(), clazz);
			gson = gson.newBuilder().registerTypeAdapterFactory(new AnnotatingTypeAdapterFactory()).create();
		}
		return this;
	}

	@Override
	public boolean isRegistered(String clazz) {
		return registeredTypes.containsKey(clazz);
	}

	@Override
	public Collection<Class<?>> getRegisteredTypes() {
		return registeredTypes.values();
	}

	@Override
	public <O> String serialize(O input) {
		checkArgument(isRegistered(input), "%s not a registered type", input.getClass().getSimpleName());
		return gson.toJson(input);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <O> O deserialize(String input) {
		JsonObject jsonObject = gson.fromJson(input, JsonObject.class);
		checkArgument(jsonObject.has(TYPE_PROPERTY_KEY), "Missing %s key. Received: ", TYPE_PROPERTY_KEY, input);
		String type = jsonObject.get(TYPE_PROPERTY_KEY).getAsString();
		checkArgument(isRegistered(type), "%s not a registered type", type);
		return (O) gson.fromJson(jsonObject, registeredTypes.get(type));
	}

	public class AnnotatingTypeAdapterFactory implements TypeAdapterFactory {

		private AnnotatingTypeAdapterFactory() {
		}

		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
			return isRegistered(type.getRawType())
					? new AnnotatingTypeAdapter<T>(gson, gson.getDelegateAdapter(this, type))
					: null;
		}
	}

	public class AnnotatingTypeAdapter<T> extends TypeAdapter<T> {

		private final TypeAdapter<T> delegate;
		private final TypeAdapter<JsonElement> elementAdapter;

		public AnnotatingTypeAdapter(Gson gson, TypeAdapter<T> delegate) {
			this.delegate = delegate;
			this.elementAdapter = gson.getAdapter(JsonElement.class);
		}

		@Override
		public void write(JsonWriter out, T value) throws IOException {
			JsonObject jsonObj = delegate.toJsonTree(value).getAsJsonObject();
			jsonObj.addProperty(TYPE_PROPERTY_KEY, value.getClass().getSimpleName());
			elementAdapter.write(out, jsonObj);
		}

		@Override
		public T read(JsonReader in) throws IOException {
			return delegate.read(in);
		}

	}

}
