package org.eclipse.scava.crossflow.runtime.serialization;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;

public class JsonSerializer extends AbstractSerializer {

	public static final String TYPE_PROPERTY_KEY = "_type_";
	public static final String ENUM_TYPE_PROPERTY_KEY = "_enum_type_";
	public static final String ENUM_VALUE_PROPERTY_KEY = "_enum_value_";

	protected Gson gson;

	public JsonSerializer() {
		this(false, true);
	}

	public JsonSerializer(boolean pretty, boolean isStrict) {
		super(isStrict);
		GsonBuilder builder = new GsonBuilder().registerTypeAdapterFactory(new ClassTypeAnnotatingTypeAdapterFactory())
				.registerTypeAdapterFactory(new EnumTypeAnnotatingTypeAdapterFactory());
		this.gson = pretty ? builder.setPrettyPrinting().create() : builder.create();
	}

	@Override
	protected void doRegisterType(Class<?> type) {
		;
	}

	@Override
	public <O> String serialize(O input) {
		if (isStrict) {
			checkArgument(isRegistered(input),
					"Strict Mode: %s type must be registered with registerType()",
					input.getClass().getCanonicalName());
		}
		return gson.toJson(input);
	}

	@Override
	public <O> O deserialize(String input) {
		JsonObject jsonObject = gson.fromJson(input, JsonObject.class);
		return doDeserialize(jsonObject);
	}
	
	@Override
	public <O> O deserialize(File input) {
		try (JsonReader reader = new JsonReader(new FileReader(input))) {
			return doDeserialize(gson.fromJson(reader, JsonObject.class));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e); // Is this the correct rethrow to do?
		}
	}
	
	@SuppressWarnings("unchecked")
	private <O> O doDeserialize(JsonObject jsonObject) {
		checkArgument(jsonObject.has(TYPE_PROPERTY_KEY),
				"Missing %s key. Received: ", TYPE_PROPERTY_KEY, jsonObject.toString());		
		String type = jsonObject.get(TYPE_PROPERTY_KEY).getAsString();
		if (isStrict) {
			checkArgument(isRegistered(type),
					"Strict Mode: %s type must be registered with registerType()",
					type);
		}
		return (O) gson.fromJson(jsonObject, registeredTypes.get(type));
	}

	/**
	 * check if the given type token is an Enumerate type
	 * 
	 * @param <T>
	 * @param type
	 * @return
	 */
	static <T> boolean isEnum(Class<T> type) {
		return Enum.class.isAssignableFrom(type) || type.isEnum() || type == Enum.class;
	}

	/**
	 * Gson Type Adapter that adds an additional {@code _type_} key containing the
	 * type name of the class being serialized.
	 * <p>
	 * Classes must be registered with the outer {@link JsonSerializer} instance
	 * first else the default Gson class serialiser will be used
	 */
	class ClassTypeAnnotatingTypeAdapterFactory implements TypeAdapterFactory {

		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
			final Class<? super T> rawType = type.getRawType();
			if (isEnum(rawType) || !isRegistered(rawType)) {
				return null;
			}

			return new TypeAdapter<T>() {
				final TypeAdapter<T> delegate = gson.getDelegateAdapter(ClassTypeAnnotatingTypeAdapterFactory.this,
						type);
				final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

				@Override
				public void write(JsonWriter out, T value) throws IOException {
					if (isStrict) {
						checkArgument(isRegistered(value),
								"Strict Mode: %s type must be registered with registerType()",
								value);
					}
					JsonElement jsonTree = delegate.toJsonTree(value);
					JsonObject jsonObj = jsonTree.getAsJsonObject();
					jsonObj.addProperty(TYPE_PROPERTY_KEY, value.getClass().getSimpleName());
					elementAdapter.write(out, jsonObj);
				}

				@Override
				public T read(JsonReader in) throws IOException {
					return delegate.read(in);
				}
			}.nullSafe();
		}
	}

	/**
	 * Gson Type Adapter that adds an additional {@code _enum_type_} key containing
	 * the type name of the Enum being serialized.
	 */
	class EnumTypeAnnotatingTypeAdapterFactory implements TypeAdapterFactory {

		@SuppressWarnings("unchecked")
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
			final Class<? super T> rawType = type.getRawType();
			if (!isEnum(rawType)) {
				return null;
			}

			final BiMap<T, String> enumToName = HashBiMap.create();
			for (T constant : ((Class<T>) type.getRawType()).getEnumConstants()) {
				String name = ((Enum<?>) constant).name();
				enumToName.put(constant, name);
			}

			return new TypeAdapter<T>() {

				final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

				@Override
				public void write(JsonWriter out, T value) throws IOException {
					if (isStrict) {
						checkArgument(isRegistered(rawType),
								"Strict Mode: %s type must be registered with registerType()",
								rawType.getSimpleName());
					}
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty(ENUM_TYPE_PROPERTY_KEY, rawType.getSimpleName());
					jsonObj.addProperty(ENUM_VALUE_PROPERTY_KEY, value.toString());
					elementAdapter.write(out, jsonObj);
				}

				@Override
				public T read(JsonReader in) throws IOException {
					in.beginObject();
					T enumValue = null;
					while (in.peek() != JsonToken.END_OBJECT) {
						String key = in.nextName();
						String value = in.nextString();
						switch (key) {
						case ENUM_TYPE_PROPERTY_KEY:
							if (isStrict) {
								checkArgument(isRegistered(value),
										"Strict Mode: %s type must be registered with registerType()",
										value);
							}
							break;
						case ENUM_VALUE_PROPERTY_KEY:
							enumValue = enumToName.inverse().get(value);
							break;
						default:
							throw new MalformedJsonException("Unrecognised key " + key);
						}
					}
					in.endObject();
					return enumValue;
				}
			}.nullSafe();

		}
	}
}
